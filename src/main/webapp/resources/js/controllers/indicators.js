var care = angular.module('care');

Array.prototype.sortByField = function(fieldName) {
    this.sort(function(a, b) {
        if (a[fieldName] > b[fieldName]) {
            return 1;
        } else if (a[fieldName] < b[fieldName]) {
            return -1;
        } else {
            return 0;
        }
    });
};

Array.prototype.notEmpty = function() {
    return (Object.keys(this).length > 0);
};

care.controller('uploadIndicatorController', function($scope) {
    $scope.title = $scope.msg('indicators.uploadXml.title');
    $scope.error = typeof springError != 'undefined' ? springError : undefined;
});

care.controller('indicatorListController', function($scope, $http, $dialog, $filter, $errorService, $location) {
    $scope.title = $scope.msg('indicators.title');

    $scope.indicators = [];
    $scope.selectedCategory = null;
    $scope.category = {};

    $scope.fetchIndicators = function() {
        $http.get('api/users/indicators')
            .success(function(indicators) {
                $scope.indicators = indicators;
            }).error(function() {
                $errorService.genericError($scope, 'indicators.list.error.cannotLoadIndicatorList');
            });
    };

    $scope.deleteIndicator = function(indicator) {
        var btns = [{result:'yes', label: $scope.msg('common.yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('common.no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('indicators.list.confirmDelete.header'), $scope.msg('indicators.list.confirmDelete.message', indicator.name), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({
                        method: 'DELETE',
                        url: 'api/indicator/' + indicator.id
                    })
                    .success(function(data, status, headers, config) {
                        $scope.selectedCategory=null;
                        $scope.fetchIndicators();
                        $location.path( "/indicators" );
                    }).error(function(response) {
                        $errorService.genericError($scope, 'indicators.list.error.delete');
                    });
                }
            });
    };

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(category) {
            $scope.category = category;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('common.error'), $scope.msg('categories.error.cannotLoadCategories'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };

    $scope.fetchCategories();

    $scope.fetchIndicatorsByCategoryId = function() {
        $http.get('api/indicator/filter/' + $scope.selectedCategory)
            .success(function(indicators) {
                $scope.indicators = indicators;
            }).error(function() {
                $errorService.genericError($scope, 'indicators.list.error.cannotLoadIndicatorList');
            });
    };

    $scope.recalculate = function() {
        $http.get('api/indicator/calculator/recalculate')
            .success(function() {
                $location.path( "/" );
            })
            .error(function() {
                 $dialog.messageBox("Error", $scope.msg('indicator.list.cannotRecalculate'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });

    };

    $scope.$watch('selectedCategory', function() {
        if($scope.selectedCategory){
            $scope.fetchIndicatorsByCategoryId();
        } else {
            $scope.fetchIndicators();
        }
    });
});

care.controller('createIndicatorController', function($rootScope, $scope, $http, $modal, $dialog, $filter, $location, $routeParams, $errorService) {
    $scope.title = $scope.msg('indicators.title');

    $scope.reportIds = [];
    $scope.indicator = {};
    $scope.indicator.values = [];
    $scope.categories = [];
    $scope.selectedOwners = {};
    $scope.condition = {};
    $scope.ownersValid = false;
    $scope.typeValid = false;
    $scope.categoriesValid = false;
    $scope.newCondition = {};
    $scope.listFields = [];
    $scope.listForms = [];
    $scope.listReportTypes = [];
    $scope.reportTypes = [];
    $scope.listCategories = [];
    $scope.listCondition = [];
    $scope.indicator.area={};
    $scope.selectedState = null;
    $scope.listState = [];
    $scope.selectedDistrict = null;
    $scope.listDistrict = [];
    $scope.showDistrict = false;
    $scope.selectedBlock = null;
    $scope.listBlock = [];
    $scope.showBlock = false;
    $scope.area = {};


    $scope.fetchRoles = function() {
        $http.get('api/users/roles')
            .success(function(roles) {
                $scope.roles = roles;
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadUserList');
            });
    };
    $scope.fetchRoles();

    /*$scope.fetchIndicatorAndFillDetails = function() {
        $http.get('api/indicator/' + $routeParams.indicatorId)
            .success(function(indicator) {
                $scope.indicator.id = indicator.id;
                $scope.indicator.name = indicator.name;
                $scope.indicator.area = indicator.area;
                $scope.indicator.frequency = indicator.defaultFrequency;
                $scope.indicator.computedField=indicator.computedField.id;
                $scope.indicator.reports=indicator.reports;
                $scope.indicator.complexCondition = indicator.complexCondition.id;
                $scope.categories = indicator.categories;
                $scope.indicator.indicatorType = indicator.indicatorType.id;
                for(var i = 0 ; i< indicator.owners.length ; i++){
                    $scope.selectedOwners[indicator.owners[i].id]=true;
                }
                $scope.setAreas();
                $scope.indicator.trend = indicator.trend;
                $scope.setReportTypes(indicator);
                $scope.removeUsedCategories();
                $scope.selectedForm=indicator.computedField.form.id;
                $scope.ownersValid = true;
                $scope.categoriesValid=true;
                $scope.typeValid = true;
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadIndicatorDetails'), [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
            });
    };*/

    $scope.setReportTypes = function(indicator) {
        for(var i = 0 ; i< indicator.reports.length ; i++){
            $scope.reportIds.push(indicator.reports[i].id);
            $scope.reportTypes.push(indicator.reports[i].reportType);
            for (var j = 0 ; j < $scope.listReportTypes.length; j++) {
                var reportType = $scope.listReportTypes[j];
                if(indicator.reports[i].reportType.id == reportType.id)
                    $scope.listReportTypes.splice($scope.listReportTypes.indexOf(reportType),1);
            }
        }
    }

    $scope.setAreas = function() {
        switch ($scope.indicator.area.level.id){
            case 3: $scope.area["selectedState"] = $scope.indicator.area.parentArea.parentArea.id;
                    $scope.area["selectedDistrict"] = $scope.indicator.area.parentArea.id;
                    $scope.area["selectedBlock"] = $scope.indicator.area.id;
                    break;
            case 2: $scope.area["selectedState"] = $scope.indicator.area.parentArea.id;
                    $scope.area["selectedDistrict"] = $scope.indicator.area.id;
                    break;
            case 1: $scope.area["selectedState"] = $scope.indicator.area.id;
                    break;
        }
        $scope.selectedState = $scope.area["selectedState"];
    }

    $scope.fetchStates = function() {
        $http.get('api/users/areas/level/1')
            .success(function(states) {
                states.sortByField('name');
                $scope.listState = states;

                if ($scope.listState.notEmpty() && !$routeParams.indicatorId) {
                    $scope.selectedState = $scope.listState[0].id;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadStateList');
            });
    };

    $scope.fetchAreasByParentAreaId = function(parentAreaId, name) {
        var listName = 'list' + name;
        var selectedItemName = 'selected' + name;
        var msgAffix = 'cannotLoad' + name + 'List';

        $http.get('api/users/areas/' + parentAreaId + '/list')
            .success(function(items) {
                items.sortByField('name');
                $scope[listName] = items;
                $scope[listName].unshift({ id: '-1', name: 'ALL' });
                if($scope.area.hasOwnProperty(selectedItemName)) {
                    $scope[selectedItemName] = $scope.area[selectedItemName];
                    delete $scope.area[selectedItemName];
                } else {
                    $scope[selectedItemName] = '-1';
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.' + msgAffix);
            });
    };

    $scope.resetDropdownDisplay = function(topAreaName) {
        var areaNames = [ 'State', 'District', 'Block', 'HSC', 'AWC' ];
        var found = false;

        for (var i = 0; i < areaNames.length; i++) {
            if (topAreaName == areaNames[i]) {
                found = true;
            }

            $scope['show' + areaNames[i]] = !found;
        }

        $scope['show' + topAreaName] = true;
    };

    $scope.$watch('selectedState', function() {
        if ($scope.selectedState > 0) {
            $scope.fetchAreasByParentAreaId($scope.selectedState, 'District');
        }

        $scope.resetDropdownDisplay(($scope.selectedState > 0) ? 'District' : 'State');
    });

    $scope.$watch('selectedDistrict', function() {
        if ($scope.selectedDistrict > 0) {
            $scope.fetchAreasByParentAreaId($scope.selectedDistrict, 'Block');
        }

        $scope.resetDropdownDisplay(($scope.selectedDistrict > 0) ? 'Block' : 'District');
    });

    $scope.$watch('selectedBlock', function() {
        if ($scope.selectedBlock > 0) {
            $scope.fetchAreasByParentAreaId($scope.selectedBlock, 'HSC');
        }

        $scope.resetDropdownDisplay(($scope.selectedBlock > 0) ? 'HSC' : 'Block');
    });

    $scope.fetchStates();

    $scope.fetchChartTypes = function() {
        $http.get('api/report/type')
            .success(function(chartTypes) {
                chartTypes.sortByField('name');
                $scope.listChartTypes = chartTypes;

                if ($scope.listChartTypes.notEmpty()) {
                    $scope.selectedChartType = "0";
                    $scope.addReportTypeDisabled = false;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadReportTypeList');
            });
    };
    $scope.fetchChartTypes();

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category')
            .success(function(categories) {
                categories.sortByField('name');
                $scope.listCategories = categories;

                if ($scope.listCategories.notEmpty()) {
                    $scope.selectedCategory = "0";
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadCategoryList');
            });
    };
    $scope.fetchCategories();

    $scope.removeUsedCategories = function() {
            for (var i = 0 ; i < $scope.categories.length; i++) {
                for (var j = 0 ; j < $scope.listCategories.length; j++) {
                    var used_category_value = $scope.listCategories[j];
                    if(used_category_value.id == $scope.categories[i].id){
                        $scope.listCategories.splice($scope.listCategories.indexOf(used_category_value),1);
                    }
                }
            }
        };

    $scope.fetchConditions = function() {
        $http.get('api/complexcondition')
            .success(function(conditions) {
                conditions.sortByField('id');
                $scope.listConditions = conditions;
                $scope.listConditions.unshift({ id: '-1', name: '---' });

                if ($scope.listConditions.notEmpty()) {
                    $scope.indicator.Condition = $scope.listConditions[0].id;
                } else {
                    $scope.indicator.Condition = '-1';
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComplexConditionList');
            });
    }
    $scope.fetchConditions();

    $scope.validateOwners = function() {
        $scope.ownersValid = false;

        for (var key in $scope.selectedOwners) {
            if ($scope.selectedOwners[key] === true) {
                $scope.ownersValid = true;
                return;
            }
        }
    }

    $scope.addCategory = function() {
        if ($scope.selectedCategory != null) {
            var category = $scope.listCategories[$scope.selectedCategory];

            if (category != null) {
                $scope.categories.push(category);
                $scope.categoriesValid = true;

                var index = $scope.listCategories.indexOf(category);
                if (index != -1) {
                    $scope.listCategories.splice(index, 1);

                    if (Object.keys($scope.listCategories).length <= 0) {
                        $scope.addCategoryDisabled = true;
                    } else {
                        $scope.selectedCategory = "0";
                    }
                }
            }
        }
    };

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.categories[index]);
        $scope.listCategories.sortByField('name');
        $scope.selectedCategory = "0";
        $scope.categories.splice(index, 1);
        $scope.addCategoryDisabled = false;

        if (Object.keys($scope.categories).length <= 0) {
            $scope.categoriesValid = false;
        }
    }

    $scope.getSelectedRoles = function() {
        var selectedRoles = [];

        for (var key in $scope.selectedRoles) {
            if ($scope.selectedRoles[key] === true) {
                selectedRoles.push(parseInt(key));
            }
        }

        return selectedRoles;
    };

    $scope.getSelectedCategories = function() {
        var selectedCategories = [];

        for (var key in $scope.categories) {
            if (!$scope.categories.hasOwnProperty(key)) {
                continue;
            }
            selectedCategories.push($scope.categories[key].id);
        }

        return selectedCategories;
    };

    $scope.getSelectedArea = function() {
        if ($scope.selectedBlock > 0 && $scope.showBlock) {
            return $scope.selectedBlock;
        } else if ($scope.selectedDistrict > 0 && $scope.showDistrict) {
            return $scope.selectedDistrict;
        } else if ($scope.selectedState > 0) {
            return $scope.selectedState;
        }
    };

    $scope.filterComputedFieldsByNumberType = function(computedFieldList) {
            var filteredFields = [];

            for (var i = 0; i < computedFieldList.length; i++) {
                if (computedFieldList[i].type === "Number") {
                    filteredFields.push(computedFieldList[i]);
                }
            }

            return filteredFields;
        };

    $scope.fetchComputedFields = function () {
        $http.get('api/forms/' + $scope.selectedForm + '/computedfields')
            .success(function(computedFields) {
                $scope.allComputedFields = computedFields;
                $scope.filteredComputedFields = [];
                for(var i = 0; i < computedFields.length; i++) {
                    if(computedFields[i].type == "Number") {
                        $scope.filteredComputedFields.push(computedFields[i]);
                    }
                }
                $scope.filteredComputedFields.sortByField('name');
                $scope.allComputedFields.sortByField('name');
                //$scope.updateComputedFields();
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComputedFieldList');
            });
    };

    /*$scope.updateComputedFields = function() {
        if($scope.listIndicatorTypes.notEmpty()) {
            var type = $scope.listIndicatorTypes[$scope.indicator.indicatorType - 1];
            if(type.name == "Average" || type.name == "Sum") {
                $scope.listComputedFields = $scope.filteredComputedFields;
            } else {
                $scope.listComputedFields = $scope.allComputedFields;
            }

            if ($scope.listComputedFields.notEmpty()) {
                $scope.indicator.computedField = $scope.listComputedFields[0].id;
            }
        }
    };

    $scope.$watch('indicator.indicatorType', $scope.updateComputedFields);*/

    $scope.$watch('selectedForm', function() {
        if ($scope.selectedForm > 0) {
            $scope.fetchComputedFields();
        }
    });

    $scope.createReports = function() {
        var reports = [];

        for (var i = 0; i < $scope.reportTypes.length; i++) {
            var report = {
                reportType: $scope.reportTypes[i]
            };
            if($scope.reportIds){
                report.id=$scope.reportIds[i];
                $scope.reportIds.splice[i,1];
            }

            reports.push(report);
        }

        return reports;
    };

    $scope.submit = function() {
        $scope.indicator.owners = $scope.getSelectedRoles();
        $scope.indicator.categories = $scope.getSelectedCategories();
        $scope.indicator.area = $scope.getSelectedArea();
        $scope.indicator.reports = $scope.createReports();

        if ($scope.indicator.condition == "-1") {
            delete $scope.indicator.condition;
        }

        $http({
            url: "api/indicator" + ($scope.indicator.id ? ('/' + $scope.indicator.id) : ''),
            method: $scope.indicator.id ? "PUT" : "POST",
            data: $scope.indicator,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                $location.path( "/indicators" );
        }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('common.error'), data, [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.addNew = function() {
        $location.path( "/indicators/new" );
    };

    $scope.fetchForms = function() {
        $http.get('api/forms')
            .success(function(forms) {
                forms.sortByField('displayName');
                $scope.listForms = forms;

                if ($scope.listForms.notEmpty() && !$routeParams.indicatorId) {
                    $scope.selectedForm = $scope.listForms[0].id;
                    $scope.newCondition.form = $scope.listForms[0].id;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadFormList');
            });
    };
    $scope.fetchForms();

    $scope.launchDialog = function() {
        $rootScope.indicatorScope = $scope;

        var opts = {
            backdrop: true,
            keyboard: true,
            backdropClick: false,
            templateUrl: 'resources/partials/indicators/newComplexConditionDialog.html',
            controller: 'createComplexConditionController',
            dialogClass: 'modal condition-dialog'
        };

        var dialog = $dialog.dialog(opts);
        dialog.open();
    };

    $scope.launchComputedFieldDialog = function() {
        $rootScope.indicatorScope = $scope;

        var dialog = $modal({
            template: "resources/partials/indicators/newComputedFieldDialog.html",
            persist: true,
            show: true,
            backdrop: "static"
        });
    };

    if ($routeParams.indicatorId) {
        $scope.fetchIndicatorAndFillDetails();
    };
});

care.controller('createComplexConditionController', function($rootScope, $scope, $http, $simplifiedHttpService,
        $dialog, dialog) {
    var indicatorScope = $rootScope.indicatorScope;
    delete $rootScope.indicatorScope;

    $scope.title = $scope.msg('indicators.title');

    $scope.complexCondition = {};
    $scope.complexCondition.conditions = [];
    $scope.listConditions = [];
    $scope.listComparisonSymbols = [];
    $scope.listComputedFields = [];
    $scope.listComputedFields2 = [];
    $scope.newConditions = [];
    $scope.newCondition = {
        field1: null,
        field2: null
    };
    $scope.listComparisonSymbols = [];
    $scope.listConditionTypes = [
        { type: 'dateDiff', code: 'complexCondition.conditionType.dateDiff' },
        { type: 'dateValue', code: 'complexCondition.conditionType.dateValue' },
        { type: 'dateRange', code: 'complexCondition.conditionType.dateRange' },
        { type: 'field', code: 'complexCondition.conditionType.field' },
        { type: 'value', code: 'complexCondition.conditionType.value' },
        { type: 'enumRange', code: 'complexCondition.conditionType.enumRange' }
    ];
    $scope.newCondition.type = 'dateDiff';

    $scope.listDateDiffTypes = [
        { code: 'complexCondition.minutes' },
        { code: 'complexCondition.hours' },
        { code: 'complexCondition.days' },
        { code: 'complexCondition.weeks' },
        { code: 'complexCondition.months' },
        { code: 'complexCondition.years' }
    ];
    $scope.newCondition.dateDiffType = 'complexCondition.days';

    $scope.listForms = indicatorScope.listForms;
    if (indicatorScope.listForms.notEmpty()) {
        $scope.newCondition.form1 = $scope.listForms[0];
        $scope.newCondition.form2 = $scope.listForms[0];
    }

    $scope.fetchComparisonSymbols = function() {
        $simplifiedHttpService.get($scope, 'api/complexcondition/comparisonsymbol',
            'indicators.form.error.cannotLoadComparisonSymbolList', function(comparisonSymbols) {
                comparisonSymbols.sortByField('name');
                $scope.listAllComparisonSymbols = comparisonSymbols;
                $scope.listComparisonSymbols = $scope.listAllComparisonSymbols;

                if ($scope.listComparisonSymbols.notEmpty()) {
                    $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0];
                }
            });
    };
    $scope.fetchComparisonSymbols();

    $scope.swapFields = function() {
        var form1 = $scope.newCondition.form1;
        var form2 = $scope.newCondition.form2;
        var field1 = $scope.newCondition.field1;
        var field2 = $scope.newCondition.field2;
        var fields1 = $scope.listComputedFields1;
        var fields2 = $scope.listComputedFields2;

        if (form1 == null || form2 == null || field1 == null || field2 == null
                || fields1 == null || fields2 == null) {
            return;
        }

        $scope.listComputedFields1 = fields2;
        $scope.listComputedFields2 = fields1;
        $scope.newCondition.form1 = form2;
        $scope.newCondition.form2 = form1;
        $scope.newCondition.field1 = field2;
        $scope.newCondition.field2 = field1;
        $scope.newCondition.swapFields = true;
    };

    $scope.$watch('newCondition.form1', function() {
        if ($scope.newCondition.form1 == null || $scope.newCondition.swapFields === true) {
            if ($scope.newCondition.type == 'value') {
                $scope.newCondition.swapFields = false;
            }
            return;
        }

        $scope.fetchComputedFieldsForForm('form1');
    });

    $scope.$watch('newCondition.form2', function() {
        if ($scope.newCondition.form2 == null || $scope.newCondition.swapFields === true) {
            $scope.newCondition.swapFields = false;
            return;
        }

        $scope.fetchComputedFieldsForForm('form2');
    });

    $scope.$watch('newCondition.type', function() {
        if ($scope.newCondition.type != null) {
            $scope.newCondition.value = null;

            $scope.fetchComputedFieldsForForm('form1');
        }
    });

    $scope.$watch('newCondition.field1', function() {
        if ($scope.newCondition.field1 != null && $scope.newCondition.type == 'value') {
            $scope.newCondition.value = null;

            if ($scope.newCondition.field1.type == 'Boolean'
                || $scope.newCondition.field1.type == 'String') {
                for (var i = 0; i < $scope.listAllComparisonSymbols.length; i++) {
                    if ($scope.listAllComparisonSymbols[i].name == '=') {
                        $scope.listComparisonSymbols = [].concat($scope.listAllComparisonSymbols[i]);
                        $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0];
                        break;
                    }
                }

                return;
            }
        }

        $scope.listComparisonSymbols = $scope.listAllComparisonSymbols;
    });

    $scope.filterComputedFieldsByTypes = function(fields, types) {
        var filteredFields = [];

        for (var f = 0; f < fields.length; f++) {
            var found = false;

            for (var t = 0; t < types.length; t++) {
                if (fields[f].type == types[t]) {
                    found = true;
                    break
                }
            }

            if (found || types.length <= 0) {
                filteredFields.push(fields[f]);
            }
        }

        return filteredFields;
    };

    $scope.fetchComputedFieldsForForm = function(formName) {
        $simplifiedHttpService.get($scope, 'api/forms/' + $scope.newCondition[formName].id + '/computedfields',
            'indicators.form.error.cannotLoadComputedFieldList', function(computedFields) {
                computedFields.sortByField('name');

                if (formName == 'form1') {
                    if ($scope.newCondition.type == 'dateDiff') {
                        $scope.listComputedFields1 = $scope.filterComputedFieldsByTypes(computedFields, [ 'Date' ]);
                    } else if ($scope.newCondition.type == 'field') {
                        $scope.listComputedFields1 = $scope.filterComputedFieldsByTypes(computedFields, [ 'Number', 'Date' ]);
                    } else if ($scope.newCondition.type == 'value') {
                        $scope.listComputedFields1 = $scope.filterComputedFieldsByTypes(computedFields, [ ]);
                    }
                } else if (formName == 'form2') {
                    if ($scope.newCondition.type == 'dateDiff') {
                        $scope.listComputedFields2 = $scope.filterComputedFieldsByTypes(computedFields, [ 'Date' ]);
                    } else if ($scope.newCondition.type == 'field') {
                        $scope.listComputedFields2 = $scope.filterComputedFieldsByTypes(computedFields,
                            [ $scope.newCondition.field1.type ]);
                    }
                }

                if (computedFields.notEmpty()) {
                    if (formName == 'form1') {
                        $scope.newCondition.field1 = $scope.listComputedFields1[0];
                    } else if (formName == 'form2') {
                        $scope.newCondition.field2 = $scope.listComputedFields2[0];
                    }
                }
            });
    };

    $scope.addCondition = function() {
        var condition = $scope.newCondition;
        if (condition.type == 'dateDiff') {
            condition.name = condition.form1.displayName + '.' + condition.field1.name
                + ' ' + condition.comparisonSymbol.name + ' ' + condition.value + ' ' + $scope.msg(condition.dateDiffType)
                + ' ' + $scope.msg('complexCondition.since')
                +  ' ' + condition.form2.displayName + '.' + condition.field2.name
        } else if (condition.type == 'field') {
            condition.name = condition.form1.displayName + '.' + condition.field1.name
                + ' ' + condition.comparisonSymbol.name
                + ' ' + condition.form2.displayName + '.' + condition.field2.name
        } else if (condition.type == 'value') {
            condition.name = condition.form1.displayName + '.' + condition.field1.name
                + ' ' + condition.comparisonSymbol.name
                + ' ' + condition.value
        }

        $scope.newConditions.push(condition);
        $scope.newCondition = {
            type: 'dateDiff',
            form1: $scope.listForms[0],
            form2: $scope.listForms[0],
            field1: $scope.listComputedFields1[0],
            field2: $scope.listComputedFields2[0],
            comparisonSymbol: $scope.listComparisonSymbols[0],
        };
    };

    $scope.removeCondition = function(key) {
        $scope.newConditions.splice(key, 1);
    };

    $scope.getNewConditions = function() {
        for (var i = 0; i < $scope.newConditions.length; i++) {
            if ($scope.newConditions[i].type == 'dateDiff') {
                $scope.newConditions[i].value = moment.duration(
                    $scope.newConditions[i].value, $scope.newConditions[i].dateDiffType).asSeconds();
            } else if ($scope.newConditions[i].type == 'field') {
                delete $scope.newConditions[i].value;
            } else if ($scope.newConditions[i].type == 'value') {
                delete $scope.newConditions[i].field2;
            }

            delete $scope.newConditions[i].name;
            delete $scope.newConditions[i].form1;
            delete $scope.newConditions[i].form2;
            delete $scope.newConditions[i].dateDiffType;
            delete $scope.newConditions[i].swapFields;
        }

        return $scope.newConditions;
    };

    $scope.saveNewComplexCondition = function() {
        $scope.complexCondition.conditions = $scope.getNewConditions();

        $http({
            url: 'api/complexcondition',
            method: 'POST',
            data: $scope.complexCondition,
            headers: { 'Content-Type': 'application/json' },
            dialog: this
        }).success(function(data, status, headers, config) {
                indicatorScope.fetchComplexConditions();
                dialog.close();
        }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('common.error'), data, [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.close = function(result) {
        dialog.close(result);
    };
});

care.controller('recalculateIndicatorsController', function($scope, $http, $dialog, $location) {
    $scope.recalculateIndicators = function() {
        $http({
            url: "api/indicator/calculator/recalculate",
            method: "GET",
            data: null,
            headers: { 'Content-Type': 'application/json' }
        }).error(function() {
            $dialog.messageBox("Error", $scope.msg('menu.recalculate.error'), [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
        });
    };

    $location.path( "api/dashboards" );
    $scope.recalculateIndicators();
});

care.controller('calculatorController', function($scope, $http, $dialog, $location, $errorService) {
    $scope.time = null;

    $scope.fetchDailyTaskTime = function() {
        $http({
            url: "api/indicator/calculator/frequency/daily",
            method: "GET",
        }).success(function(data) {
             $scope.time = data;
        }).error(function() {
            $errorService.genericError($scope, 'indicatorCalculator.calculationTime.error.cannotLoadTime');
        });
    };
    $scope.fetchDailyTaskTime();

    $scope.saveTime = function() {
        $http({
            url: "api/indicator/calculator/frequency/daily",
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            data: $scope.time,
            dialog: this
        }).success(function(data, status, headers, config) {
            config.dialog.dismiss();
        }).error(function(data, status, headers, config) {
            $errorService.genericError($scope, 'indicatorCalculator.calculationTime.error.cannotSaveTime');
        });
    };
});

care.controller('dateDepthController', function($scope, $http, $dialog, $location, $errorService) {
    $scope.dateDepth = [];

    $scope.fetchDateDepth = function() {
        $http({
            url: "api/indicator/calculator/dateDepth",
            method: "GET",
        }).success(function(dateDepth) {
             $scope.dateDepth = moment.utc(dateDepth, "MM-DD-YYYY").toDate();
        }).error(function() {
            $errorService.genericError($scope, 'indicatorCalculator.dateDepth.error.cannotLoadTime');
        });
    };

    $scope.fetchDateDepth();

    $scope.saveDateDepth = function() {
        $http({
            url: "api/indicator/calculator/dateDepth",
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            data: $scope.dateDepth,
            dialog: this
        }).success(function(data, status, headers, config) {
            config.dialog.dismiss();
        }).error(function(data, status, headers, config) {
            $errorService.genericError($scope, 'indicatorCalculator.dateDepth.error.cannotSaveTime');
        });
    };
});

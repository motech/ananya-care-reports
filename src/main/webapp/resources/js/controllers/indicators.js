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

care.controller('indicatorListController', function($scope, $http, $dialog, $filter, $location, $errorService) {
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
    $scope.fetchIndicators();

    $scope.deleteIndicator = function(indicator) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
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
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.load'), [{label: 'Ok', cssClass: 'btn'}]).open();
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
    $scope.listComplexCondition = [];
	$scope.indicator.area={};

    $scope.fetchUsers = function() {
        $http.get('api/users/')
            .success(function(users) {
                $scope.users = users;
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadUserList');
            });
    };
    $scope.fetchUsers();

    $scope.selectedState = null;
    $scope.listState = [];
    $scope.selectedDistrict = null;
    $scope.listDistrict = [];
    $scope.showDistrict = false;
    $scope.selectedBlock = null;
    $scope.listBlock = [];
    $scope.showBlock = false;
    $scope.selectedHSC = null;
    $scope.listHSC = [];
    $scope.showHSC = false;
    $scope.selectedAWC = null;
    $scope.listAWC = [];
    $scope.showAWC = false;
    $scope.area = {};

    $scope.fetchIndicatorAndFillDetails = function() {
        $http.get('api/indicator/' + $routeParams.indicatorId)
            .success(function(indicator) {
                $scope.indicator.id = indicator.id;
                $scope.indicator.name = indicator.name;
                $scope.indicator.area = indicator.area;
                $scope.indicator.frequency = indicator.frequency;
                $scope.indicator.computedField=indicator.computedField.id;
                $scope.indicator.reports=indicator.reports;
                $scope.indicator.complexCondition = indicator.complexCondition.id;
                $scope.categories = indicator.categories;
                $scope.indicator.indicatorType=indicator.indicatorType.id;
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
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadIndicatorDetails'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

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
            case 5: $scope.area["selectedState"] = $scope.indicator.area.parentArea.parentArea.parentArea.parentArea.id;
                    $scope.area["selectedDistrict"] = $scope.indicator.area.parentArea.parentArea.parentArea.id;
                    $scope.area["selectedBlock"] = $scope.indicator.area.parentArea.parentArea.id;
                    $scope.area["selectedHSC"] = $scope.indicator.area.parentArea.id;
                    $scope.area["selectedAWC"] = $scope.indicator.area.id;
                    break;
            case 4: $scope.area["selectedState"] = $scope.indicator.area.parentArea.parentArea.parentArea.id;
                    $scope.area["selectedDistrict"] = $scope.indicator.area.parentArea.parentArea.id;
                    $scope.area["selectedBlock"] = $scope.indicator.area.parentArea.id;
                    $scope.area["selectedHSC"] = $scope.indicator.area.id;
                    break;
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
    
    $scope.$watch('selectedHSC', function() {
        if ($scope.selectedHSC > 0) {
            $scope.fetchAreasByParentAreaId($scope.selectedHSC, 'AWC');
        }

        $scope.resetDropdownDisplay(($scope.selectedHSC > 0) ? 'AWC' : 'HSC');
    });

    $scope.fetchStates();

    $scope.fetchIndicatorTypes = function() {
        $http.get('api/indicator/type')
            .success(function(indicatorTypes) {
                indicatorTypes.sortByField('name');
                $scope.listIndicatorTypes = indicatorTypes;

                if ($scope.listIndicatorTypes.notEmpty()) {
                    $scope.indicator.indicatorType = $scope.listIndicatorTypes[0].id;
                    $scope.typeValid = true;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadIndicatorTypeList');
            });
    };
    $scope.fetchIndicatorTypes();

    $scope.fetchReportTypes = function() {
        $http.get('api/report/type')
            .success(function(reportTypes) {
                reportTypes.sortByField('name');
                $scope.listReportTypes = reportTypes;

                if ($scope.listReportTypes.notEmpty()) {
                    $scope.selectedReportType = "0";
                    $scope.addReportTypeDisabled = false;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadReportTypeList');
            });
    };
    $scope.fetchReportTypes();

    $scope.addReportType = function() {
        if ($scope.selectedReportType != null) {
            var reportType = $scope.listReportTypes[$scope.selectedReportType];

            if (reportType != null) {
                $scope.reportTypes.push(reportType);

                var index = $scope.listReportTypes.indexOf(reportType);
                if (index != -1) {
                    $scope.listReportTypes.splice(index, 1);

                    if ($scope.listReportTypes.notEmpty()) {
                        $scope.selectedReportType = "0";
                    }
                }
            }
        }
    };

    $scope.removeReportType = function(index) {
        $scope.listReportTypes.push($scope.reportTypes[index]);
        $scope.listReportTypes.sortByField('name');
        $scope.selectedReportType = "0";
        $scope.reportTypes.splice(index, 1);
    }

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

    $scope.fetchComplexConditions = function() {
        $http.get('api/complexcondition')
            .success(function(conditions) {
                conditions.sortByField('id');
                $scope.listComplexConditions = conditions;
                $scope.listComplexConditions.unshift({ id: '-1', name: '---' });

                if ($scope.listComplexConditions.notEmpty()) {
                    $scope.indicator.complexCondition = $scope.listComplexConditions[0].id;
                } else {
                    $scope.indicator.complexCondition = '-1';
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComplexConditionList');
            });
    }
    $scope.fetchComplexConditions();

    $scope.listFrequencies = [
        { name: "1 day", value: "1" },
        { name: "2 days", value: "2" },
        { name: "30 days", value: "30" },
        { name: "60 days", value: "60" },
        { name: "180 days", value: "180" }
    ];
    $scope.indicator.frequency = "30";

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

    $scope.getSelectedOwners = function() {
        var selectedOwners = [];

        for (var key in $scope.selectedOwners) {
            if ($scope.selectedOwners[key] === true) {
                selectedOwners.push(parseInt(key));
            }
        }

        return selectedOwners;
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
        if ($scope.selectedAWC > 0 && $scope.showAWC) {
            return $scope.selectedAWC;
        } else if ($scope.selectedHSC > 0 && $scope.showHSC) {
            return $scope.selectedHSC;
        } else if ($scope.selectedBlock > 0 && $scope.showBlock) {
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
                computedFields.sortByField('name');
                $scope.listComputedFields = $scope.filterComputedFieldsByNumberType(computedFields);

                if ($scope.listComputedFields.notEmpty() && !$routeParams.indicatorId) {
                    $scope.indicator.computedField = $scope.listComputedFields[0].id;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComputedFieldList');
            });
    };

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
        $scope.indicator.owners = $scope.getSelectedOwners();
        $scope.indicator.categories = $scope.getSelectedCategories();
        $scope.indicator.area = $scope.getSelectedArea();
        $scope.indicator.reports = $scope.createReports();

        if ($scope.indicator.complexCondition == "-1") {
            delete $scope.indicator.complexCondition;
        }

        $http({
            url: "api/indicator" + ($scope.indicator.id ? ('/' + $scope.indicator.id) : ''),
            method: "PUT",
            data: $scope.indicator,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                $location.path( "/indicators" );
            }).error(function(response) {
                $errorService.apiError($scope, response);
        });
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

        var dialog = $modal({
            template: "resources/partials/indicators/newComplexConditionDialog.html",
            persist: true,
            show: true,
            backdrop: "static"
        });
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

care.controller('createComplexConditionController', function($rootScope, $scope, $http, $dialog) {
    var indicatorScope = $rootScope.indicatorScope;
    delete $rootScope.indicatorScope;

    $rootScope.pageTitle = $scope.msg('indicators.title');

    $scope.complexCondition = {};
    $scope.complexCondition.conditions = [];
    $scope.listConditions = [];
    $scope.listComparisonSymbols = [];
    $scope.listComputedFields = [];
    $scope.newConditions = [];
    $scope.showForm = false;
    $scope.newCondition = {
        form: -1,
        comparisonSymbol: -1,
        field: -1,
        comparisonValue: null
    };

    $scope.listForms = indicatorScope.listForms;
    if ($scope.listForms.notEmpty()) {
        $scope.newCondition.form = $scope.listForms[0];
    }

    $scope.fetchComparisonSymbols = function() {
        $http.get('api/complexcondition/comparisonsymbol')
            .success(function(comparisonSymbols) {
                comparisonSymbols.sortByField('name');
                $scope.listAllComparisonSymbols = comparisonSymbols;
                $scope.listComparisonSymbols = $scope.listAllComparisonSymbols;

                if ($scope.listComparisonSymbols.notEmpty()) {
                    $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0];
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComparisonSymbolList');
            });
    };
    $scope.fetchComparisonSymbols();

    $scope.removeExistingComputedFields = function(computedFields) {
        for (var c = computedFields.length - 1; c >= 0; c--) {
            var field = computedFields[c];

            for (nc = 0; nc < $scope.newConditions.length; nc++) {
                var condition = $scope.newConditions[nc];

                if (field.id == condition.computedField.id) {
                    computedFields.splice(c, 1);
                }
            }
        }

        return computedFields;
    };

    $scope.fetchComputedFields = function () {
        $http.get('api/forms/' + indicatorScope.selectedForm + '/computedfields')
            .success(function(computedFields) {
                computedFields.sortByField('name');
                $scope.listComputedFields = $scope.removeExistingComputedFields(computedFields);

                if ($scope.listComputedFields.notEmpty()) {
                    $scope.newCondition.computedField = $scope.listComputedFields[0];
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotLoadComputedFieldList');
            });
    };

    $scope.filterComparisonSymbolsByFieldType = function(fieldType) {
        if (fieldType != "Number" && fieldType != "Date") {
            for (var i = 0; i < $scope.listAllComparisonSymbols.length; i++) {
                if ($scope.listAllComparisonSymbols[i].name == "=") {
                    return [
                        $scope.listAllComparisonSymbols[i]
                    ];
                }
            }
        }

        return $scope.listAllComparisonSymbols;
    };

    $scope.$watch('newCondition.form', function() {
        $scope.fetchComputedFields();
    });

    $scope.$watch('newCondition.computedField', function() {
        $scope.newCondition.comparisonValue = null;

        if ($scope.newCondition.computedField != null) {
            $scope.listComparisonSymbols = $scope.filterComparisonSymbolsByFieldType($scope.newCondition.computedField.type);

            if ($scope.listComparisonSymbols != null && $scope.listComparisonSymbols.notEmpty()) {
                $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0];
            }
        }
    });

    $scope.addCondition = function() {
        var condition = {
            form: $scope.newCondition.form,
            comparisonSymbol: $scope.newCondition.comparisonSymbol,
            computedField: $scope.newCondition.computedField,
            comparisonValue: $scope.newCondition.comparisonValue
        };

        if (condition.computedField.type == "Date") {
            condition.comparisonValue = moment(condition.comparisonValue).format("MM/DD/YYYY");
        }

        for (var i = 0; i < $scope.listComputedFields.length; i++) {
            if ($scope.listComputedFields[i].id == condition.computedField.id) {
                $scope.listComputedFields.splice(i, 1);

                if ($scope.listComputedFields.notEmpty()) {
                    $scope.newCondition.computedField = $scope.listComputedFields[0];
                }

                break;
            }
        }

        $scope.newConditions.push(condition);
        $scope.newCondition.comparisonValue = null;
    };

    $scope.removeCondition = function(key) {
        var condition = $scope.newConditions[key];

        if (condition.form.id == $scope.newCondition.form.id) {
            $scope.listComputedFields.push(condition.computedField);
            $scope.listComputedFields.sortByField('name');
            $scope.newCondition.computedField = $scope.listComputedFields[0];
        }

        $scope.newConditions.splice(key, 1);
    };

    $scope.clearAllConditions = function() {
        for (var i = 0; i < $scope.newConditions.length; i++) {
            var condition = $scope.newConditions[i];

            if (condition.form.id == $scope.newCondition.form.id) {
                $scope.listComputedFields.push(condition.computedField);
            }
        }

        if ($scope.listComputedFields.notEmpty()) {
            $scope.listComputedFields.sortByField('name');
            $scope.newCondition.computedField = $scope.listComputedFields[0];
        }
        $scope.newConditions.length = 0;
    };

    $scope.getNewConditions = function() {
        for (var i = 0; i < $scope.newConditions.length; i++) {
            delete $scope.newConditions[i].form;
        }

        return $scope.newConditions;
    };

    $scope.saveNewComplexCondition = function() {
        $scope.complexCondition.conditions = $scope.getNewConditions();

        $http({
            url: "api/complexcondition",
            method: "POST",
            data: $scope.complexCondition,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                indicatorScope.fetchComplexConditions();
                $location.path( "/indicators" );
            }).error(function() {
                $errorService.genericError($scope, 'indicators.form.error.cannotCreateComplexCondition');
        });
    };
});

care.controller('createComputedFieldController', function($rootScope, $scope, $http, $dialog) {
    var indicatorScope = $rootScope.indicatorScope;
    delete $rootScope.indicatorScope;

    $rootScope.pageTitle = $scope.msg('indicators.title');

    $scope.listOperators = [];
    $scope.listFields = [];
    $scope.selectedFields = [];
    $scope.newField = {};
    $scope.newField.type = "Number";

    $scope.filterFieldsByNumberType = function(fieldList) {
        var filteredFields = [];

        for (var i = 0; i < fieldList.length; i++) {
            if (fieldList[i].type === $scope.newField.type) {
                filteredFields.push(fieldList[i]);
            }
        }

        return filteredFields;
    };

    $scope.fetchOperators = function() {
        $http.get('api/complexcondition/operatortype')
            .success(function(operators) {
                operators.sortByField('name');
                $scope.listOperators = operators;

                if ($scope.listOperators.notEmpty()) {
                    $scope.selectedOperator = $scope.listOperators[0];
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.computedFieldDialog.error.cannotLoadOperatorList');
            });
    };
    $scope.fetchOperators();

    $scope.fetchFields = function() {
        $http.get('api/forms/' + indicatorScope.selectedForm + '/fields')
            .success(function(fields) {
                fields.sortByField('name');
                $scope.listFields = $scope.filterFieldsByNumberType(fields);

                if ($scope.listFields.notEmpty()) {
                    $scope.selectedField = $scope.listFields[0].id;
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.computedFieldDialog.error.cannotLoadFieldList');
            });
    };
    $scope.fetchFields();

    $scope.addField = function() {
        if (!$scope.selectedField) {
            return;
        }

        var index = -1;
        for (var i = 0; i < $scope.listFields.length; i++) {
            if ($scope.listFields[i].id == $scope.selectedField) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            var field = {
                operator: ($scope.selectedFields.length > 0) ? $scope.selectedOperator : null,
                field: $scope.listFields[index]
            };

            $scope.selectedFields.push(field);
            $scope.listFields.splice(index, 1);
            if ($scope.listFields.notEmpty()) {
                $scope.selectedField = $scope.listFields[0].id;
            }
        }
    };

    $scope.removeField = function(key) {
        $scope.listFields.push($scope.selectedFields[key].field);
        $scope.listFields.sortByField('name');
        $scope.selectedField = $scope.listFields[0].id;

        $scope.selectedFields.splice(key, 1);

        if (key == 0 && $scope.selectedFields.notEmpty()) {
            $scope.selectedFields[0].operator = null;
        }
    };

    $scope.createFieldOperations = function() {
        var fields = $scope.selectedFields;
        var fieldOperations = [];

        for (var i = 0; i < $scope.selectedFields.length; i++) {
            if (fieldOperations.length > 1 && i >= $scope.selectedFields.length - 1) {
                break;
            }

            var fieldOperation = {
                field1: fields[i].field,
                field2: (i + 1 < $scope.selectedFields.length) ? fields[i + 1].field : null,
                operatorType: (i + 1 < $scope.selectedFields.length) ? fields[i + 1].operator : null,
            };

            fieldOperations.push(fieldOperation);
        }

        return fieldOperations;
    };

    $scope.saveNewComputedField = function() {
        $scope.newField.form = indicatorScope.selectedForm;
        $scope.newField.fieldOperations = $scope.createFieldOperations();

        $http({
            url: "api/computedfields",
            method: "POST",
            data: $scope.newField,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
            indicatorScope.fetchComputedFields();
        }).error(function(response) {
            $errorService.apiError($scope, response);
        });
    };
});

care.controller('recalculateIndicatorsController', function($scope, $http, $dialog, $location) {
    $scope.recalculateIndicators = function() {
        $http({
            url: "api/indicator/recalculate",
            method: "GET",
            data: null,
            headers: { 'Content-Type': 'application/json' }
        }).error(function() {
            $dialog.messageBox("Error", $scope.msg('dashboard.recalculate.error'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };

    $location.path( "api/dashboards" );
    $scope.recalculateIndicators();
});

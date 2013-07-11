var care = angular.module('care');

Array.prototype.sortByName = function() {
    this.sort(function(a, b) {
        return (a.name > b.name);
    });
};

Array.prototype.sortById = function() {
    this.sort(function(a, b) {
        return (a.id < b.id);
    });
};

care.controller('indicatorListController', function($scope, $http, $dialog, $filter, $location) {
    $scope.indicators = [];

    $scope.fetchIndicators = function() {
        $http.get('api/users/indicators')
            .success(function(indicators) {
                $scope.indicators = indicators;
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.list.error.cannotLoadIndicatorList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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
                        $scope.fetchIndicators();
                    }).error(function(response) {
                        $dialog.messageBox("Error", $scope.msg('indicators.list.error.delete'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
                }
            });
    };
});

care.controller('createIndicatorController', function($rootScope, $scope, $http, $modal, $dialog, $filter, $location) {
    $scope.title = $scope.msg('indicators.title');

    $scope.addCategoryDisabled = true;
    $scope.addDimensionDisabled = true;

    $scope.indicator = {};
    $scope.indicator.complexConditions = [];
    $scope.indicator.values = [];
    $scope.complexConditions = [];
    $scope.categories = [];
    $scope.selectedOwners = {};
    $scope.condition = {};
    $scope.ownersValid = false;
    $scope.typeValid = false;
    $scope.categoriesValid = false;
    $scope.conditionsValid = false;
    $scope.newCondition = {};
    $scope.listFields = [];

    $scope.fetchUsers = function() {
        $http.get('api/users/')
            .success(function(users) {
                $scope.users = users;
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadUserList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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

    $scope.fetchStates = function() {
        $http.get('api/users/areas/level/1')
            .success(function(states) {
                states.sortByName();
                $scope.listState = states;

                if (Object.keys($scope.listState).length > 0) {
                    $scope.selectedState = $scope.listState[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadStateList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchAreasByParentAreaId = function(parentAreaId, name) {
        var listName = 'list' + name;
        var selectedItemName = 'selected' + name;
        var msgAffix = 'cannotLoad' + name + 'List';

        $http.get('api/users/areas/' + parentAreaId + '/list')
            .success(function(items) {
                items.sortByName();
                $scope[listName] = items;
                $scope[listName].unshift({ id: '-1', name: 'ALL' });
                $scope[selectedItemName] = '-1';
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.' + msgAffix), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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
                indicatorTypes.sortByName();
                $scope.listIndicatorTypes = indicatorTypes;

                if (Object.keys($scope.listIndicatorTypes).length > 0) {
                    $scope.indicator.indicatorType = $scope.listIndicatorTypes[0].id;
                    $scope.typeValid = true;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadIndicatorTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchIndicatorTypes();

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category')
            .success(function(categories) {
                categories.sortByName();
                $scope.listCategories = categories;

                if (Object.keys($scope.listCategories).length > 0) {
                    $scope.selectedCategory = "0";
                    $scope.addCategoryDisabled = false;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadCategoryList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchCategories();

    $scope.removeUsedConditions = function() {
        var keyCount = Object.keys($scope.listComplexConditions).length;
        for (var i = keyCount - 1; i >= 0; i--) {
            var condition_value = $scope.listComplexConditions[i];
            if (!$scope.listComplexConditions.hasOwnProperty(i)) {
                continue;
            }

            for (var used_condition_key in $scope.complexConditions) {
                var used_condition_value = $scope.complexConditions[used_condition_key];
                if (!$scope.complexConditions.hasOwnProperty(used_condition_key)) {
                    continue;
                }

                if (condition_value.id == used_condition_value.id) {
                    $scope.listComplexConditions.splice(i, 1);
                }
            }
        }
    };

    $scope.fetchComplexConditions = function() {
        $http.get('api/complexcondition')
            .success(function(conditions) {
                conditions.sortById();
                $scope.listComplexConditions = conditions;

                if (Object.keys($scope.listComplexConditions).length > 0) {
                    $scope.removeUsedConditions();
                    $scope.selectedComplexCondition = "0";
                    $scope.addDimensionDisabled = false;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadComplexConditionList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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
        $scope.listCategories.sortByName();
        $scope.selectedCategory = "0";
        $scope.categories.splice(index, 1);
        $scope.addCategoryDisabled = false;

        if (Object.keys($scope.categories).length <= 0) {
            $scope.categoriesValid = false;
        }
    }

    $scope.addComplexCondition = function() {
        if ($scope.selectedComplexCondition != null) {
            var complexCondition = $scope.listComplexConditions[$scope.selectedComplexCondition];

            if (complexCondition != null) {
                $scope.complexConditions.push(complexCondition);
                $scope.conditionsValid = true;

                var index = $scope.listComplexConditions.indexOf(complexCondition);
                if (index != -1) {
                    $scope.listComplexConditions.splice(index, 1);

                    if (Object.keys($scope.listComplexConditions).length <= 0) {
                        $scope.addDimensionDisabled = true;
                    }
                }
            }
        }
    };

    $scope.removeComplexCondition = function(index) {
        $scope.listComplexConditions.push($scope.complexConditions[index]);
        $scope.complexConditions.splice(index, 1);
        $scope.addDimensionDisabled = false;

        if (Object.keys($scope.complexConditions).length <= 0) {
            $scope.conditionsValid = false;
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

    $scope.submit = function() {
        $scope.indicator.owners = $scope.getSelectedOwners();
        $scope.indicator.categories = $scope.getSelectedCategories();
        $scope.indicator.area = $scope.getSelectedArea();

        $http({
            url: "api/indicator",
            method: "POST",
            data: $scope.indicator,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                $location.path( "/indicators" );
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotCreateIndicator'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.fetchOperatorTypes = function() {
        $http.get('api/complexcondition/operatortype')
            .success(function(operatorTypes) {
                operatorTypes.sortByName();
                $scope.listOperatorTypes = operatorTypes;

                if (Object.keys($scope.listOperatorTypes).length > 0) {
                    $scope.newCondition.operatorType = $scope.listOperatorTypes[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadOperatorTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchForms = function() {
        $http.get('api/forms')
            .success(function(forms) {
                forms.sort(function(a,b) { return a.displayName > b.displayName; });
                $scope.listForms = forms;

                if (Object.keys($scope.listForms).length > 0) {
                    $scope.selectedForm = $scope.listForms[0].id;
                    $scope.newCondition.form = $scope.listForms[0].id;
                    $scope.listFields = $scope.listForms[0].fields;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadFormList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchForms();

    $scope.fetchComparisonSymbols = function() {
        $http.get('api/complexcondition/comparisonsymbol')
            .success(function(comparisonSymbols) {
                comparisonSymbols.sortByName();
                $scope.listComparisonSymbols = comparisonSymbols;

                if (Object.keys($scope.listComparisonSymbols).length > 0) {
                    $scope.newCondition.comparisonSymbol = $scope.listComparisonSymbols[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadComparisonSymbolList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchFields = function() {
        if ($scope.newCondition.form == null) {
            return;
        }

        $http.get('api/forms/' + $scope.newCondition.form)
            .success(function(form) {
                form.fields.sortByName();
                $scope.listFields = form.fields;

                if (Object.keys($scope.listFields).length > 0) {
                    $scope.selectedField = $scope.listFields[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadFieldList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.launchDialog = function() {
        $scope.fetchOperatorTypes();
        $scope.fetchComparisonSymbols();

        var dialog = $modal({
            template: "resources/partials/indicators/newComplexConditionDialog.html",
            persist: true,
            show: true,
            backdrop: "static",
            scope: $scope
        });
    };

    $scope.newCondition.fields = [];
    $scope.addField = function() {
        var field = $scope.selectedField;
        var index = -1;
        for (var i = 0; i < $scope.listFields.length; i++) {
            if ($scope.listFields[i].id == field) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            $scope.newCondition.fields.push($scope.listFields[index]);
            $scope.newCondition.fields.sortByName();
            $scope.listFields.splice(index, 1);

            if (Object.keys($scope.listFields).length > 0) {
                $scope.selectedField = $scope.listFields[0].id;
            }
        }
    };

    $scope.removeField = function(field) {
        var index = -1;
        for (var i = 0; i < $scope.newCondition.fields.length; i++) {
            if ($scope.newCondition.fields[i].id == field) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            $scope.listFields.push($scope.newCondition.fields[index]);
            $scope.listFields.sortByName();
            $scope.selectedField = $scope.listFields[0].id;
            $scope.newCondition.fields.splice(index, 1);
        }
    };

    $scope.saveNewComplexCondition = function() {
        $scope.newCondition.indicators = [];

        $http({
            url: "api/complexcondition",
            method: "POST",
            data: $scope.newCondition,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
                $scope.fetchComplexConditions();
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotCreateNewComplexCondition'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.$watch('newCondition.form', function() {
        $scope.newCondition.fields = [];
        $scope.fetchFields();
    });

    $scope.launchComputedFieldDialog = function() {
        $rootScope.indicatorScope = $scope;

        var dialog = $modal({
            template: "resources/partials/indicators/newComputedFieldDialog.html",
            persist: true,
            show: true,
            backdrop: "static"
        });
    };
});

care.controller('createComputedFieldController', function($rootScope, $scope, $http, $dialog) {
    var indicatorScope = $rootScope.indicatorScope;
    delete $rootScope.indicatorScope;

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
                operators.sortByName();
                $scope.listOperators = operators;

                if (Object.keys($scope.listOperators).length > 0) {
                    $scope.selectedOperator = $scope.listOperators[0];
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.computedFieldDialog.error.cannotLoadOperatorList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };
    $scope.fetchOperators();

    $scope.fetchFields = function() {
        $http.get('api/forms/' + indicatorScope.selectedForm)
            .success(function(form) {
                form.fields.sortByName();
                $scope.listFields = $scope.filterFieldsByNumberType(form.fields);

                if (Object.keys($scope.listFields).length > 0) {
                    $scope.selectedField = $scope.listFields[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.computedFieldDialog.error.cannotLoadFieldList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
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
            if (Object.keys($scope.listFields).length > 0) {
                $scope.selectedField = $scope.listFields[0].id;
            }
        }
    };

    $scope.removeField = function(key) {
        $scope.listFields.push($scope.selectedFields[key].field);
        $scope.listFields.sortByName();
        $scope.selectedField = $scope.listFields[0].id;

        $scope.selectedFields.splice(key, 1);

        if (key == 0 && Object.keys($scope.selectedFields).length > 0) {
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
        }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.computedFieldDialog.error.cannotCreateNewComputedField'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };
});

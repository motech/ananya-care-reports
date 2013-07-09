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

care.controller('createIndicatorController', function($scope, $http, $modal, $dialog, $filter, $location) {
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
                    }
                }
            }
        }
    };

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.categories[index]);
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
                    $scope.newCondition.form = $scope.listForms[0].id;
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadFormTypeList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchFields = function() {
        var formId = $scope.newCondition.form;
        if (isNaN(formId) || !isFinite(formId)) {
            return;
        }

        $http.get('api/forms/' + formId + "/fields")
            .success(function(fields) {
                fields.sort(function(a,b) { return a.displayName > b.displayName; });
                $scope.listFields = fields;

                if (Object.keys($scope.listFields).length > 0) {
                    $scope.newCondition.field = $scope.listFields[0];
                }
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('indicators.form.error.cannotLoadFieldList'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

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

    $scope.launchDialog = function() {
        $scope.fetchOperatorTypes();
        $scope.fetchForms();
        $scope.fetchComparisonSymbols();

        var dialog = $modal({
            template: "resources/partials/newComplexConditionDialog.html",
            persist: true,
            show: true,
            backdrop: "static",
            scope: $scope
        });
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
        $scope.fetchFields();
    });
});

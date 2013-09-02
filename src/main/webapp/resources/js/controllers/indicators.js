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

care.controller('createIndicatorController', function($rootScope, $scope, $http, $modal,
                                                      $dialog, $location, $errorService, $route) {
    $scope.title = $scope.msg('indicators.title');
    $scope.indicator = {
        categories: []
    };

    $scope.initializeInputFields = function() {
        $scope.listCategories = $scope.formData.categories;

        $scope.selectedCategory = $scope.listCategories[0];
        $scope.selectedChartType = $scope.formData.reportTypes[0].id;
        $scope.selectedForm = $scope.formData.forms[0].id;
        $scope.formData.complexConditions.unshift({ id: -1, name: '---' });
        $scope.indicator.complexCondition = $scope.formData.complexConditions[0].id;
    };

    $scope.sortFormDataArrays = function() {
        $scope.formData.categories.sortByField('name');
        $scope.formData.roles.sortByField('name');
        $scope.formData.forms.sortByField('displayName');
        $scope.formData.complexConditions.sortByField('name');
        $scope.formData.reportTypes.sortByField('name');
    };

    $http.get("api/indicator/creationform").success(function(indicatorCreationFormDto) {
        $scope.formData = indicatorCreationFormDto;
        $scope.sortFormDataArrays();
        $scope.initializeInputFields();
    }).error(function() {
        $errorService.genericError($scope, 'indicators.form.error.cannotLoadFormDetails');
    });

    $scope.fetchComputedFieldsForForm = function(formId) {
        $http.get('api/forms/' + formId + '/computedfields').success(function(computedFields) {
            computedFields.sortByField('name');
            $scope.formData.computedFields = computedFields;
            if(computedFields.length > 0) {
                $scope.indicator.computedField = computedFields[0].id;
            }
        }).error(function() {
            $errorService.genericError($scope, 'indicators.form.error.cannotLoadComputedFieldList');
        });
    };

    $scope.$watch('selectedForm', function(newValue, oldValue) {
        if (newValue != null) {
            $scope.fetchComputedFieldsForForm($scope.selectedForm);
        }
    });

    $scope.addCategory = function() {
        $scope.indicator.categories.push($scope.selectedCategory);
        $scope.indicator.categories.sortByField('name');

        for (var i = $scope.listCategories.length - 1; i >= 0; i--) {
            if ($scope.listCategories[i].id === $scope.selectedCategory.id) {
                $scope.listCategories.splice(i, 1);
                $scope.selectedCategory = $scope.listCategories[0];
                break;
            }
        }
    };

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.indicator.categories[index]);
        $scope.listCategories.sortByField('name');
        $scope.indicator.categories.splice(index, 1);
        $scope.selectedCategory = $scope.listCategories[0];
    };

    $scope.addNewIndicator = function() {
        $route.reload();
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

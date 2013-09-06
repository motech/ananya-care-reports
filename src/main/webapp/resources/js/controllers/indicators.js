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
    $scope.category = {};

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
            $scope.selectedCategory = null;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('common.error'), $scope.msg('categories.error.cannotLoadCategories'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };

    $scope.fetchCategories();

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
        if($scope.selectedCategory) {
            for(var i = 0; i < $scope.category.length; i++) {
                if($scope.category[i].id == $scope.selectedCategory) {
                    $scope.indicators = $scope.category[i].indicators;
                    break;
                }
            }
        } else {
            $scope.indicators = new Array();
            for(var i = 0; i < $scope.category.length; i++) {
                $scope.indicators = $scope.indicators.concat($scope.category[i].indicators);
            }
        }
    });
});

care.controller('createIndicatorController', function($rootScope, $scope, $http, $modal,
                                                      $dialog, $location, $errorService, $route) {
    $scope.title = $scope.msg('indicators.title');
    $scope.indicator = {
        categories: [],
        owners: [],
        charts: []
    };

    $scope.initializeInputFields = function() {
        $scope.listCategories = $scope.formData.categories;
        $scope.listCharts = $scope.formData.reportTypes;

        $scope.indicator.level = $scope.formData.levels[0].id;
        $scope.indicator.frequency = $scope.formData.frequencies[0].id;
        $scope.selectedChart = $scope.formData.reportTypes[0];
        $scope.selectedCategory = $scope.listCategories[0];
        $scope.selectedForm = $scope.formData.forms[0].id;

        for(var i = 0; i < $scope.formData.roles.length; i++) {
            var id = $scope.formData.roles[i].id;
            $scope.indicator.owners[id] = false;
        }
    };

    $scope.sortFormDataArrays = function() {
        $scope.formData.categories.sortByField('name');
        $scope.formData.roles.sortByField('name');
        $scope.formData.forms.sortByField('displayName');
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

    $scope.addChart = function() {
        $scope.indicator.charts.push($scope.selectedChart);

        for (var i = $scope.listCharts.length - 1; i >= 0; i--) {
            if ($scope.listCharts[i].id === $scope.selectedChart.id) {
                $scope.listCharts.splice(i, 1);
                $scope.selectedChart = $scope.listCharts[0];
                break;
            }
        }
    }

    $scope.removeCategory = function(index) {
        $scope.listCategories.push($scope.indicator.categories[index]);
        $scope.listCategories.sortByField('name');
        $scope.indicator.categories.splice(index, 1);
        $scope.selectedCategory = $scope.listCategories[0];
    };

    $scope.removeChart = function(index) {
        $scope.listCharts.push($scope.indicator.charts[index]);
        $scope.listCharts.sortByField('name');
        $scope.indicator.charts.splice(index, 1);
        $scope.selectedChart = $scope.listCharts[0];
    };

    $scope.addNewIndicator = function() {
        $route.reload();
    };

    $scope.selectAllRoles = function() {
         if($scope.isAllSelected() == true) {
             var check = false;
         } else {
             var check = true;
         }

        for(var i = 0; i < $scope.formData.roles.length; i++) {
            var id = $scope.formData.roles[i].id;
            $scope.indicator.owners[id] = check;
        }
    };

    $scope.sumRoles = function() {
        var sum = 0;
        if($scope.formData != undefined) {
            for (var i = 0; i < $scope.formData.roles.length; i++) {
                 var id = $scope.formData.roles[i].id;
                 if($scope.indicator.owners[id] === true)
                    sum++;
             }
         }
         return sum;
    }

    $scope.isAllSelected = function() {
        var sum = $scope.sumRoles();
        return sum != 0 && $scope.formData.roles.length == sum;
    };

    $scope.isAtLeastOneSelected = function() {
        return $scope.sumRoles() > 0;
    }
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

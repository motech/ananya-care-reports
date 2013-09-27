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
    $scope.title = 'indicators.uploadXml.title';
    $scope.error = typeof springError != 'undefined' ? springError : undefined;
    $scope.errorHeader = typeof springErrorHeader != 'undefined' ? springErrorHeader : undefined;
    $scope.errorType = typeof springErrorType != 'undefined' ? springErrorType : undefined;
    $scope.showStackTrace = false;
    $scope.showHideButtonLabel = $scope.msg('common.show');
    console.log($scope.error);
    console.log($scope.errorType);

    $scope.toggleStackTraceDisplay = function() {
        $scope.showStackTrace = !$scope.showStackTrace;
        $scope.showHideButtonLabel = ($scope.showStackTrace) ? $scope.msg('common.hide') : $scope.msg('common.show');
    }
});

care.controller('indicatorListController', function($scope, $http, $dialog, $filter, $errorService, $location) {
    $scope.title = 'indicators.title';

    $scope.classification = [];
    $scope.indicators = [];

    $scope.$watch('selectedClassification', function() {
        if ($scope.selectedClassification != null) {
            for (var i = 0; i < $scope.classification.length; i++) {
                if ($scope.classification[i].id == $scope.selectedClassification) {
                    $scope.indicators = $scope.classification[i].indicators;
                    break;
                }
            }
        }
    }, true);

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
                        $scope.fetchClassifications();
                        for(var i = 0; i < $scope.indicators.length; i++) {
                            if($scope.indicators[i] == indicator) {
                                $scope.indicators.splice(i, 1);
                            }
                        }
                    }).error(function(response) {
                        $errorService.genericError($scope, 'indicators.list.error.delete');
                    });
                }
            });
    };

    $scope.fetchClassifications = function() {
        $http.get('api/indicator/classification').success(function(classification) {
            $scope.classification = new Array();
            var ind = new Array();
            for(var i = 0; i < classification.length; i++) {
                ind = ind.concat(classification[i].indicators);
            }
            $scope.classification = [{
                id: 0,
                indicators: ind,
                name: $scope.msg('indicators.list.allClassifications')
            }].concat(classification);
            $scope.selectedClassification = 0;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('common.error'), $scope.msg('classifications.error.cannotLoadClassifications'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };

    $scope.fetchClassifications();

    $scope.recalculate = function() {
        $http.get('api/indicator/calculator/recalculate/' + $scope.selectedClassification)
            .success(function() {
                $location.path( "/" );
            })
            .error(function() {
                 $dialog.messageBox("Error", $scope.msg('indicator.list.cannotRecalculate'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });

    };

    $scope.isAnyComputed = function() {
        if($scope.indicators.length == 0) {
            return true;
        }

        for(var i = 0; i < $scope.indicators.length; i++) {
            if(!$scope.indicators[i].isComputed) {
                return true;
            }
        }

        return false;
    };

});

care.controller('createIndicatorController', function($rootScope, $scope, $http, $modal,
                                                      $dialog, $location, $errorService, $route,
                                                      $routeParams) {
    $scope.title = 'indicators.title';

    $scope.editIndicatorId = $routeParams['indicatorId'];
    $scope.isEdit = ($scope.editIndicatorId !== undefined && $scope.editIndicatorId != null);

    $scope.selectedClassifications = [];
    $scope.selectedOwners = [];
    $scope.selectedChart = {};
    $scope.indicator = {
        classifications: [],
        owners: [],
        reports: [],
        numerator: null,
        denominator: -1,
        categorized: false,
        trend: null,
        additive: false
    };

    if ($scope.isEdit === true) {
        $scope.fetchIndicatorData = function(indicatorId) {
            $http.get('api/indicator/indicatorId').success(function() {

            }).errors(function() {
                $errorService.genericError($scope, '');
            });
        };
        $scope.fetchIndicatorData($scope.indicatorId);
    }

    $scope.sortFormDataArrays = function() {
        $scope.formData.classifications.sortByField('name');
        $scope.formData.roles.sortByField('name');
        $scope.formData.reportTypes.sortByField('name');
        $scope.formData.dwQueries.sortByField('name');
    };

    $scope.initializeInputFields = function() {
        $scope.listClassifications = $scope.formData.classifications;
        $scope.listCharts = $scope.formData.reportTypes;
        $scope.formData.denominatorDwQueries = [{ id: -1, name: '---' }].concat($scope.formData.dwQueries);

        $scope.indicator.level = $scope.formData.levels[0].id;
        $scope.indicator.frequency = $scope.formData.frequencies[0].id;
        $scope.selectedChart.reportType = $scope.formData.reportTypes[0];
        $scope.selectedClassification = $scope.listClassifications[0];
        $scope.indicator.numerator = $scope.formData.dwQueries[0].id;

        for(var i = 0; i < $scope.formData.roles.length; i++) {
            var id = $scope.formData.roles[i].id;
            $scope.selectedOwners[id] = false;
        }
    };

    $http.get("api/indicator/creationform").success(function(indicatorCreationFormDto) {
        $scope.formData = indicatorCreationFormDto;
        $scope.sortFormDataArrays();
        $scope.initializeInputFields();
    }).error(function() {
        $errorService.genericError($scope, 'indicators.form.error.cannotLoadFormDetails');
    });

    $scope.addClassification = function() {
        $scope.selectedClassifications.push($scope.selectedClassification);
        $scope.selectedClassifications.sortByField('name');

        for (var i = $scope.listClassifications.length - 1; i >= 0; i--) {
            if ($scope.listClassifications[i].id === $scope.selectedClassification.id) {
                $scope.listClassifications.splice(i, 1);
                $scope.selectedClassification = $scope.listClassifications[0];
                break;
            }
        }
    };

    $scope.addChart = function() {
        $scope.indicator.reports.push($scope.selectedChart);

        for (var i = $scope.listCharts.length - 1; i >= 0; i--) {
            if ($scope.listCharts[i].id === $scope.selectedChart.reportType.id) {
                $scope.listCharts.splice(i, 1);
                $scope.selectedChart = {};
                $scope.selectedChart.reportType = $scope.listCharts[0];
                break;
            }
        }
    }

    $scope.filterReportViews = function(reportView) {
        return reportView.name != "Admin" && reportView.name != "Read Only";
    };

    $scope.removeClassification = function(index) {
        $scope.listClassifications.push($scope.selectedClassifications[index]);
        $scope.listClassifications.sortByField('name');
        $scope.selectedClassifications.splice(index, 1);
        $scope.selectedClassification = $scope.listClassifications[0];
    };

    $scope.removeChart = function(index) {
        $scope.listCharts.push($scope.indicator.reports[index].reportType);
        $scope.listCharts.sortByField('name');
        $scope.indicator.reports.splice(index, 1);
        $scope.selectedChart.reportType = $scope.listCharts[0];
    };

    $scope.addNewIndicator = function() {
        $route.reload();
    };

    $scope.selectAllRoles = function() {
         if ($scope.isAllSelected() == true) {
             var check = false;
         } else {
            var check = true;
         }

        for (var i = 0; i < $scope.formData.roles.length; i++) {
            var id = $scope.formData.roles[i].id;
            $scope.selectedOwners[id] = check;
        }
    };

    $scope.sumRoles = function() {
        var sum = 0;
        if ($scope.formData != undefined) {
            for (var i = 0; i < $scope.formData.roles.length; i++) {
                 var id = $scope.formData.roles[i].id;
                 if ($scope.selectedOwners[id] === true) {
                    sum++;
                 }
             }
         }
         return sum;
    }

    $scope.isAllSelected = function() {
        var sum = $scope.sumRoles();
        return sum != 0 && $scope.formData.roles.length == sum;
    };

    $scope.atLeastOneSelected = function() {
        return $scope.sumRoles() > 0 || $scope.selectedOwners[0];
    };

    $scope.submit = function() {
        if ($scope.indicator.denominator == null || $scope.indicator.denominator == -1) {
            $scope.indicator.denominator = null;
        }

        for (var i = 0; i < $scope.selectedClassifications.length; i++) {
            $scope.indicator.classifications.push($scope.selectedClassifications[i].id);
        }
        if ($scope.selectedOwners[0] === true) {
            $scope.indicator.owners.push(0);
        } else {
            for (var i = 0; i < $scope.formData.roles.length; i++) {
                 var id = $scope.formData.roles[i].id;
                 if ($scope.selectedOwners[id] === true) {
                     $scope.indicator.owners.push(id);
                 }
            }
        }
        $http({
            url: "api/indicator",
            method: "POST",
            data: $scope.indicator,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
            $location.path( "/indicators" );
        }).error(function(response, status, headers, config) {
            $errorService.apiError($scope, response);
        });
    };

});

care.controller('dateDepthController', function($scope, $http, $dialog, $location, $errorService) {
    $scope.title = 'indicatorCalculator.dateDepth.header';

    $scope.dateDepth = [];

        $scope.fetchDateDepth = function() {
            $http({
                url: "api/indicator/calculator/dateDepth",
                method: "GET",
            }).success(function(dateDepth) {
                 $scope.dateDepth = moment.utc(dateDepth, "MM-DD-YYYY").toDate();
            }).error(function(response) {
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
                $dialog.messageBox("Date depth saved", "Saved successfully.", [{label: 'Ok', cssClass: 'btn'}]).open();
            }).error(function(response, status, headers, config) {
                $errorService.apiError($scope, response);
            });
        };
});

care.controller('frequencyController', function($scope, $http, $dialog, $location, $errorService, $routeParams) {
    $scope.title = 'menu.calculator.frequency';

    $scope.isEdit = ($routeParams['indicatorId'] !== undefined && $routeParams['indicatorId'] != null);
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
        }).error(function(response, status, headers, config) {
            $errorService.apiError($scope, response);
        });
    };

});

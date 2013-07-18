var care = angular.module('care');


care.controller('reportController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('reports.title');

    $scope.reportId = $routeParams.reportId;

    $scope.indicator = {};

    $scope.fetchReport = function() {
        $http.get('api/report/' + $scope.reportId)
            .success(function(report) {
                $scope.report = report;
            }).error(function() {
                $dialog.messageBox($scope.msg('error'), $scope.msg('reports.form.error.cannotFetchReport'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchIndicators = function() {
        $http.get('api/indicator')
            .success(function(indicators) {
                $scope.indicators = indicators;
            })
            .error(function() {
                $dialog.messageBox($scope.msg('error'), $scope.msg('reports.form.error.cannotLoadIndicators'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchReportTypes = function() {
        $http.get('api/report/type')
            .success(function(reportTypes) {
                $scope.reportTypes = reportTypes;
            })
            .error(function() {
                $dialog.messageBox($scope.msg('error'), $scope.msg('reports.form.error.cannotLoadReportTypes'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
        };


    $scope.submitReport = function(report) {
        $http({method: 'PUT', url: 'api/report' + (report.id !== undefined ? ('/' + report.id) : ''), data: report})
            .success(function(response) {
                $location.path( "/report" );
            }).error(function(response) {
                $errorService.apiError($scope, response);
            });
    };

    $scope.fetchIndicators();
    $scope.fetchReportTypes();

    if ($scope.reportId !== undefined) {
        $scope.fetchReport();
    };
});

care.controller('reportListController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('reports.title');

    $scope.fetchReports = function() {
        $http.get('api/report').success(function(reports) {
            $scope.reports = reports;
        });
    };

    $scope.deleteReport = function(report) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('reports.list.confirmDelete.header'), $scope.msg('reports.list.confirmDelete.message', report.name), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/report/' + report.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchReports();
                    }).error(function(response) {
                        $errorService.apiError($scope, response);
                    });
                }
            });
    };

    $scope.fetchReports();

});
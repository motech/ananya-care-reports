var care = angular.module('care');


care.controller('reportController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = 'reports.title';

    $scope.reportId = $routeParams.reportId;

    $scope.indicator = {};

    $scope.fetchReport = function() {
        $http.get('api/report/' + $scope.reportId)
            .success(function(report) {
                $scope.report = report;
            }).error(function() {
                $dialog.messageBox($scope.msg('common.error'), $scope.msg('reports.form.error.cannotFetchReport'), [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.submitReport = function(report) {
        $scope.report.indicatorId = $scope.report.indicator.id;
        $scope.report.reportTypeId = $scope.report.reportType.id;

        delete $scope.report.indicator;
        delete $scope.report.reportType;

        $http({method: 'PUT', url: 'api/report' + (report.id !== undefined ? ('/' + report.id) : ''), data: report})
            .success(function(response) {
                $location.path("/reports");
            }).error(function(response) {
                $errorService.apiError($scope, response);
            });
    };

    if ($scope.reportId !== undefined) {
        $scope.fetchReport();
    };
});

care.controller('reportListController', function($scope, $http, $routeParams, $location, $dialog) {
    $scope.title = 'reports.title';

    $scope.fetchReports = function() {
        $http.get('api/report').success(function(reports) {
            $scope.reports = reports;
        });
    };
    $scope.fetchReports();

});
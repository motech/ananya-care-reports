var care = angular.module('care');

care.controller('dashboardController', function($scope, $http) {
    $scope.title = $scope.msg('dashboard.title');

    $scope.compareDashboardPositions = function(dashboardA, dashboardB) {
        return parseInt(dashboardA.tabPosition) - parseInt(dashboardB.tabPosition);
    };

    $scope.fetchDashboards = function() {
        $http.get('api/dashboards').success(function(dashboards) {
            dashboards.sort($scope.compareDashboardPositions);
            $scope.dashboards = dashboards;
        });
    };

    $scope.fetchDashboards();

});

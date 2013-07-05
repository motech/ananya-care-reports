var care = angular.module('care');

care.controller('dashboardController', function($scope) {
    $scope.title = $scope.msg('dashboard.title');
});

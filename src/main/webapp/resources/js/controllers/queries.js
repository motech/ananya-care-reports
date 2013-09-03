var care = angular.module('care');

care.controller('createDwQueryController', function($rootScope, $scope, $http, $modal,
                                                    $dialog, $location, $errorService, $route) {
    $scope.title = $scope.msg('queries.new.title');

    $scope.cloneQueryForm = function(queryForm) {
        return {
            joinType: queryForm.joinType
        };
    };

    $scope.initForms = function() {
        $scope.listQueryJoinTypes = [
            'Union', 'UnionAll', 'Intersect', 'Except', 'Join'
        ];
        $scope.selectedJoinType = $scope.listQueryJoinTypes[0];

        $scope.queryForms = [
            { joinType: null }
        ];
    };
    $scope.initForms();

    $scope.addQueryForm = function() {
        $scope.queryForms.push({ joinType: $scope.selectedJoinType });
    }
});

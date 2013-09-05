var care = angular.module('care');

care.controller('whereConditionDialogController', function($scope, $http, $errorService, dialog) {
    $scope.close = function(result) {
        dialog.close(result);
    };
});

care.controller('createDwQueryController', function($rootScope, $scope, $http, $modal,
                                                    $dialog, $location, $errorService, $route, $timeout) {
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
            { joinType: null, whereGroup: null }
        ];
    };
    $scope.initForms();

    $scope.addQueryForm = function() {
        $scope.queryForms.push({
            joinType: $scope.selectedJoinType,
            whereGroup: null
        });
    }

    $scope.getWhereConditionDialog = function() {
        return $dialog.dialog({
            backdrop: true,
            keyboard: false,
            backdropClick: false,
            templateUrl: 'resources/partials/indicators/whereConditionDialog.html',
            controller: 'whereConditionDialogController'
        });
    };

    // Access any data from the whereGroup directive inside a $timeout function
    $timeout(function() {

    });
});

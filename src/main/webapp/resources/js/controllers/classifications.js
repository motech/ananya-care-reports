var care = angular.module('care');

care.controller('classificationsController', function($scope, $http, $dialog, $routeParams, $location, $errorService) {
    $scope.title = $scope.msg('classifications.title');

    $scope.classificationId = $routeParams.classificationId;
    $scope.classification = {};

    $scope.fetchClassification = function() {
            $http.get('api/indicator/classification/' + $scope.classificationId)
                .success(function(cat) {
                    $scope.classification = cat;
                }).error(function(response) {
                    $errorService.apiError($scope, response);
                });
        };

    $scope.submitClassification = function(classification) {
        $http({method: 'PUT', url: 'api/indicator/classification' + (classification.id !== undefined ? ('/' + classification.id) : ''), data: classification})
            .success(function(response) {
                $location.path( "/indicators/classifications" );
            }).error(function(response, status, headers, config) {
                $errorService.apiError($scope, response);
            });
    };

    if ($scope.classificationId !== undefined) {
        $scope.fetchClassification();
        $scope.formHeaderMsg = $scope.msg('classifications.form.edit');
    } else {
        $scope.formHeaderMsg = $scope.msg('classifications.form.add');
    };
});

care.controller('classificationsListController', function($scope, $http, $dialog, $route) {
    $scope.title = $scope.msg('classifications.title');

    $scope.classification = {};
    $scope.fetchClassifications = function() {
        $http.get('api/indicator/classification').success(function(classification) {
            $scope.classification = classification;
        }).error(function(response) {
            $errorService.apiError($scope, response);
        });
    };

    $scope.deleteClassification = function(classification) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('classifications.form.confirmDelete.header'), $scope.msg('classifications.form.confirmDelete.message', classification.name), btns)
        .open()
        .then(function(result) {
            if (result === 'yes') {
                $http({method: 'DELETE',
                    url: 'api/indicator/classification/' + classification.id,
                    data: classification,
                    headers: { 'Content-Type': 'application/json' }
                })
                .success(function(response) {
                    $scope.fetchClassifications();
                }).error(function(response) {
                    $errorService.apiError($scope, response);
                });
            }
        });
    };

    $scope.fetchClassifications();
});

var care = angular.module('care');

care.controller('categoriesController', function($scope, $http, $dialog, $routeParams, $location, $errorService) {
    $scope.title = $scope.msg('category.title');

    $scope.categoryId = $routeParams.categoryId;
    $scope.category = {};

    $scope.fetchCategory = function() {
            $http.get('api/indicator/category/' + $scope.categoryId)
                .success(function(cat) {
                    $scope.category = cat;
                }).error(function() {
                    $dialog.messageBox("Error", $scope.msg('category.error.edit.load'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                });
        };

    $scope.submitCategory = function(category) {
        $http({method: 'PUT', url: 'api/indicator/category' + (category.id !== undefined ? ('/' + category.id) : ''), data: category})
            .success(function(response) {
                $location.path( "/categories" );
            }).error(function(response) {
                $errorService.apiError($scope, response);
            });
    };

    if ($scope.categoryId !== undefined) {
        $scope.fetchCategory();
    };
});

care.controller('categoriesListController', function($scope, $http, $dialog, $route) {
    $scope.title = $scope.msg('category.title');

    $scope.category = {};
    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(category) {
            $scope.category = category;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.load'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };

    $scope.deleteCategory = function(category) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('category.confirmDelete.header'), $scope.msg('category.confirmDelete.message', category.name), btns)
        .open()
        .then(function(result) {
            if (result === 'yes') {
                $http({method: 'DELETE',
                    url: 'api/indicator/category/' + category.id,
                    data: category,
                    headers: { 'Content-Type': 'application/json' }
                })
                .success(function(response) {
                    $scope.fetchCategories();
                }).error(function(response) {
                    $errorService.apiError($scope, response);
                });
            }
        });
    };

    $scope.fetchCategories();
});

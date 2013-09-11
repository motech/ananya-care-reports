var care = angular.module('care');

care.controller('categoriesController', function($scope, $http, $dialog, $routeParams, $location, $errorService) {
    $scope.title = $scope.msg('categories.title');

    $scope.categoryId = $routeParams.categoryId;
    $scope.category = {};

    $scope.fetchCategory = function() {
            $http.get('api/indicator/category/' + $scope.categoryId)
                .success(function(cat) {
                    $scope.category = cat;
                }).error(function(response) {
                    $errorService.apiError($scope, response);
                });
        };

    $scope.submitCategory = function(category) {
        $http({method: 'PUT', url: 'api/indicator/category' + (category.id !== undefined ? ('/' + category.id) : ''), data: category})
            .success(function(response) {
                $location.path( "/indicators/categories" );
            }).error(function(response, status, headers, config) {
                $errorService.apiError($scope, response);
            });
    };

    if ($scope.categoryId !== undefined) {
        $scope.fetchCategory();
        $scope.formHeaderMsg = $scope.msg('categories.form.edit');
    } else {
        $scope.formHeaderMsg = $scope.msg('categories.form.add');
    };
});

care.controller('categoriesListController', function($scope, $http, $dialog, $route) {
    $scope.title = $scope.msg('categories.title');

    $scope.category = {};
    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(category) {
            $scope.category = category;
        }).error(function(response) {
            $errorService.apiError($scope, response);
        });
    };

    $scope.deleteCategory = function(category) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('categories.form.confirmDelete.header'), $scope.msg('categories.form.confirmDelete.message', category.name), btns)
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

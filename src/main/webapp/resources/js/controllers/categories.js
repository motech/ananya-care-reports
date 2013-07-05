var care = angular.module('care');

care.controller('categoriesController', function($scope, $http, $dialog, $route) {
    $scope.title = $scope.msg('category.title');

    $scope.category = {};
    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(category) {
            $scope.category = category;
        }).error(function(response) {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.load'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };
    $scope.createCategory = function(category) {
        $http({method: 'POST',
            url: 'api/indicator/category' ,
            data: category,
            headers: { 'Content-Type': 'application/json' }
        })
        .success(function(response) {
            $dialog.messageBox($scope.msg('information'), $scope.msg('category.success.create'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }).error(function(response) {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.create'), [{label: 'Ok', cssClass: 'btn'}]).open();
        });
    };
    $scope.editCategory = function(category, newName) {
        if(newName && category && newName != category.name){
            category.name = newName;
            $http({method: 'PUT',
                url: 'api/indicator/category/' + category.id,
                data: category,
                headers: { 'Content-Type': 'application/json' }
            })
            .success(function(response) {
                $route.reload();
                $dialog.messageBox($scope.msg('information'), $scope.msg('category.success.edit'), [{label: 'Ok', cssClass: 'btn'}]).open();
            }).error(function(response) {
                $route.reload();
                $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.edit'), [{label: 'Ok', cssClass: 'btn'}]).open();
            });
        }
        else if(!category){
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.choose'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }
        else {
            $dialog.messageBox($scope.msg('error'), $scope.msg('category.name.valid'), [{label: 'Ok', cssClass: 'btn'}]).open();
        }
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
                    $dialog.messageBox($scope.msg('error'), $scope.msg('category.error.delete'), [{label: 'Ok', cssClass: 'btn'}]).open();
                });
            }
        });
    };
    $scope.fetchCategories();
});

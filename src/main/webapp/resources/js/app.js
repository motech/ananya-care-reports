(function () {
    'use strict';

    angular.module('care', ['ngResource', 'ui.bootstrap', 'localization', '$strap.directives']).config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'resources/partials/dashboard.html', controller: 'dashboardController' })
            .when('/indicator/new', { templateUrl: 'resources/partials/indicatorForm.html', controller: 'createIndicatorController' })
            .when('/forms', { templateUrl: 'resources/partials/forms/list.html', controller: 'formListController' })
            .when('/forms/new', { templateUrl: 'resources/partials/forms/form.html', controller: 'formController' })
            .when('/forms/:formId', { templateUrl: 'resources/partials/forms/form.html', controller: 'formController' })
            .when('/users', {templateUrl: 'resources/partials/users/listUsers.html', controller: 'userListController'})
            .when('/users/roles', {templateUrl: 'resources/partials/users/listRoles.html', controller: 'roleListController'})
            .when('/users/roles/new', {templateUrl: 'resources/partials/users/editRole.html', controller: 'roleController'})
            .when('/users/roles/:roleId', {templateUrl: 'resources/partials/users/editRole.html', controller: 'roleController'})
            .when('/users/new', {templateUrl: 'resources/partials/users/editUser.html', controller: 'userController'})
            .when('/users/:userId', {templateUrl: 'resources/partials/users/editUser.html', controller: 'userController'})
            .when('/categories', { templateUrl: 'resources/partials/categories.html', controller: 'categoriesListController'})
            .when('/categories/new', { templateUrl: 'resources/partials/addCategory.html', controller: 'categoriesController'})
            .when('/categories/:categoryId', { templateUrl: 'resources/partials/addCategory.html', controller: 'categoriesController'})
            .otherwise({ redirectTo: '/' });
    }]).run(function($rootScope, i18nService) {
        $rootScope.msg = function(key, params) {
            return i18nService.getMessage(key, params);
        };
        $('.multiselect').multiselect({
            buttonClass: 'btn btn-small'
        });
    });

}());


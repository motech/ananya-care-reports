(function () {
    'use strict';

    angular.module('care', ['ngResource', 'ui.bootstrap', 'localization', '$strap.directives']).config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'resources/partials/dashboards/dashboard.html', controller: 'dashboardController' })
            .when('/indicators', { templateUrl: 'resources/partials/indicators/listIndicators.html', controller: 'indicatorListController' })
            .when('/indicators/new', { templateUrl: 'resources/partials/indicators/indicatorForm.html', controller: 'createIndicatorController' })
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
            .when('/report/:reportId', {templateUrl: 'resources/partials/reports/editReport.html', controller: 'reportController'})
            .when('/report', {templateUrl: 'resources/partials/reports/reportList.html', controller: 'reportListController'})
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


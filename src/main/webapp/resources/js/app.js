(function () {
    'use strict';

    angular.module('care', ['ngResource', 'ui.bootstrap', 'localization', '$strap.directives']).config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', { templateUrl: 'resources/partials/dashboards/dashboard.html', controller: 'dashboardController' })
            .when('/indicators', { templateUrl: 'resources/partials/indicators/listIndicators.html', controller: 'indicatorListController' })
            .when('/indicators/new', { templateUrl: 'resources/partials/indicators/indicatorForm.html', controller: 'createIndicatorController' })
            .when('/indicators/recalculate', { templateUrl: 'resources/partials/dashboards/dashboard.html', controller: 'recalculateIndicatorsController' })
            .when('/indicators/:indicatorId', { templateUrl: 'resources/partials/indicators/indicatorForm.html', controller: 'createIndicatorController' })
            .when('/forms', { templateUrl: 'resources/partials/forms/list.html', controller: 'formListController' })
            .when('/forms/:formId', { templateUrl: 'resources/partials/forms/form.html', controller: 'formController' })
            .when('/users', {templateUrl: 'resources/partials/users/listUsers.html', controller: 'userListController'})
            .when('/users/roles', {templateUrl: 'resources/partials/users/listRoles.html', controller: 'roleListController'})
            .when('/users/roles/new', {templateUrl: 'resources/partials/users/editRole.html', controller: 'roleController'})
            .when('/users/roles/:roleId', {templateUrl: 'resources/partials/users/editRole.html', controller: 'roleController'})
            .when('/users/new', {templateUrl: 'resources/partials/users/editUser.html', controller: 'userController'})
            .when('/users/:userId', {templateUrl: 'resources/partials/users/editUser.html', controller: 'userController'})
            .when('/categories', { templateUrl: 'resources/partials/indicators/categories.html', controller: 'categoriesListController'})
            .when('/categories/new', { templateUrl: 'resources/partials/indicators/addCategory.html', controller: 'categoriesController'})
            .when('/categories/:categoryId', { templateUrl: 'resources/partials/indicators/addCategory.html', controller: 'categoriesController'})
            .when('/reports/:reportId', {templateUrl: 'resources/partials/reports/editReport.html', controller: 'reportController'})
            .when('/reports', {templateUrl: 'resources/partials/reports/reportList.html', controller: 'reportListController'})
            .when('/admin/computed-fields', { templateUrl: 'resources/partials/computed-fields/manage.html', controller: 'computedFieldsController' })
            .when('/messages', {templateUrl: 'resources/partials/messages/listLanguages.html', controller: 'languageListController'})
            .when('/messages/:languageCode', {templateUrl: 'resources/partials/messages/editMessages.html', controller: 'messageController'})
            .otherwise({ redirectTo: '/' });


        var interceptor = ['$rootScope', '$q', 'i18nService', function (scope, $q, i18nService) {

            function success(response) {
                return response;
            }

            function error(response) {
                var status = response.status;

                if (status == 403) {
                    response.data = [{
                        field: i18nService.getMessage('unauthorized.title'),
                        message: i18nService.getMessage('unauthorized.message')}];
                    return $q.reject(response);
                }
                if (status == 302 || //user session expired
                    status == 0) { //the server is probably down
                    document.location.reload(true);
                    return;
                }
                return $q.reject(response);
            }

            return function (promise) {
                return promise.then(success, error);
            }
        }];

        $httpProvider.responseInterceptors.push(interceptor);

    }]).run(function($rootScope, i18nService) {
        $rootScope.msg = function(key, params) {
            return i18nService.getMessage(key, params);
        };
        $('.multiselect').multiselect({
            buttonClass: 'btn btn-small'
        });
    });

}());


(function () {
    'use strict';

    angular.module('care', ['ngResource', 'ui.bootstrap', 'localization', '$strap.directives']).config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', { templateUrl: 'resources/partials/dashboards/dashboard.html', controller: 'dashboardController' })
            .when('/indicators', { templateUrl: 'resources/partials/indicators/listIndicators.html', controller: 'indicatorListController' })
            .when('/indicators/new', { templateUrl: 'resources/partials/indicators/editIndicator.html', controller: 'createIndicatorController' })
            .when('/indicators/recalculate', { templateUrl: 'resources/partials/dashboards/dashboard.html', controller: 'recalculateIndicatorsController' })
            .when('/indicators/upload-xml', { templateUrl: 'resources/partials/indicators/uploadXml.html', controller: 'uploadIndicatorController'})
            .when('/indicators/calculation-time', { templateUrl: 'resources/partials/indicators/frequency.html', controller: 'frequencyController'})
            .when('/indicators/date-depth', { templateUrl: 'resources/partials/indicators/dateDepth.html', controller: 'dateDepthController'})
            .when('/indicators/queries', { templateUrl: 'resources/partials/indicators/listQueries.html', controller: 'queryListController' })
            .when('/indicators/queries/new', { templateUrl: 'resources/partials/indicators/createDwQuery.html', controller: 'createDwQueryController' })
            .when('/admin/forms', { templateUrl: 'resources/partials/admin/forms/listForms.html', controller: 'formListController' })
            .when('/admin/forms/:formId', { templateUrl: 'resources/partials/admin/forms/editForm.html', controller: 'formController' })
            .when('/indicators/categories', { templateUrl: 'resources/partials/indicators/listCategories.html', controller: 'categoriesListController'})
            .when('/indicators/categories/new', { templateUrl: 'resources/partials/indicators/editCategory.html', controller: 'categoriesController'})
            .when('/indicators/categories/:categoryId', { templateUrl: 'resources/partials/indicators/editCategory.html', controller: 'categoriesController'})
            .when('/indicators/:indicatorId', { templateUrl: 'resources/partials/indicators/editIndicator.html', controller: 'createIndicatorController' })
            .when('/reports/:reportId', {templateUrl: 'resources/partials/reports/editReport.html', controller: 'reportController'})
            .when('/reports', {templateUrl: 'resources/partials/reports/listReports.html', controller: 'reportListController'})
            .when('/admin/computed-fields', { templateUrl: 'resources/partials/admin/computed-fields/computedField.html', controller: 'computedFieldsController' })
            .when('/admin/languages', {templateUrl: 'resources/partials/admin/messages/listLanguages.html', controller: 'languageListController'})
            .when('/admin/languages/:languageCode', {templateUrl: 'resources/partials/admin/messages/editMessages.html', controller: 'messageController'})
            .when('/admin/select-language', {templateUrl: 'resources/partials/admin/messages/selectLanguage.html', controller: 'messageController'})
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

        $rootScope.getLocation = function() {
            return location.hash;
        };

        $('.multiselect').multiselect({
            buttonClass: 'btn btn-small'
        });
    });

}());


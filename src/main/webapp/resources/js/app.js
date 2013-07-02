(function () {
    'use strict';

    angular.module('care', ['ngResource', 'ui.bootstrap']).config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'resources/partials/main.html', controller: 'mainController' })
            .when('/indicator/new', { templateUrl: 'resources/partials/indicatorForm.html', controller: 'createIndicatorController' })
            .when('/forms', { templateUrl: 'resources/partials/forms/list.html', controller: 'formListController' })
            .when('/forms/new', { templateUrl: 'resources/partials/forms/form.html', controller: 'formController' })
            .when('/forms/:formId', { templateUrl: 'resources/partials/forms/form.html', controller: 'formController' })
            .otherwise({ redirectTo: '/' });
    }]);
}());
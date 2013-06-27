(function () {
    'use strict';

    angular.module('care', ['ngResource']).config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: '/resources/partials/main.html', controller: 'mainController' })
            .when('/indicator/new', { templateUrl: '/resources/partials/indicatorForm.html', controller: 'createIndicatorController' })
            .otherwise({ redirectTo: '/' });
    }]);
}());
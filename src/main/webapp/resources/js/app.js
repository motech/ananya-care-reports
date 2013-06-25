(function () {
    'use strict';

    angular.module('care', []).config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

        $routeProvider
            .when('/', { templateUrl: 'resources/partials/main.html', controller: mainController })
            .otherwise({ redirectTo: '/' });
    }]);

}());
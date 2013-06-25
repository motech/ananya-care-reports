(function () {
    'use strict';

    angular.module('care', []).config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/', { templateUrl: 'partials/main.html', controller: mainController })
            .when('/login', { templateUrl: 'partials/login.html', controller: loginController })
            .otherwise({ redirectTo: '/login' });
    }]);
}());
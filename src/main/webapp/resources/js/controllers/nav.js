var care = angular.module('care');

care.controller('navController', function($scope, $rootScope, $http, $simplifiedHttpService, $dialog,
        i18nService, $timeout, $modal) {
    $scope.listLanguages = [];
    $scope.defaultLanguage = null;

    $scope.fetchLanguages = function() {
        $simplifiedHttpService.get($scope, 'api/languages',
                'languages.error.cannotLoadLanguageList', function(languages) {
            languages.sort();
            $scope.listLanguages = languages;
        });
    };
    $timeout($scope.fetchLanguages, 0);

    $scope.fetchActiveUserDefaultLanguage = function() {
        $simplifiedHttpService.get($scope, 'api/users/logged_in/language',
                'languages.error.cannotLoadLanguage', function(language) {
            $scope.defaultLanguage = language;
            i18nService.init(language.code);
        });
    };
    $timeout($scope.fetchActiveUserDefaultLanguage, 0);

    $scope.selectLanguage = function(language) {
        $http({
            url: 'api/users/logged_in/language',
            method: 'PUT',
            data: language,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
            $scope.defaultLanguage = language;
            i18nService.init(language.code);
        });
    };

    $rootScope.$watch('languagesChanged', function() {
        if ($rootScope.languagesChanged !== undefined && $rootScope.languagesChanged !== false) {
            $rootScope.languagesChanged = false;

            $scope.fetchLanguages();
        }
    });

    $scope.launchFrequencyDialog = function() {
        var dialog = $modal({
                template: "resources/partials/indicators/frequencyDialog.html",
                persist: true,
                show: true,
                backdrop: "static"
            });
    };

    $scope.launchDateDepthDialog = function() {
        var dialog = $modal({
                template: "resources/partials/indicators/dateDepthDialog.html",
                persist: true,
                show: true,
                backdrop: "static"
            });
        };
});

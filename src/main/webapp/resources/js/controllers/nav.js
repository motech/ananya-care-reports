var care = angular.module('care');

care.controller('navController', function($scope, $rootScope, $http, $simplifiedHttpService, $dialog, i18nService, $location, $timeout, $modal) {
    $scope.listLanguages = [];

    $scope.fetchLanguages = function() {
        $simplifiedHttpService.get($scope, 'api/languages/defined',
                'languages.cannotLoadLanguageList', function(languages) {
            languages.sort();
            $scope.listLanguages = languages;
        });
    };
    $timeout($scope.fetchLanguages, 0);

    $scope.selectLanguage = function(index) {
        i18nService.init($scope.listLanguages[index].code);
    }

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
});

var care = angular.module('care');

care.controller('accountSettingsController', function($scope, $http, $errorService, $route) {
    $scope.title = 'account.settings.title';

    $scope.fetchDefaultLanguage = function() {
        $http.get('api/users/logged_in/language').success(function(language) {
            $scope.currentLanguage = language;
            $scope.fetchLanguages();
        }).error(function() {
            $errorService.genericError($scope, 'languages.error.cannotLoadLanguage');
        });
    };
    $scope.fetchDefaultLanguage();

    $scope.fetchLanguages = function() {
        $http.get('api/languages').success(function(languages) {
            languages.sortByField('name');
            $scope.listLanguages = languages;

            var index = 0;
            for (var i = 0; i < $scope.listLanguages.length; i++) {
                if ($scope.listLanguages[i].code == $scope.currentLanguage.code) {
                    index = i;
                    break;
                }
            }

            $scope.selectedLanguage = $scope.listLanguages[index];
        }).error(function() {
            $errorService.genericError($scope, 'languages.error.cannotLoadLanguageList');
        });
    };

    $scope.selectLanguage = function() {
        $http({
            url: 'api/users/logged_in/language',
            method: 'PUT',
            data: $scope.selectedLanguage,
            headers: { 'Content-Type': 'application/json' }
        }).success(function() {
            window.location.reload();
        }).error(function(response, headers, status, config) {
            $errorService.apiError($scope, response);
        });
    };
});

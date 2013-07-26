care.controller('navController', function($scope, $http, $dialog, $location, $modal) {
    $scope.launchFrequencyDialog = function() {

            var dialog = $modal({
                template: "resources/partials/indicators/frequencyDialog.html",
                persist: true,
                show: true,
                backdrop: "static"
            });
        };

});

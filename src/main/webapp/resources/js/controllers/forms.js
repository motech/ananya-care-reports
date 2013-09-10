var care = angular.module('care');

care.controller('formController', function($scope, $http, $routeParams, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('forms.title');

    $scope.formId = $routeParams.formId;
    $scope.careForm = {};

    $scope.fetchForm = function() {
        $http.get('api/forms/' + $scope.formId)
            .success(function(form) {
                $scope.careForm = form;
            }).error(function() {
                $errorService.genericError($scope, 'forms.form.error.cannotLoadForm');
            });
    };

    $scope.submitForm = function(form) {
        $http({method: 'PUT',
                url: 'api/forms/' + form.id,
                data: form})
            .success(function(response) {
                $location.path( "/admin/forms" );
            }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('common.error'), data, [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchForm();
});

care.controller('formListController', function($scope, $http, $dialog, $errorService, $simplifiedHttpService) {
    $scope.title = $scope.msg('forms.title');

    var reloadMsg = $scope.msg('forms.list.reloadForms');
    var loadingMsg = $scope.msg('forms.list.reloadForms.loading');

    $scope.isListReloaded = false;
    $scope.reloadButtonMessage = reloadMsg;

    $scope.fetchForms = function() {
        $http.get('api/forms/list').success(function(forms) {
            $scope.mother_forms = forms.motherForms;
            $scope.child_forms = forms.childForms;
            $scope.other_forms = forms.otherForms;
            $scope.isListReloaded = true;
            $scope.reloadButtonMessage = reloadMsg;
        }).error(function(data, status, header, config) {
            $errorService.genericError($scope, 'forms.form.error.cannotReceiveForeignKey');
        });
    };

    $scope.reloadForms = function() {
        $scope.isListReloaded = false;
        $scope.reloadButtonMessage = loadingMsg;

        $simplifiedHttpService.get($scope, 'api/forms/reload', 'forms.list.error.cannotReloadForms', function() {
            $scope.fetchForms();
        });
    };

    $scope.fetchForms();

});

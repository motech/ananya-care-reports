var care = angular.module('care');

care.controller('formController', function($scope, $http, $routeParams, $location, $dialog, $errorService, $errorsDialogService) {
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

    $scope.fetchTables = function() {
        $http.get('api/forms/tables')
            .success(function(tables) {
                $scope.tables = tables;
            })
            .error(function() {
                $errorService.genericError($scope, 'forms.form.error.cannotLoadTables');
            });
    };

    $scope.submitForm = function(form) {

        $http({method: 'PUT', url: 'api/forms' + (form.id !== undefined ? ('/' + form.id) : ''), data: form})
            .success(function(response) {
                $location.path( "/forms" );
            }).error(function(response) {
                $errorsDialogService.apiError($scope, response);
            });
    };

    $scope.fetchTables();

    if ($scope.formId !== undefined) {
        $scope.fetchForm();
    };
});

care.controller('formListController', function($scope, $http, $dialog, $errorService, $simplifiedHttpService) {
    $scope.title = $scope.msg('forms.title');

    var reloadMsg = $scope.msg('forms.list.reloadForms');
    var loadingMsg = $scope.msg('forms.list.reloadForms.loading');

    $scope.isListReloaded = false;
    $scope.reloadButtonMessage = reloadMsg;

    $scope.fetchForms = function() {
        $http.get('api/forms').success(function(forms) {
            var mother_forms = new Array();
            var child_forms = new Array();
            var other_forms = new Array();
            for(var i = 0; i < forms.length; i++) {
                if(forms[i].tableName.indexOf("mother") !== -1) {
                    mother_forms.push(forms[i]);
                } else if (forms[i].tableName.indexOf("child") !== -1) {
                    child_forms.push(forms[i]);
                } else {
                    $http.get('api/forms/table/foreignKey/' + forms[i].tableName, {index:i})
                        .success(function(foreignKey, status, header, config) {
                            if(foreignKey.indexOf("mother") !== -1) {
                                mother_forms.push(forms[config.index]);
                            } else if(foreignKey.indexOf("child") !== -1) {
                                child_forms.push(forms[config.index]);
                            } else {
                                other_forms.push(forms[config.index]);
                            }
                        })
                        .error(function(data, status, header, config) {
                            $errorService.genericError($scope, 'forms.form.error.cannotReceiveForeignKey');
                        })
                }
            }

            $scope.mother_forms = mother_forms;
            $scope.child_forms = child_forms;
            $scope.other_forms = other_forms;
            $scope.isListReloaded = true;
            $scope.reloadButtonMessage = reloadMsg;
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

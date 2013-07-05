var care = angular.module('care');

care.controller('formController', function($scope, $http, $routeParams, $location, $dialog) {
    $scope.title = $scope.msg('forms.title');

    $scope.formId = $routeParams.formId;
    $scope.careForm = {};

    $scope.fetchForm = function() {
        $http.get('api/forms/' + $scope.formId)
            .success(function(form) {
                $scope.careForm = form;
            }).error(function() {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotLoadForm'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchTables = function() {
        $http.get('api/forms/tables')
            .success(function(tables) {
                $scope.tables = tables;
            })
            .error(function() {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotLoadTables'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.submitForm = function(form) {

        $http({method: 'PUT', url: 'api/forms' + (form.id !== undefined ? ('/' + form.id) : ''), data: form})
            .success(function(response) {
                $location.path( "/forms" );
            }).error(function(response) {
                $dialog.messageBox("Error", $scope.msg('forms.form.error.submit'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
    };

    $scope.fetchTables();

    if ($scope.formId !== undefined) {
        $scope.fetchForm();
    };
});

care.controller('formListController', function($scope, $http, $dialog) {
    $scope.title = $scope.msg('forms.title');

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
                            $dialog.messageBox("Error", $scope.msg('forms.form.error.cannotReceiveForeignKey', forms[config.index].tableName), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                        })
                }
            }
            $scope.mother_forms = mother_forms;
            $scope.child_forms = child_forms;
            $scope.other_forms = other_forms;
        });
    };

    $scope.deleteForm = function(form) {
        var btns = [{result:'yes', label: $scope.msg('yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('forms.list.confirmDelete.header'), $scope.msg('forms.list.confirmDelete.message', form.displayName), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/forms/' + form.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchForms();
                    }).error(function(response) {
                        $dialog.messageBox("Error", $scope.msg('forms.list.error.delete'), [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchForms();

});

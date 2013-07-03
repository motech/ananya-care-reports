var care = angular.module('care');

care.controller('dashboardController', function($scope) {

});

care.controller('createIndicatorController', function($scope) {

    $scope.indicator = {};

    $scope.indicator.listOwners = [
        { name: "Program Manager", value: "1" },
        { name: "Operational Manager #1", value: "2" },
        { name: "Operational Manager #3", value: "3" },
        { name: "Operational Manager #4", value: "4" },
        { name: "Operational Manager #5", value: "5" }
    ];
    $scope.indicator.owner = "1";
    $scope.indicator.listFrequencies = [
        { name: "1 day", value: "1" },
        { name: "2 days", value: "2" },
        { name: "30 days", value: "30" },
        { name: "60 days", value: "60" },
        { name: "180 days", value: "180" }
    ];
    $scope.indicator.frequency = "30";
    $scope.indicator.listLevelDistrict = [
        { name: "District 9", value: "1" },
        { name: "District X", value: "2" },
        { name: "District Z", value: "3" }
    ];
    $scope.indicator.levelDistrict = "1";
    $scope.indicator.listLevelBlock = [
        { name: "Block 1", value: "1" },
        { name: "Block 2", value: "2" },
        { name: "Block 3", value: "3" }
    ];
    $scope.indicator.levelBlock = "1";
    $scope.indicator.listLevelSubcentre = [
        { name: "Subcentre A", value: "1" },
        { name: "Subcentre B", value: "2" },
        { name: "Subcentre C", value: "3" }
    ];
    $scope.indicator.levelSubcentre = "1";
    $scope.indicator.listLevelFlwType = [
        { name: "FLW 1", value: "1" },
        { name: "FLW 2", value: "2" },
        { name: "FLW 3", value: "3" }
    ];
    $scope.indicator.levelFlwType = "1";

    $scope.indicator.name = "";
    $scope.indicator.listDefinedAs = [
        { name: "Item 1", value: "1" },
        { name: "Item 2", value: "12" },
        { name: "Item 3", value: "123" }
    ];
    $scope.indicator.definedAs = "1";
    $scope.indicator.listClassifiedAs = [
        { name: "Item #1", value: "1" },
        { name: "Item #2", value: "12" },
        { name: "Item #3", value: "123" }
    ];
    $scope.indicator.classifiedAs = "1";
    $scope.indicator.listOperators = [
        { name: "ADD", value: "1" },
        { name: "SUBTRACT", value: "2" },
        { name: "MULTIPLY", value: "3" },
        { name: "DIVIDE", value: "4" }
    ];
    $scope.indicator.operator = "1";
    $scope.indicator.listForms = [
        { name: "Form 1", value: "1" },
        { name: "Form 2", value: "2" }
    ];
    $scope.indicator.form = "1";
    $scope.indicator.listCalculateBy = [
        { name: "Formula 1", value: "1" },
        { name: "Formula 2", value: "2" }
    ];
    $scope.indicator.calculateBy = "1";

});

care.controller('formController', function($scope, $http, $routeParams, $location, $dialog) {

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

    $scope.fetchForms = function() {
        $http.get('api/forms').success(function(forms) {
            $scope.forms = forms;
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

care.controller('userListController', function($scope, $http, $routeParams, $location, $dialog) {

    $scope.fetchUsers = function() {
        $http.get('api/users').success(function(users) {
            $scope.users = users;
        });
    };

    $scope.deleteUser = function(user) {
        var btns = [{result:'yes', label: 'Yes', cssClass: 'btn-primary btn'}, {result:'no', label: 'No', cssClass: 'btn-danger btn'}];
        $dialog.messageBox("Confirm delete user", "Are you sure you want to delete user: " + user.username + '?', btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({method: 'DELETE', url: 'api/users/' + user.id})
                    .success(function(data, status, headers, config) {
                        $scope.fetchForms();
                    }).error(function(response) {
                        $dialog.messageBox("Error", "Cannot delete user.", [{label: 'Ok', cssClass: 'btn'}]).open();
                    });
                }
            });
    };

    $scope.fetchUsers();

});
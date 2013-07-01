var care = angular.module('care');

care.controller('mainController', function($scope) {

});

care.controller('createIndicatorController', function($scope) {
    $scope.showCreateForm = true;
    $scope.showUpdateForm = false;

    $scope.indicator = {};
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

care.controller('createFormController', function($scope, $http) {

   $scope.careForm = {}

   $scope.fetchTables = function() {
        $http.get('/forms/tables').success(function(tables) {
            $scope.tables = tables;
        });
    }

    $scope.addNewForm = function(form) {
        $http.post("/forms/add", form).success(function(response) {
            }).error(function(response) {
            });
    }

    $scope.fetchTables();

});
var care = angular.module('care');

care.controller('mainController', function($scope) {

});

care.controller('createIndicatorController', function($scope) {
    $scope.showCreateForm = true;
    $scope.showUpdateForm = false;

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

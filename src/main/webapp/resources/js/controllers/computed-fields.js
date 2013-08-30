var care = angular.module('care');

care.controller('computedFieldsController', function($scope, $http, $routeParams, $rootScope, $location, $dialog, $errorService) {
    $scope.title = $scope.msg('computedField.title');

    $scope.listOperators = [];
    $scope.computedField = {};
    $scope.selectedFields = [];

    $scope.fetchComputedFields = function() {
        $http.get('api/computedfields')
        .success(function(computedFields) {
            $scope.computedFields = computedFields;
        }).error(function() {
            $errorService.genericError($scope, 'computedFields.error.cannotLoadComputedFields');
        });
    };
    $scope.fetchComputedFields();

    $scope.fetchFormFields = function() {
        $http.get('api/forms/' + $scope.computedField.form + "/computedfields")
        .success(function(fields) {
            $scope.fields = fields;
        }).error(function() {
            $errorService.genericError($scope, 'computedFields.error.cannotLoadFields');
        });
    };

    $scope.fetchForms = function() {
        $http.get('api/forms')
        .success(function(forms) {
            $scope.forms = forms;
        }).error(function() {
            $errorService.genericError($scope, 'computedFields.error.cannotLoadForms');
        });
    };
    $scope.fetchForms();

    $scope.$watch('computedField.form', function(newVal, oldVal) {
         if ($scope.computedField.form != undefined) {
              if ($scope.selectedFields.length > 0) {
                   $scope.selectedFields = [];
              }
              $scope.fetchFormFields();
         }
    });

    $scope.fetchOperators = function() {
        $http.get('api/complexcondition/operatortype')
            .success(function(operators) {
                operators.sortByField('name');
                $scope.listOperators = operators;

                if ($scope.listOperators.notEmpty()) {
                    $scope.selectedOperator = $scope.listOperators[0];
                }
            }).error(function() {
                $errorService.genericError($scope, 'indicators.computedFieldDialog.error.cannotLoadOperatorList');
            });
    };
    $scope.fetchOperators();

    $scope.addField = function() {
        if (!$scope.selectedField) {
            return;
        }

        var index = -1;
        for (var i = 0; i < $scope.fields.length; i++) {
            if ($scope.fields[i].id == $scope.selectedField) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            var field = {
                operator: ($scope.selectedFields.length > 0) ? $scope.selectedOperator : null,
                field: $scope.fields[index]
            };

            $scope.selectedFields.push(field);
            $scope.fields.splice(index, 1);
            if ($scope.fields.notEmpty()) {
                $scope.selectedField = $scope.fields[0].id;
            }
        }
    };

    $scope.removeField = function(key) {
        $scope.fields.push($scope.selectedFields[key].field);
        $scope.fields.sortByField('name');
        $scope.selectedField = $scope.fields[0].id;

        $scope.selectedFields.splice(key, 1);

        if (key == 0 && $scope.selectedFields.notEmpty()) {
            $scope.selectedFields[0].operator = null;
        }
    };

    $scope.save = function() {
        var computedFieldId = $scope.computedField.id;
        delete $scope.computedField.id;
        $scope.computedField.fieldOperations = $scope.createFieldOperations();
        $scope.computedField.type = "Number";

        $http({
            url: "api/computedfields" + (computedFieldId != undefined ? ("/" + computedFieldId) : ""),
            method: "POST",
            data: $scope.computedField,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(data, status, headers, config) {
            $scope.fetchComputedFields();
            $scope.computedField = {};
            $scope.selectedFields = [];
            $scope.fields = [];
        }).error(function(data, status, headers, config) {
            $dialog.messageBox($scope.msg('error'), data, [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
        });
    };

    $scope.createFieldOperations = function() {
        var fields = $scope.selectedFields;
        var fieldOperations = [];

        if ($scope.selectedFields.length == 1) {
            return fieldOperations;
        }

        for (var i = 0; i < $scope.selectedFields.length; i++) {
            if (fieldOperations.length >=1 && i >= $scope.selectedFields.length - 1) {
                break;
            }
            fieldOperations.push({
                field1: fields[i].field,
                field2: (i + 1 < $scope.selectedFields.length) ? fields[i + 1].field : null,
                operatorType: (i + 1 < $scope.selectedFields.length) ? fields[i + 1].operator : null,
            });
        }

        return fieldOperations;
    };

    $scope.loadComputedField = function(computedField) {
        if(computedField.id != $scope.computedField.id) {
            $http({
                url: "api/computedfields/" + computedField.id,
                method: "GET",
            }).success(function(data, status, headers, config) {
                $scope.selectedFields = [];
                $scope.computedField = data;
                $scope.computedField.form = $scope.computedField.form.id;
                for (var i=0; i<data.fieldOperations.length; i+=1) {
                    var operation = $scope.computedField.fieldOperations[i];
                    if (i==0) {
                        $scope.selectedFields.push({
                            operator: null,
                            field: operation.field1
                        });
                    }
                    $scope.selectedFields.push({
                        operator: operation.operatorType,
                        field: operation.field2
                    });
                }
            }).error(function(data, status, headers, config) {
                $dialog.messageBox($scope.msg('error'), data, [{label: $scope.msg('ok'), cssClass: 'btn'}]).open();
            });
        }
    };

});

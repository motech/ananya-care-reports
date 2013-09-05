var care = angular.module('care');

care.controller('whereConditionDialogController', function($rootScope, $scope, $http, $errorService, dialog) {
    $scope.mainScope = $rootScope.mainScope;
    delete $rootScope.mainScope;

    $scope.listConditionTypes = [
        { name: 'dateDiff', code: 'queries.conditionDialog.conditionType.dateDiff' },
        { name: 'dateRange', code: 'queries.conditionDialog.conditionType.dateRange' },
        { name: 'dateValue', code: 'queries.conditionDialog.conditionType.dateValue' },
        { name: 'enumRange', code: 'queries.conditionDialog.conditionType.enumRange' },
        { name: 'value', code: 'queries.conditionDialog.conditionType.value' },
        { name: 'field', code: 'queries.conditionDialog.conditionType.field' }
    ];

    $scope.formData = {
        forms: [],
        computedFields1: [],
        operators: [
            { name: '<' },
            { name: '<=' },
            { name: '=' },
            { name: '<>' },
            { name: '>' },
            { name: '>=' }
        ]
    };
    $scope.condition = {
        type: $scope.listConditionTypes[0].name,
        form1: null,
        field1: null,
        operator: $scope.formData.operators[0].name,
        form2: null,
        field2: null,
        value: null,
        date1: null,
        date2: null
    };

    $scope.sortFormData = function(formData) {
        formData.forms.sortByField('displayName');
    };

    $scope.fetchFormData = function() {
        $http.get('api/indicator/query/creationform').success(function(formData) {
            $scope.sortFormData(formData);
            $scope.formData.forms = formData.forms;
            $scope.condition.form1 = formData.forms[0];
        }).error(function() {
            $errorService.genericError($scope, 'queries.new.error.cannotLoadFormList');
        });
    };
    $scope.fetchFormData();

    $scope.fetchComputedFields1 = function(form) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');
            $scope.formData.computedFields1 = computedFields;
            $scope.condition.field1 = $scope.formData.computedFields1[0];
        }).error(function() {
            $errorService.genericError($scope, 'queries.new.error.cannotLoadComputedFieldList');
        });
    };

    $scope.$watch('condition.form1', function(newValue, oldValue) {
        if (newValue !== undefined && newValue != null) {
            $scope.fetchComputedFields1(newValue);
        }
    });

    $scope.save = function() {
        $scope.close($scope.condition);
    };

    $scope.close = function(result) {
        dialog.close(result);
    };
});

care.controller('createDwQueryController', function($rootScope, $scope, $http, $modal,
                                                    $dialog, $location, $errorService, $route, $timeout) {
    $scope.title = $scope.msg('queries.new.title');
    $scope.formData = {
        dimension: null,
        forms: [
            { tableName: null, displayName: '---' }
        ],
        functions: [
            { name: '---', code: 'none' },
            { name: $scope.msg('queries.new.function.average'), code: 'average' },
            { name: $scope.msg('queries.new.function.count'), code: 'count' },
            { name: $scope.msg('queries.new.function.max'), code: 'max' },
            { name: $scope.msg('queries.new.function.min'), code: 'min' },
            { name: $scope.msg('queries.new.function.sum'), code: 'sum' }
        ]
    }

    $scope.sortFormData = function(formData) {
        formData.forms.sortByField('displayName');
    };

    $scope.fetchFormData = function() {
        $http.get('api/indicator/query/creationform').success(function(formData) {
            $scope.sortFormData(formData);
            $scope.formData.forms = formData.forms;
            $scope.formData.forms.unshift({ tableName: null, displayName: '---' });
        }).error(function() {
            $errorService.genericError($scope, 'queries.new.error.cannotLoadFormList');
        });
    };

    $scope.fetchComputedFields = function(queryForm, form) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');
            computedFields.unshift({ name: 'ALL' });
            queryForm.listComputedFields = computedFields;
            queryForm.selectColumn.field = queryForm.listComputedFields[0];
        }).error(function() {
            $errorService.genericError($scope, 'queries.new.error.cannotLoadComputedFieldList');
        });
    };

    $scope.addQueryForm = function() {
        var queryForm = {
            joinType: $scope.selectedJoinType,
            whereGroup: null,
            selectColumns: [],
            selectColumn: {
                form: null,
                field: null,
                function: $scope.formData.functions[0].code,
                nullValue: null
            },
            listComputedFields: [
                { name: 'ALL' }
            ],
            addSelectColumn: function() {
                this.selectColumns.push({
                    form: (this.selectColumn.form.tableName == null) ? null : this.selectColumn.form,
                    field: (this.selectColumn.field.name == '---') ? null : this.selectColumn.field,
                    function: (this.selectColumn.function.name == '---') ? null : this.selectColumn.function,
                    nullValue: this.selectColumn.nullValue,
                    nullValueText: (this.selectColumn.nullValue == null) ? null : ', if null then ' + this.selectColumn.nullValue
                });
            },
            removeSelectColumn: function(index) {
                this.selectColumns.splice(index, 1);
            }
        };
        queryForm.selectColumn.field = queryForm.listComputedFields[0];
        $scope.queryForms.push(queryForm);

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].selectColumn.form', function(newValue, oldValue) {
            if (newValue !== undefined && newValue != null && newValue.tableName != null) {
                $scope.fetchComputedFields(queryForm, newValue);
            } else if (newValue != null && newValue.tableName == null) {
                queryForm.listComputedFields = [
                    { name: 'ALL' }
                ];
                queryForm.selectColumn.field = queryForm.listComputedFields[0];
            }
        });
    }

    $scope.initQueryForms = function() {
        $scope.listQueryJoinTypes = [
            'Union', 'UnionAll', 'Intersect', 'Except', 'Join'
        ];
        $scope.queryForms = [];
        $scope.addQueryForm();
        $scope.selectedJoinType = $scope.listQueryJoinTypes[0];
    };
    $scope.initQueryForms();
    $scope.fetchFormData();

    $scope.getWhereConditionDialog = function() {
        $rootScope.mainScope = $scope;

        return $dialog.dialog({
            backdrop: true,
            keyboard: false,
            backdropClick: false,
            templateUrl: 'resources/partials/indicators/whereConditionDialog.html',
            controller: 'whereConditionDialogController',
            dialogClass: 'modal modal-huge'
        });
    };

    // Access any data from the whereGroup directive inside a $timeout function
    $timeout(function() {

    });
});

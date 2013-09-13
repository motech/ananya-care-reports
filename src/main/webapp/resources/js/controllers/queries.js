var care = angular.module('care');

care.controller('whereConditionDialogController', function($rootScope, $scope, $http, $errorService, dialog) {
    $scope.mainScope = $rootScope.mainScope;
    delete $rootScope.mainScope;

    $scope.listConditionTypes = [
        { name: 'dateDiff', code: 'queries.whereConditionDialog.conditionType.dateDiff' },
        { name: 'dateRange', code: 'queries.whereConditionDialog.conditionType.dateRange' },
        { name: 'dateValue', code: 'queries.whereConditionDialog.conditionType.dateValue' },
        { name: 'enumRange', code: 'queries.whereConditionDialog.conditionType.enumRange' },
        { name: 'value', code: 'queries.whereConditionDialog.conditionType.value' },
        { name: 'field', code: 'queries.whereConditionDialog.conditionType.field' },
        { name: 'period', code: 'queries.whereConditionDialog.conditionType.period' }
    ];

    $scope.formData = {
        forms: [],
        computedFields1: [],
        computedFields2: [],
        allComputedFields1: [],
        allComputedFields2: [],
        operators: [],
        allOperators: [
            { name: '<' },
            { name: '<=' },
            { name: '=' },
            { name: '<>' },
            { name: '>' },
            { name: '>=' }
        ],
        newEnumRangeValue: null
    };
    $scope.formData.operators = [].concat($scope.formData.allOperators);

    $scope.condition = {
        type: $scope.listConditionTypes[0].name,
        form1: null,
        field1: null,
        operator: $scope.formData.operators[0],
        form2: null,
        field2: null,
        value: null,
        date1: null,
        date2: null,
        values: []
    };

    $scope.sortFormData = function(formData) {
        formData.forms.sortByField('displayName');
    };

    $scope.fetchFormData = function() {
        $http.get('api/indicator/query/creationform').success(function(formData) {
            $scope.sortFormData(formData);
            $scope.formData.forms = formData.forms;
            $scope.condition.form1 = formData.forms[0];
            $scope.condition.form2 = formData.forms[0];
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadFormList');
        });
    };
    $scope.fetchFormData();

    $scope.filterOperatorsByFieldType = function(type) {
        if (type === undefined || type == null) {
            return [];
        } else if (type == 'Number' || type == 'Date') {
            return [].concat($scope.formData.allOperators);
        } else if (type == 'String' || type == 'Boolean') {
            return [
                $scope.formData.allOperators[2],
                $scope.formData.allOperators[3]
            ];
        }

        return [].concat($scope.formData.allOperators);
    };

    $scope.filterComputedFieldsByTypes = function(index, types) {
        var computedFields = [];

        for (var i = $scope.formData['allComputedFields' + index].length - 1; i >= 0; i--) {
            for (var t = 0; t < types.length; t++) {
                var computedField = $scope.formData['allComputedFields' + index][i];
                if (computedField.type === types[t]) {
                    computedFields.push(computedField);
                }
            }
        }

        return computedFields;
    };

    $scope.filterComputedFieldsByComparisonType = function(index, computedFields) {
        if ($scope.condition.type == 'dateDiff' || $scope.condition.type == 'dateRange' || $scope.condition.type == 'dateValue'
            || $scope.condition.type == 'period') {
            return $scope.filterComputedFieldsByTypes(index, [ 'Date' ]);
        } else {
            return computedFields;
        }
    };

    $scope.fetchComputedFields = function(form, index) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');
            $scope.formData['allComputedFields' + index] = [].concat(computedFields);
            $scope.formData['computedFields' + index] = $scope.filterComputedFieldsByComparisonType(index, computedFields);
            var computedField = $scope.formData['computedFields' + index][0];
            $scope.condition['field' + index] = computedField;
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadComputedFieldList');
        });
    };

    $scope.$watch('condition.form1', function(newValue, oldValue) {
        if (newValue !== undefined && newValue != null) {
            $scope.fetchComputedFields(newValue, 1);
        }
    });

    $scope.$watch('condition.form2', function(newValue, oldValue) {
        if (newValue !== undefined && newValue != null) {
            $scope.fetchComputedFields(newValue, 2);
        }
    });

    $scope.$watch('condition.field1', function(newValue, oldValue) {
        if (newValue !== undefined && newValue != null) {
            $scope.formData.operators = $scope.filterOperatorsByFieldType(newValue.type);
            $scope.condition.operator = $scope.formData.operators[0];
            $scope.formData.computedFields2 = $scope.filterComputedFieldsByTypes(2, [ newValue.type ]);
            $scope.condition.field2 = $scope.formData.computedFields2[0];
            $scope.condition.values.length = 0;
            $scope.formData.newEnumRangeValue = null;
        }
    });

    $scope.$watch('condition.type', function(newValue, oldValue) {
        if (newValue !== undefined && newValue != null && $scope.condition.form1 != null) {
            $scope.fetchComputedFields($scope.condition.form1, 1);
        }
    });

    $scope.addValue = function() {
        if ($scope.formData.newEnumRangeValue === undefined || $scope.formData.newEnumRangeValue == null
                || $scope.formData.newEnumRangeValue.length <= 0) {
            return;
        }

       var constructValue = function(value) {
           function isNumber(n) {
               return !isNaN(parseFloat(value)) && isFinite(value);
           };

           var date = moment(value);
           if (!isNumber(value) && date.isValid()) {
               return date.format('YYYY-MM-DD');
           } else {
               return value;
           }
       };

        $scope.condition.values.push(constructValue($scope.formData.newEnumRangeValue));
    };

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
        queryName: null,
        dimension: null,
        forms: [
            { tableName: null, displayName: '---' }
        ],
        functions: [
            { name: '---', code: 'none' },
            { name: $scope.msg('queries.new.function.Average'), code: 'Average' },
            { name: $scope.msg('queries.new.function.Count'), code: 'Count' },
            { name: $scope.msg('queries.new.function.Max'), code: 'Max' },
            { name: $scope.msg('queries.new.function.Min'), code: 'Min' },
            { name: $scope.msg('queries.new.function.Sum'), code: 'Sum' }
        ],
        operators: [
            { name: '---' },
            { name: '<' },
            { name: '<=' },
            { name: '=' },
            { name: '<>' },
            { name: '>' },
            { name: '>=' }
        ],
    }

    $scope.sortFormData = function(formData) {
        formData.forms.sortByField('displayName');
    };

    $scope.initQueryForms = function() {
        $scope.listQueryJoinTypes = [
            { code: 'Union', name: $scope.msg('queries.new.joinType.union') },
            { code: 'UnionAll', name: $scope.msg('queries.new.joinType.unionAll') },
            { code: 'Intersect', name: $scope.msg('queries.new.joinType.intersect') },
            { code: 'Join', name: $scope.msg('queries.new.joinType.join') },
            { code: 'Except', name: $scope.msg('queries.new.joinType.except') }
        ];
        $scope.queryForms = [];
        $scope.addQueryForm();
        $scope.selectedJoinType = $scope.listQueryJoinTypes[0];
    };

    $scope.fetchFormData = function() {
        $http.get('api/indicator/query/creationform').success(function(formData) {
            $scope.sortFormData(formData);
            $scope.formData.dimensionForms = [].concat(formData.forms);
            $scope.formData.forms = [{ tableName: null, displayName: '---' }].concat(formData.forms);

            $scope.initQueryForms();
            $scope.queryForms[0].form = $scope.formData.forms[0];
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadFormList');
        });
    };
    $scope.fetchFormData();

    $scope.assignComputedFieldList = function(queryForm, computedFields) {
        queryForm.listComputedFields = [{ name: '*' }];

        if (computedFields === undefined || computedFields == null) {
            return;
        }

        computedFields.sortByField('name');
        queryForm.listComputedFields = queryForm.listComputedFields.concat(computedFields);
        queryForm.selectColumn.field = queryForm.listComputedFields[0];
    };

    $scope.fetchDimensionFields = function(queryForm, form) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');
            queryForm.listAllComputedFields = computedFields;
            queryForm.listGroupByComputedFields = [{ name: '---' }].concat(computedFields);
            queryForm.groupBy.field = queryForm.listGroupByComputedFields[0];
            queryForm.groupBy.having.function = queryForm.groupByFunctions[0];
            queryForm.groupBy.having.operator = $scope.formData.operators[0].name;
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadComputedFieldList');
        });
    };

    $scope.fetchComputedFields = function(queryForm, form) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            $scope.assignComputedFieldList(queryForm, computedFields);
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadComputedFieldList');
        });
    };

    $scope.filterFunctionsByType = function(type) {
        if (type === undefined || type == null) {
            return [
                $scope.formData.functions[0],
                $scope.formData.functions[2]
            ];
        } else if (type == 'Number') {
            return [].concat($scope.formData.functions);
        } else if (type == 'Date') {
            return [
                $scope.formData.functions[0],
                $scope.formData.functions[2],
                $scope.formData.functions[3],
                $scope.formData.functions[4]
            ];
        } else if (type == 'String' || type == 'Boolean') {
            return [
                $scope.formData.functions[0],
                $scope.formData.functions[2]
            ];
        }

        return [].concat($scope.formData.functions);
    };

    $scope.addQueryForm = function() {
        var queryForm = {
            dimension: $scope.formData.dimensionForms[0],
            joinType: $scope.selectedJoinType,
            key1: null,
            key2: null,
            whereGroup: null,
            groupBy: {
                field: null,
                having: {
                    function: null,
                    operator: null,
                    value: null
                }
            },
            selectColumns: [],
            selectColumn: {
                form: null,
                field: null,
                function: null,
                nullValue: null
            },
            listComputedFields: [],
            listGroupByComputedFields: [],
            listAllComputedFields: [],
            addSelectColumn: function() {
                this.selectColumns.push({
                    form: (this.selectColumn.form.tableName == null) ? null : this.selectColumn.form,
                    field: (this.selectColumn.field.name == '---') ? null : this.selectColumn.field,
                    function: (this.selectColumn.function.name == '---') ? null : this.selectColumn.function,
                    nullValue: this.selectColumn.nullValue,
                    nullValueText: (this.selectColumn.nullValue == null || this.selectColumn.nullValue == "")
                        ? null : ', if null then ' + this.selectColumn.nullValue
                });
            },
            removeSelectColumn: function(index) {
                this.selectColumns.splice(index, 1);
            },
            functions: [
                $scope.formData.functions[0],
                $scope.formData.functions[2]
            ],
            groupByFunctions: [
                $scope.formData.functions[0],
                $scope.formData.functions[2]
            ]
        };
        queryForm.selectColumn.function = queryForm.functions[0];
        $scope.queryForms.push(queryForm);

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].dimension', function(newValue, oldValue) {
            if (newValue !== undefined && newValue != null && newValue.tableName != null) {
                $scope.fetchDimensionFields(queryForm, newValue);
            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].selectColumn.form', function(newValue, oldValue) {
            if (newValue !== undefined && newValue != null && newValue.tableName != null) {
                $scope.fetchComputedFields(queryForm, newValue);
            } else if (newValue != null && newValue.tableName == null) {
                $scope.assignComputedFieldList(queryForm, null);
                queryForm.selectColumn.field = queryForm.listComputedFields[0];
            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].selectColumn.field', function(newValue, oldValue) {
            if (newValue !== undefined && newValue != null) {
                var functions = $scope.filterFunctionsByType(newValue.type);
                $scope.queryForms[$scope.queryForms.length - 1].functions = functions;
                $scope.queryForms[$scope.queryForms.length - 1].selectColumn.function = functions[0];
            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].groupBy.having.function', function(newValue, oldValue) {
            $scope.queryForms[$scope.queryForms.length - 1].groupBy.having.value = null;
            if (newValue == null || newValue.code == 'none') {
                $scope.queryForms[$scope.queryForms.length - 1].groupBy.having.operator = '---';
            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].groupBy.having.operator', function(newValue, oldValue) {
            if (newValue == undefined || newValue == null || newValue == '---') {
                $scope.queryForms[$scope.queryForms.length - 1].groupBy.having.value = null;
            }
        });

        queryForm.selectColumn.form = $scope.formData.forms[0];
    };

    $scope.removeQuery = function(index) {
        $scope.queryForms.splice(index, 1);
    };

    $scope.isFormValid = function() {
        if ($scope.formData.queryName == null || $scope.formData.queryName.length <= 0) {
            return false;
        }
        for (var i = 0; i < $scope.queryForms.length; i++) {
            if ($scope.queryForms[i].selectColumns == null || $scope.queryForms[i].selectColumns.length <= 0) {
                return false;
            }
            if (i > 0 && ($scope.queryForms[i].key1 == null || $scope.queryForms[i].key2 == null)) {
                return false;
            }
        }

        return true;
    };

    $scope.save = function() {
        var cleanSelectColumns = function(selectColumns) {
            for (var i = 0; i < selectColumns.length; i++) {
                selectColumns[i].field = (selectColumns[i].field.id === undefined) ? null : selectColumns[i].field.id;
                delete selectColumns[i].form;
                selectColumns[i].function = (selectColumns[i].function == null) ? null : selectColumns[i].function.code;
                delete selectColumns[i].nullValueText;
            }
        };
        var cleanWhereGroup = function(whereGroup) {
            delete whereGroup.uniqueId;
            delete whereGroup.hierarchyLevel;
            delete whereGroup.parentGroup;

            for (var i = 0; i < whereGroup.groups.length; i++) {
                cleanWhereGroup(whereGroup.groups[i]);
            }

            for (var i = 0; i < whereGroup.conditions.length; i++) {
                if (whereGroup.conditions[i].field1 != null) {
                    whereGroup.conditions[i].field1 = whereGroup.conditions[i].field1.id;
                }
                if (whereGroup.conditions[i].field2 != null) {
                    whereGroup.conditions[i].field2 = whereGroup.conditions[i].field2.id;
                }
            
                delete whereGroup.conditions[i].displayName;
                delete whereGroup.conditions[i].parentGroup;
                delete whereGroup.conditions[i].uniqueId;
            }
        };

        var constructDwQuery = function(queryForm) {
            cleanSelectColumns(queryForm.selectColumns);
            cleanWhereGroup(queryForm.whereGroup);

            return {
                name: $scope.formData.queryName,
                dimension: queryForm.dimension.tableName,
                selectColumns: queryForm.selectColumns,
                joinType: null,
                key1: null,
                key2: null,
                combineWith: null,
                groupBy: null,
                whereGroup: queryForm.whereGroup,
                addCombineWith: function(joinType, key1, key2, dwQuery) {
                    this.key1 = key1;
                    this.key2 = key2;
                    this.joinType = joinType.code;
                    this.combineWith = dwQuery;
                },
                addGroupBy: function(groupBy) {
                    if (groupBy.field == null || groupBy.field.name == '---') {
                        return;
                    }

                    this.groupBy = {
                        tableName: this.dimension,
                        fieldName: groupBy.field.name,
                        fieldId: groupBy.field.id,
                        having: null
                    };

                    if (groupBy.having != null && groupBy.having.operator != '---') {
                        this.groupBy.having = {
                            function: groupBy.having.function.code,
                            operator: groupBy.having.operator,
                            value: groupBy.having.value
                        };
                    }
                }
            };
        };

        var lastDwQuery = null;
        for (var i = $scope.queryForms.length - 1; i >= 0; i--) {
            var dwQuery = constructDwQuery($scope.queryForms[i]);
            if (i > 0) {
                dwQuery.name += '_' + i;
            }

            if (lastDwQuery != null) {
                var queryForm = $scope.queryForms[i + 1];
                dwQuery.addCombineWith(queryForm.joinType, queryForm.key1, queryForm.key2, lastDwQuery);
            }
            if ($scope.queryForms[i].groupBy != null) {
                dwQuery.addGroupBy($scope.queryForms[i].groupBy);
            }

            lastDwQuery = dwQuery;
        }

        $http({
            url: 'api/indicator/queries/new',
            method: "POST",
            data: lastDwQuery,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(response) {
            $location.path('/indicators/new');
        }).error(function(data, status, headers, config) {
            $dialog.messageBox($scope.msg('common.error'), data, [{label: $scope.msg('common.ok'), cssClass: 'btn'}]).open();
        });
    };
});

care.controller('queryListController', function($scope, $http, $location, $dialog, $errorService) {
    $scope.queries = [];

    $scope.fetchQueries = function() {
        $http.get('api/indicator/queries').success(function(queries) {
            queries.sortByField('name');
            $scope.queries = queries;
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadQueryList');
        });
    };
    $scope.fetchQueries();

    $scope.deleteQuery = function(index) {
        var query = $scope.queries[index];
        var btns = [{result:'yes', label: $scope.msg('common.yes'), cssClass: 'btn-primary btn'}, {result:'no', label: $scope.msg('common.no'), cssClass: 'btn-danger btn'}];
        $dialog.messageBox($scope.msg('queries.list.confirmDelete.header', query.name), $scope.msg('queries.list.confirmDelete.message', query.name), btns)
            .open()
            .then(function(result) {
                if (result === 'yes') {
                    $http({
                        method: 'DELETE',
                        url: 'api/indicator/queries/' + query.id
                    })
                    .success(function(data, status, headers, config) {
                        $scope.queries.splice(index, 1);
                    }).error(function(response) {
                        $errorService.genericError($scope, 'queries.list.error.cannotDeleteQuery', query.name);
                    });
                }
            });
    };
});

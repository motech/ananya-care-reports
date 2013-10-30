var care = angular.module('care');

Array.prototype.getByField = function(name, value) {
    var tempArray = [];

    for (var i = 0; i < this.length; i++) {
        if (!this.hasOwnProperty(i)) {
            continue;
        }

        if (this[i][name] == value) {
            tempArray.push(this[i]);
        }
    }

    return tempArray;
};

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
        { name: 'period', code: 'queries.whereConditionDialog.conditionType.period' },
        { name: 'calculationEndDate', code: 'queries.whereConditionDialog.conditionType.calculationEndDate' }
    ];

    $scope.formData = {
        forms: [],
        computedFields1: [],
        computedFields2: [],
        allComputedFields1: [],
        allComputedFields2: [],
        operators: [],
        allOperators: [
            '<',
            '<=',
            '=',
            '<>',
            '>',
            '>='
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
        $http.get('api/indicator/queries/creationform').success(function(formData) {
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
            || $scope.condition.type == 'period' || $scope.condition.type == 'calculationEndDate') {
            return $scope.filterComputedFieldsByTypes(index, [ 'Date' ]);
        } else {
            return computedFields;
        }
    };

    $scope.fetchComputedFields = function(form, index) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');

            for (var i = 0; i < computedFields.length; i++) {
                if (computedFields[i].name == '*') {
                    computedFields.splice(i, 1);
                    break;
                }
            }

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

    $scope.removeValue = function(index) {
        $scope.condition.values.splice(index, 1);
    };

    $scope.save = function() {
        $scope.condition.field1.form = $scope.condition.form1;
        if ($scope.condition.field2 != null) {
            $scope.condition.field2.form = $scope.condition.form2;
        }

        $scope.close($scope.condition);
    };

    $scope.close = function(result) {
        dialog.close(result);
    };
});

care.controller('createDwQueryController', function($rootScope, $scope, $http, $modal, $dialog, $location,
                                                    $errorService, $route, $timeout, $routeParams) {
    $scope.title = 'queries.new.title';

    $scope.editQueryId = $routeParams['queryId'];
    $scope.isEdit = ($scope.editQueryId !== undefined && $scope.editQueryId !== null);

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
            { name: $scope.msg('queries.new.function.Sum'), code: 'Sum' },
            { name: $scope.msg('queries.new.function.Median'), code: 'Median' },
            { name: $scope.msg('queries.new.function.Mode'), code: 'Mode' },
            { name: $scope.msg('queries.new.function.StandardDeviation'), code: 'StandardDeviation' }
        ],
        operators: [
            '---',
            '<',
            '<=',
            '<>',
            '>',
            '>='
        ]
    };
    $scope.listQueryJoinTypes = [
        { code: 'Union', name: $scope.msg('queries.new.joinType.union') },
        { code: 'UnionAll', name: $scope.msg('queries.new.joinType.unionAll') },
        { code: 'Intersect', name: $scope.msg('queries.new.joinType.intersect') },
        { code: 'Join', name: $scope.msg('queries.new.joinType.join') },
        { code: 'Except', name: $scope.msg('queries.new.joinType.except') }
    ];
    $scope.queryForms = [];

    $scope.sortFormData = function(formData) {
        formData.forms.sortByField('displayName');
    };

    if ($scope.isEdit === true) {
        $scope.copyQueryEntityToQueryForm = function(queryForm, query) {
            queryForm.dimension = $scope.formData.dimensionForms.getByField('tableName', query.dimension)[0];
            queryForm.whereGroup = query.whereGroup;

            if (query.joinType != null) {
                queryForm.joinType = $scope.listQueryJoinTypes.getByField('code', query.joinType)[0];
                queryForm.key1 = query.key1;
                queryForm.key2 = query.key2;
            }

            for (var i = 0; i < query.selectColumns.length; i++) {
                var column = query.selectColumns[i];
                var selectColumn = {
                    form: (column.field == null) ? null
                        : $scope.formData.forms.getByField('id', column.field.form.id)[0],
                    field: (column.field == null) ? { name: '*' } : column.field,
                    function: (column.function == null) ? null : $scope.formData.functions.getByField('code', column.function)[0],
                    nullValue: column.nullValue,
                    nullValueText: (column.nullValue == null || column.nullValue == '')
                        ? null : ', if null then ' + column.nullValue
                };
                console.log(selectColumn);
                queryForm.selectColumns.push(selectColumn);
            }

            if (query.groupBy != null) {
                queryForm.editGroupBy = {
                    field: query.groupBy.computedField,
                    having: (query.groupBy.having == null) ? null : {
                        function: query.groupBy.having.function,
                        operator: query.groupBy.having.operator,
                        value: parseInt(query.groupBy.having.value)
                    }
                };
            }
        };

        $scope.extractQueriesIntoArray = function(array, query) {
            var queryForm = $scope.addQueryForm();
            $scope.copyQueryEntityToQueryForm(queryForm, query);

            if (query.combineWith != null) {
                $scope.extractQueriesIntoArray(array, query.combineWith);
            }
        };

        $scope.fetchQuery = function() {
            $http.get('api/indicator/queries/' + $scope.editQueryId).success(function(query) {
                $scope.formData.queryName = query.name;
                $scope.formData.originalQueryName = query.name;
                $scope.extractQueriesIntoArray($scope.formData.queryForms, query);
            }).error(function() {
                $errorService.genericError($scope, 'queries.edit.error.cannotLoadQuery');
            });
        };

        $scope.fetchQuery();
    }

    $scope.fetchFormData = function() {
        $http.get('api/indicator/queries/creationform').success(function(formData) {
            $scope.sortFormData(formData);
            $scope.formData.dimensionForms = [].concat(formData.forms);
            $scope.formData.forms = $scope.formData.forms.concat(formData.forms);
            $scope.selectedJoinType = $scope.listQueryJoinTypes[0];

            if ($scope.isEdit === false) {
                $scope.addQueryForm();
                $scope.queryForms[0].form = $scope.formData.forms[0];
            }
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadFormList');
        });
    };
    $scope.fetchFormData();

    $scope.assignComputedFieldList = function(queryForm, computedFields) {
        if (computedFields === undefined) {
            return;
        }
        if (computedFields == null) {
            queryForm.listComputedFields = [{ name: '*' }];
            return;
        }

        computedFields.sortByField('name');
        queryForm.listComputedFields = computedFields;
        queryForm.selectColumn.field = queryForm.listComputedFields[0];
    };

    $scope.fetchDimensionFields = function(queryForm, form) {
        $http.get('api/forms/' + form.id + '/computedfields/all').success(function(computedFields) {
            computedFields.sortByField('name');
            queryForm.listAllComputedFields = computedFields;
            queryForm.listGroupByComputedFields = [{ name: '---' }].concat(computedFields);

            for (var i = 0; i < queryForm.listGroupByComputedFields.length; i++) {
                if (queryForm.listGroupByComputedFields[i].name == '*') {
                    queryForm.listGroupByComputedFields.splice(i, 1);
                    break;
                }
            }

            if (queryForm.editGroupBy === undefined) {
                queryForm.groupBy.field = queryForm.listGroupByComputedFields[0];
                queryForm.groupBy.having.function = queryForm.groupByFunctions[0];
                queryForm.groupBy.having.operator = $scope.formData.operators[0];
            } else {
                queryForm.groupBy.field = queryForm.listGroupByComputedFields.getByField('id', queryForm.editGroupBy.field.id)[0];

                if (queryForm.editGroupBy.having == null) {
                    delete queryForm.editGroupBy;
                } else {
                    queryForm.groupBy.having.function = queryForm.groupByFunctions.getByField('code', queryForm.editGroupBy.having.function)[0];
                    queryForm.groupBy.having.operator = queryForm.editGroupBy.having.operator;
                    queryForm.groupBy.having.value = queryForm.editGroupBy.having.value;
                }

                $timeout(function() {
                    delete queryForm.editGroupBy;
                });
            }
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
            joinType: ($scope.queryForms.length > 0) ? $scope.selectedJoinType : null,
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
            var queryForm = $scope.queryForms[$scope.queryForms.length - 1];

            if (newValue !== undefined && newValue != null) {
                if (newValue.name == '*') {
                    queryForm.functions = [ $scope.formData.functions[0], $scope.formData.functions[2] ];
                    queryForm.selectColumn.function = $scope.formData.functions[0];
                } else {
                    var functions = $scope.filterFunctionsByType(newValue.type);
                    queryForm.functions = functions;
                    queryForm.selectColumn.function = functions[0];
                }

            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].groupBy.having.function', function(newValue, oldValue) {
            var queryForm = $scope.queryForms[$scope.queryForms.length - 1];

            if (queryForm.editGroupBy !== undefined && queryForm.editGroupBy.having != null) {
                queryForm.groupBy.having.operator = queryForm.editGroupBy.having.operator;
                return;
            }

            if (newValue == null || newValue.code == 'none') {
                queryForm.groupBy.having.operator = '---';
            }
        });

        $scope.$watch('queryForms[' + ($scope.queryForms.length - 1) + '].groupBy.having.operator', function(newValue, oldValue) {
            var queryForm = $scope.queryForms[$scope.queryForms.length - 1];

            if (queryForm.editGroupBy !== undefined && queryForm.editGroupBy.having != null) {
                queryForm.groupBy.having.value = queryForm.editGroupBy.having.value;
                return;
            }

            if (newValue == undefined || newValue == null || newValue == '---') {
                queryForm.groupBy.having.value = null;
            }
        });

        queryForm.selectColumn.form = $scope.formData.forms[0];
        return queryForm;
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

            if (i > 0 && ($scope.queryForms[i].joinType.code == 'Join'
                    && ($scope.queryForms[i].key1 == null || $scope.queryForms[i].key2 == null))) {
                return false;
            }
        }

        return true;
    };

    $scope.save = function(update) {
        var convertSelectColumnsToDto = function(selectColumns) {
            var newColumns = [];

            for (var i = 0; i < selectColumns.length; i++) {
                var column = selectColumns[i];
                newColumns.push({
                    field: column.field,
                    function: (column.function == null) ? null : column.function.code,
                    nullValue: column.nullValue
                });
            }

            return newColumns;
        };

        var convertConditionsToDto = function(conditions) {
            var newConditions = [];

            for (var i = 0; i < conditions.length; i++) {
                var condition = conditions[i];
                if (condition == null) {
                    continue;
                }

                newConditions.push({
                    field1: condition.field1,
                    field2: condition.field2,
                    offset1: condition.offset1,
                    offset2: condition.offset2,
                    date1: condition.date1,
                    date2: condition.date2,
                    type: condition.type,
                    operator: condition.operator,
                    value: condition.value,
                    values: condition.values
                });
            }

            return newConditions;
        };

        var convertWhereGroupToDto = function(whereGroup) {
            var group = {
                conditions: (whereGroup.conditions == null) ? null : convertConditionsToDto(whereGroup.conditions),
                groups: [],
                operator: whereGroup.operator
            };

            if (whereGroup.groups != null) {
                for (var i = 0; i < whereGroup.groups.length; i++) {
                    group.groups.push(convertWhereGroupToDto(whereGroup.groups[i]));
                }
            }

            return group;
        };

        var constructDwQuery = function(queryForm) {
            console.log(queryForm);

            return {
                name: $scope.formData.queryName,
                dimension: queryForm.dimension.tableName,
                selectColumns: convertSelectColumnsToDto(queryForm.selectColumns),
                joinType: null,
                key1: null,
                key2: null,
                combineWith: null,
                groupBy: null,
                whereGroup: (queryForm.whereGroup == null) ? null : convertWhereGroupToDto(queryForm.whereGroup),
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
                        computedField: groupBy.field,
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

        console.log(lastDwQuery);

        var url = (update === true) ? 'api/indicator/queries/' + $scope.editQueryId : 'api/indicator/queries/new';
        var httpMethod = (update === true) ? 'PUT' : 'POST';

        $http({
            url: url,
            method: httpMethod,
            data: lastDwQuery,
            headers: { 'Content-Type': 'application/json' }
        }).success(function(response) {
            $location.path('/indicators/new');
        }).error(function(response) {
            $errorService.stackTraceDialog($scope, response);
        });
    };
});

care.controller('queryListController', function($rootScope, $scope, $http, $location, $dialog, $errorService) {
    $scope.queries = [];
    $scope.title = 'queries.title';

    $scope.fetchQueries = function() {
        $http.get('api/indicator/queries').success(function(queries) {
            queries.sortByField('name');
            $scope.queries = queries;
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadQueryList');
        });
    };
    $scope.fetchQueries();

    $scope.viewQuerySql = function(index) {
        var query = $scope.queries[index];
        $http.get('api/indicator/queries/' + query.id + '/getsql').success(function(sql) {
            $rootScope.sql = sql;

            $dialog.dialog({
                backdrop: true,
                keyboard: true,
                backdropClick: false,
                templateUrl: 'resources/partials/indicators/querySqlDialog.html',
                controller: 'querySqlDialogController',
                dialogClass: 'modal modal-huge'
            }).open();
        }).error(function() {
            $errorService.genericError($scope, 'queries.error.cannotLoadQuerySql');
        });
    };

    $scope.editQuery = function(index) {
        $location.path('/indicators/queries/edit/' + $scope.queries[index].id);
    };

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
                        $errorService.genericError($scope, 'queries.error.cannotDeleteQuery', query.name);
                    });
                }
            });
    };
});

care.controller('querySqlDialogController', function($rootScope, $scope, dialog) {
    $scope.sql = $rootScope.sql;
    delete $rootScope.sql;

    $scope.close = function(result) {
        dialog.close(result);
    };
});

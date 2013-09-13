(function () {
    'use strict';

    /* Directives */

    var widgetModule = angular.module('care');

    widgetModule.directive('userPopover', function () {
       return {
           restrict: 'A',
           link: function (scope, element, attrs) {
                angular.element(element).popover({
                    placement: 'left',
                    trigger: 'hover',
                    html: true,
                    title: scope.msg('users.list.popoverTitle') + ":",
                    content: function () {
                        var html = angular.element('<ul />');
                        for (var i in scope.user.roles) {
                            if (scope.user.roles.hasOwnProperty(i)) {
                                var li = angular.element('<li />');
                                li.text(scope.user.roles[i].name);
                                html.append(li);
                            }
                        }

                        return html;
                    }
                });
           }
       };
    });

    widgetModule.directive('rolePopover', function () {
           return {
               restrict: 'A',
               link: function (scope, element, attrs) {
                    angular.element(element).popover({
                        placement: 'left',
                        trigger: 'hover',
                        html: true,
                        title: scope.msg('roles.list.popoverTitle') + ":",
                        content: function () {
                            var html = angular.element('<ul />');
                            for (var i in scope.role.permissions) {
                                if (scope.role.permissions.hasOwnProperty(i)) {
                                    var li = angular.element('<li />');
                                    li.text(scope.role.permissions[i].displayName);
                                    html.append(li);
                                }
                            }

                            return html;
                        }
                    });
               }
           };
        });

        widgetModule.directive('indicatorsTrend', function () {
               return {
                   restrict: 'A',
                   link: function (scope, element, attrs) {
                        var updateTrends = function() {
                            var indicatorClassification, tr, spanTrend, indicator,
                                trend, labelClass, calc;
                            element.html('');
                            if (scope.indicatorClassifications == undefined) {
                                return;
                            }
                            scope.indicatorClassifications.sort(function(a, b) {
                                if (a.name > b.name) {
                                    return 1;
                                } else if (a.name < b.name) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            });
                            for (var c in scope.indicatorClassifications) {
                                if (!scope.indicatorClassifications.hasOwnProperty(c)) {
                                    continue;
                                }
                                indicatorClassification = scope.indicatorClassifications[c];
                                tr = angular.element("<tr/>").addClass("performance-summary-classification").append(
                                    angular.element("<td/>").attr("colspan", "2").html(indicatorClassification.name));
                                element.append(tr);
                                indicatorClassification.indicators.sort(function(a, b) {
                                    if (a.indicator.name > b.indicator.name) {
                                        return 1;
                                    } else if (a.indicator.name < b.indicator.name) {
                                        return -1;
                                    } else {
                                        return 0;
                                    }
                                });
                                for (var i in indicatorClassification.indicators) {
                                    if (!indicatorClassification.indicators.hasOwnProperty(i)) {
                                        continue;
                                    }
                                    indicator = indicatorClassification.indicators[i];
                                    switch (indicator.trend) {
                                    case 0:
                                        trend = scope.msg('dashboards.trends.trend.neutral');
                                        labelClass = "label-info";
                                        break;
                                    case 1:
                                        trend = scope.msg('dashboards.trends.trend.positive');
                                        labelClass = "label-success";
                                        break;
                                    case -1:
                                        trend = scope.msg('dashboards.trends.trend.negative');
                                        labelClass = "label-important";
                                        break;
                                    }
                                    if(!indicator.indicator.isComputed) {
                                        trend = scope.msg('dashboards.trends.progress');
                                        labelClass = "";
                                    }
                                    tr = angular.element("<tr/>").append(
                                        angular.element("<td/>").html(indicator.indicator.name)
                                    ).append(
                                        angular.element("<td/>").append(
                                            angular.element("<span/>").html(trend).addClass("label").addClass(labelClass)
                                        )
                                    );
                                    element.append(tr);
                                }
                            }
                        }

                        scope.$watch('indicatorClassifications', function(oldVal, newVal) {
                            updateTrends();
                       });
                   }
               };
            });

        widgetModule.directive('indicatorChart', function($http, $timeout, $dialog) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    attrs.$observe('value', function(report) {
                        var report;
                        if (!attrs.value || !element[0]) {
                            return;
                        }
                        report = JSON.parse(attrs.value);
                        if (report.displayType != "chart") {
                            return;
                        }
                        if (report.computing === null) {
                            $(element[0]).html("<p class='text-center' style='margin-top: 50px;'>" + scope.msg('dashboards.charts.computing') + "</p>");
                            return;
                        }
                        var createChart = function() {
                            $(element).html("<img src='/resources/images/loading.gif' />");
                            if(report.frequencyId != undefined && report.areaId != undefined) {
                                var url = 'api/chart?chartType=' + report.reportType.name.toLowerCase()
                                    + '&indicatorId=' + report.indicatorId
                                    + '&startDate=' + moment(report.from).format("L")
                                    + '&endDate=' + moment(report.to).format("L")
                                    + "&frequencyId=" + report.frequencyId
                                    + "&areaId=" + report.areaId;

                                $http.get(url).success(function(chart) {
                                    element.html = '';
                                    if (chart.settings.title != undefined) {
                                        delete chart.settings.title;
                                    }
                                    if (chart.data.length > 1 && report.reportType.name.toLowerCase() == 'bar chart') {
                                        chart.settings.mouse.trackFormatter = function(obj) {
                                            return obj.y;
                                        };
                                    } else {
                                        chart.settings.mouse.trackFormatter = function(obj) {
                                            return '('+moment(new Date(parseInt(obj.x))).format("MM/DD/YYYY")+', '+obj.y+')';
                                        };
                                    }
                                    var drawChart = function(opts) {
                                            var o = Flotr._.extend(Flotr._.clone(chart.settings), opts || {});
                                            Flotr.draw(element[0], chart.data, o);
                                    };

                                    drawChart();

                                    if(chart.settings.pie == undefined) {
                                        Flotr.EventAdapter.observe(element[0], 'flotr:select', function(area) {
                                            drawChart({
                                                xaxis: {
                                                    min: area.x1,
                                                    max: area.x2
                                                }
                                            });
                                        });

                                        Flotr.EventAdapter.observe(element[0], 'flotr:click', function() {
                                            drawChart();
                                        });
                                    }
                                }).error(function(data, status, headers, config) {
                                    $dialog.messageBox(scope.msg('common.error'), data, [{label: scope.msg('common.ok'), cssClass: 'btn'}]).open();
                                });
                            }
                        };
                        $timeout(createChart, 0);
                    });
                }
            }
        });

        widgetModule.directive('whereGroup', function($http, $timeout, $dialog, $modal, $q) {
            return {
                restrict: 'A',
                scope: {
                    msg: '=',
                    query: '='
                },
                link: function(scope, element, attrs) {
                    $timeout(function() {
                        var query = scope.query;

                        var constructFieldName = function(tableName, fieldName) {
                            return tableName + '.' + fieldName;
                        };
                        var constructFieldNameWithOffset = function(tableName, fieldName, fieldOffset) {
                            if (fieldOffset !== undefined && fieldOffset != null) {
                                return '(' + tableName + '.' + fieldName + ' ' + ((fieldOffset < 0) ? '-' : '+')
                                    + ' ' + Math.abs(fieldOffset) + ')';
                            } else {
                                return tableName + '.' + fieldName;
                            }
                        };
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

                        var constructCondition = function(parentGroup) {
                            return {
                                uniqueId: new Date().getTime(),
                                parentGroup: parentGroup,
                                type: null,
                                tableName1: null,
                                field1: null,
                                tableName2: null,
                                field2: null,
                                operator: null,
                                value: null,
                                values: [],
                                fieldOffset1: null,
                                fieldOffset2: null,
                                date1: null,
                                date2: null,
                                createValueComparison: function(tableName, field, operator, value) {
                                    this.type = 'value';
                                    this.tableName1 = tableName;
                                    this.field1 = field;
                                    this.operator = operator.name;
                                    this.value = value;
                                    this.displayName = constructFieldName(tableName, field.name) + ' ' + operator.name
                                                       + ' ' + constructValue(value);
                                },
                                createDateDiffComparison: function(tableName1, field1, fieldOffset1, tableName2,
                                                                    field2, fieldOffset2, operator, value) {
                                    this.type = 'dateDiff';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.fieldOffset1 = fieldOffset1;
                                    this.tableName2 = tableName2;
                                    this.field2 = field2;
                                    this.fieldOffset2 = fieldOffset2;
                                    this.operator = operator.name;
                                    this.value = value;
                                    this.displayName = constructFieldNameWithOffset(tableName1, field1.name, fieldOffset1)
                                                       + ' - ' + constructFieldNameWithOffset(tableName2, field2.name, fieldOffset2)
                                                       + ' ' + operator.name + ' ' + constructValue(value);
                                },
                                createDateRangeComparison: function(tableName1, field1, fieldOffset1, date1, date2) {
                                    this.type = 'dateRange';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.fieldOffset1 = fieldOffset1;
                                    this.date1 = date1;
                                    this.date2 = date2;
                                    this.displayName = constructFieldNameWithOffset(tableName1, field1.name, fieldOffset1)
                                                       + ' between ' + constructValue(date1) + ' and ' + constructValue(date2);
                                },
                                createDateValueComparison: function(tableName1, field1, fieldOffset1, operator, value) {
                                    this.type = 'dateValue';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.fieldOffset1 = fieldOffset1;
                                    this.operator = operator.name;
                                    this.value = value;
                                    this.displayName = constructFieldNameWithOffset(tableName1, field1.name, fieldOffset1)
                                                       + ' ' + operator.name + ' ' + constructValue(value);
                                },
                                createEnumRangeComparison: function(tableName1, field1, values) {
                                    this.type = 'enumRange';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.values = values;

                                    this.displayName = constructFieldName(tableName1, field1.name)
                                                       + ' in (' + values.join(', ') + ')';
                                },
                                createFieldComparison: function(tableName1, field1, fieldOffset1, tableName2,
                                                                 field2, fieldOffset2, operator) {
                                    this.type = 'field';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.fieldOffset1 = fieldOffset1;
                                    this.operator = operator.name;
                                    this.tableName2 = tableName2;
                                    this.field2 = field2;
                                    this.fieldOffset2 = fieldOffset2;
                                    this.displayName = constructFieldNameWithOffset(tableName1, field1.name, fieldOffset1)
                                                       + ' ' + operator.name + ' '
                                                       + constructFieldNameWithOffset(tableName2, field2.name, fieldOffset2)
                                },
                                createPeriodCondition: function(tableName1, field1, fieldOffset1) {
                                    this.type = 'period';
                                    this.tableName1 = tableName1;
                                    this.field1 = field1;
                                    this.fieldOffset1 = fieldOffset1;
                                    this.displayName = scope.msg('queries.new.label.periodCondition') + ': '
                                                       + constructFieldNameWithOffset(tableName1, field1.name, fieldOffset1);
                                },
                                constructElement: function() {
                                    var conditionElement = angular.element('<div />');
                                    var removeButton = angular.element('<button />').addClass('btn btn-mini btn-append')
                                        .append(angular.element('<i />').addClass('icon-trash'));

                                    conditionElement.text(this.displayName);
                                    conditionElement.append(removeButton);
                                    removeButton.click(function() {
                                        conditionElement.remove();
                                        parentGroup.removeCondition(this.uniqueId);
                                    });

                                    return conditionElement;
                                }
                            };
                        };

                        var constructWhereGroup = function(parentGroup) {
                            return {
                                uniqueId: new Date().getTime(),
                                parentGroup: parentGroup,
                                hierarchyLevel: 0,
                                groups: [],
                                conditions: [],
                                operator: 'and',
                                removeGroup: function(uniqueId) {
                                    var index = -1;
                                    for (var i = 0; i < this.groups.length; i++) {
                                        if (this.groups[i].uniqueId == uniqueId) {
                                            index = i;
                                            break;
                                        }
                                    }

                                    this.groups.splice(index, 1);
                                },
                                removeCondition: function(uniqueId) {
                                    var index = -1;
                                    for (var i = 0; i < this.conditions.length; i++) {
                                        if (this.conditions[i].uniqueId == uniqueId) {
                                            index = i;
                                            break;
                                        }
                                    }

                                    this.conditions.splice(index, 1);
                                },
                                constructElement: function() {
                                    var constructAddGroupButton = function(div, group) {
                                        return angular.element('<button />').addClass('btn btn-mini')
                                            .append(angular.element('<i />').addClass('icon-th-list'))
                                            .click(function() {
                                                var newGroup = constructWhereGroup(group);
                                                newGroup.hierarchyLevel = group.hierarchyLevel + 1;

                                                if (group.groups.length > 0) {
                                                    var andOption = angular.element('<option />').val('and')
                                                        .text(scope.msg('queries.new.option.and'));
                                                    var orOption = angular.element('<option />').val('or')
                                                        .text(scope.msg('queries.new.option.or'));

                                                    var select = angular.element('<select />')
                                                        .append(andOption)
                                                        .append(orOption)
                                                        .change(function(value) {
                                                            group.operator = $(this).val();
                                                    })
                                                    div.append(select);
                                                }

                                                div.append(newGroup.constructElement());
                                                group.groups.push(newGroup);
                                        });
                                    };
                                    var resolveConditionType = function(condition, result) {
                                        switch (result.type) {
                                            case 'dateDiff':
                                                condition.createDateDiffComparison(result.form1.tableName,
                                                    result.field1, result.offset1, result.form2.tableName,
                                                    result.field2, result.offset2, result.operator, result.value);
                                                break;
                                            case 'dateRange':
                                                condition.createDateRangeComparison(result.form1.tableName,
                                                    result.field1, result.offset1, result.date1, result.date2);
                                                break;
                                            case 'dateValue':
                                                condition.createDateValueComparison(result.form1.tableName,
                                                    result.field1, result.offset1, result.operator, result.value);
                                                break;
                                            case 'enumRange':
                                                condition.createEnumRangeComparison(result.form1.tableName,
                                                    result.field1, result.values);
                                                break;
                                            case 'field':
                                                condition.createFieldComparison(result.form1.tableName, result.field1,
                                                    result.offset1, result.form2.tableName, result.field2,
                                                    result.offset2, result.operator);
                                                break;
                                            case 'value':
                                                condition.createValueComparison(result.form1.tableName,
                                                    result.field1, result.operator, result.value);
                                                break;
                                            case 'period':
                                                condition.createPeriodCondition(result.form1.tableName,
                                                    result.field1, result.offset1);
                                                break;
                                            default:
                                                return;
                                        }
                                    };
                                    var constructAddConditionButton = function(div, group) {
                                        return angular.element('<button />').addClass('btn btn-mini')
                                            .append(angular.element('<i />').addClass('icon-plus-sign'))
                                            .click(function() {
                                                var dialog = scope.$apply($dialog.dialog({
                                                    backdrop: true,
                                                    keyboard: false,
                                                    backdropClick: false,
                                                    templateUrl: 'resources/partials/indicators/whereConditionDialog.html',
                                                    controller: 'whereConditionDialogController',
                                                    dialogClass: 'modal modal-huge'
                                                }).open().then(function(result) {
                                                    if (result == 'cancel') {
                                                        return;
                                                    }

                                                    var newCondition = constructCondition(group);
                                                    resolveConditionType(newCondition, result);
                                                    div.append(newCondition.constructElement());
                                                    group.conditions.push(newCondition);
                                                }));
                                            });
                                    };
                                    var constructRemoveGroupButton = function(div, group) {
                                        if (group.hierarchyLevel <= 0) { return; }

                                        return angular.element('<button />').addClass('btn btn-mini')
                                            .append(angular.element('<i />').addClass('icon-trash'))
                                            .click(function() {
                                                if (parentGroup.groups[0].uniqueId == group.uniqueId) {
                                                    div.next('select').remove();
                                                }
                                                else {
                                                    div.prev('select').remove();
                                                }

                                                div.remove();
                                                parentGroup.removeGroup(group.uniqueId);
                                            });
                                    };

                                    var whereGroupElement = angular.element('<div />').addClass('where-group');
                                    var openingBrace = angular.element('<div />').text('(');
                                    var closingBrace = angular.element('<div />').text(')');
                                    var groupsElement = angular.element('<div />');
                                    var conditionsElement = angular.element('<div />');
                                    var buttonsElement = angular.element('<div />')
                                        .append(constructAddGroupButton(groupsElement, this))
                                        .append(constructAddConditionButton(conditionsElement, this))
                                        .append(constructRemoveGroupButton(whereGroupElement, this));

                                    whereGroupElement.append(openingBrace);
                                    whereGroupElement.append(groupsElement);
                                    whereGroupElement.append(conditionsElement);
                                    whereGroupElement.append(buttonsElement);
                                    whereGroupElement.append(closingBrace);

                                    return whereGroupElement;
                                }
                            };
                        };

                        var whereGroup = (query.whereGroup == null) ? constructWhereGroup(null) : query.whereGroup;
                        element.append(whereGroup.constructElement());
                        query.whereGroup = whereGroup;
                    });
                }
            }
        });

}());

(function () {
    'use strict';

    /* Directives */

    var widgetModule = angular.module('care');

    widgetModule.directive('tip', function ($timeout) {
        return {
            restrict: 'E',
            link: function (scope, element, attrs) {
            $timeout(function() {
                    var placement = (attrs['placement'] === undefined || attrs['placement'] === null)
                        ? 'right' : attrs['placement'];

                    element.append(angular.element('<span class="add-on btn"><i class="icon-question-sign"></i></span>'))
                    .popover({
                        placement: placement,
                        trigger: 'click',
                        content:  scope.msg(attrs.msg)
                    });
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
                                field1: null,
                                field2: null,
                                operator: null,
                                value: null,
                                values: [],
                                offset1: null,
                                offset2: null,
                                date1: null,
                                date2: null,
                                createValueComparison: function(field1, operator, value) {
                                    this.type = 'value';
                                    this.field1 = field1;
                                    this.operator = operator;
                                    this.value = value;
                                    this.displayName = constructFieldName(field1.form.tableName, field1.name) + ' ' + operator
                                                       + ' ' + constructValue(value);
                                },
                                createDateDiffComparison: function(field1, offset1, field2, offset2, operator, value) {
                                    this.type = 'dateDiff';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.field2 = field2;
                                    this.offset2 = (offset2 == null) ? 0 : offset2;
                                    this.operator = operator;
                                    this.value = value;
                                    this.displayName = constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1)
                                                       + ' - ' + constructFieldNameWithOffset(field2.form.tableName, field2.name, offset2)
                                                       + ' ' + operator + ' ' + constructValue(value);
                                },
                                createDateRangeComparison: function(field1, offset1, date1, date2) {
                                    this.type = 'dateRange';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.date1 = date1;
                                    this.date2 = date2;
                                    this.displayName = constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1)
                                                       + ' between ' + constructValue(date1) + ' and ' + constructValue(date2);
                                },
                                createDateValueComparison: function(field1, offset1, operator, value) {
                                    this.type = 'dateValue';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.operator = operator;
                                    this.value = value;
                                    this.displayName = constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1)
                                                       + ' ' + operator + ' ' + constructValue(value);
                                },
                                createEnumRangeComparison: function(field1, values) {
                                    this.type = 'enumRange';
                                    this.field1 = field1;
                                    this.values = values;

                                    this.displayName = constructFieldName(field1.form.tableName, field1.name)
                                                       + ' in (' + values.join(', ') + ')';
                                },
                                createFieldComparison: function(field1, offset1, field2, offset2, operator) {
                                    this.type = 'field';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.operator = operator;
                                    this.field2 = field2;
                                    this.offset2 = (offset2 == null) ? 0 : offset2;
                                    this.displayName = constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1)
                                                       + ' ' + operator + ' '
                                                       + constructFieldNameWithOffset(field2.form.tableName, field2.name, offset2)
                                },
                                createPeriodCondition: function(field1, offset1) {
                                    this.type = 'period';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.displayName = scope.msg('queries.new.label.periodCondition') + ': '
                                                       + constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1);
                                },
                                createCalculationEndDateCondition: function(field1, offset1) {
                                    this.type = 'calculationEndDate';
                                    this.field1 = field1;
                                    this.offset1 = (offset1 == null) ? 0 : offset1;
                                    this.displayName = scope.msg('queries.new.label.calculationEndDateCondition') + ': '
                                                       + constructFieldNameWithOffset(field1.form.tableName, field1.name, offset1);
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

                        var resolveConditionType = function(condition, result) {
                            if (result == null) {
                                return;
                            }

                            switch (result.type) {
                                case 'dateDiff':
                                    condition.createDateDiffComparison(result.field1, result.offset1,
                                        result.field2, result.offset2, result.operator, result.value);
                                    break;
                                case 'dateRange':
                                    condition.createDateRangeComparison(result.field1, result.offset1,
                                        result.date1, result.date2);
                                    break;
                                case 'dateValue':
                                    condition.createDateValueComparison(result.field1, result.offset1,
                                        result.operator, result.value);
                                    break;
                                case 'enumRange':
                                    condition.createEnumRangeComparison(result.field1, result.values);
                                    break;
                                case 'field':
                                    condition.createFieldComparison(result.field1, result.offset1,
                                        result.field2, result.offset2, result.operator);
                                    break;
                                case 'value':
                                    condition.createValueComparison(result.field1, result.operator,
                                        result.value);
                                    break;
                                case 'period':
                                    condition.createPeriodCondition(result.field1, result.offset1);
                                    break;
                                case 'calculationEndDate':
                                    condition.createCalculationEndDateCondition(result.field1, result.offset1);
                                    break;
                                default:
                                    return;
                            }
                        };

                        var constructAndOrSelect = function(div, group, index) {
                            if (group == null || index === null || index <= 0) {
                                return;
                            }

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
                            if (group.operator != 'and') {
                                select.val(group.operator);
                            }
                            div.append(select);
                        }

                        var constructWhereGroup = function(parentGroup, groupOperator) {
                            return {
                                uniqueId: new Date().getTime(),
                                parentGroup: parentGroup,
                                hierarchyLevel: (parentGroup === undefined || parentGroup == null)
                                    ? 0 : parentGroup.hierarchyLevel + 1,
                                groups: [],
                                conditions: [],
                                operator: (groupOperator == null) ? 'and' : groupOperator.toLowerCase(),
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

                                                constructAndOrSelect(div, group, group.groups.length);

                                                div.append(newGroup.constructElement());
                                                group.groups.push(newGroup);
                                        });
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

                                    for (var i = 0; i < this.groups.length; i++) {
                                        var group = this.groups[i].constructElement();
                                        if (i > 0) {
                                            constructAndOrSelect(groupsElement, this.groups[i], i);
                                        }
                                        groupsElement.append(group);
                                    }

                                    for (var i = 0; i < this.conditions.length; i++) {
                                        var condition = this.conditions[i].constructElement();
                                        conditionsElement.append(condition);
                                    }

                                    return whereGroupElement;
                                }
                            };
                        };
                        var parseEntityWhereGroup = function(parentGroup, group) {
                            var whereGroup = constructWhereGroup(parentGroup, group.operator);

                            for (var i = 0; i < group.groups.length; i++) {
                                if (group.groups[i] == null) {
                                    continue;
                                }

                                var childGroup = parseEntityWhereGroup(whereGroup, group.groups[i]);
                                whereGroup.groups.push(childGroup);
                            }
                            for (var i = 0; i < group.conditions.length; i++) {
                                if (group.conditions[i] == null || group.conditions[i].type == null) {
                                    continue;
                                }

                                var condition = constructCondition(whereGroup);
                                resolveConditionType(condition, group.conditions[i]);

                                whereGroup.conditions.push(condition);
                            }

                            return whereGroup;
                        };

                        var whereGroup = (query.whereGroup == null) ? constructWhereGroup(null, null)
                            : parseEntityWhereGroup(null, query.whereGroup);
                        element.append(whereGroup.constructElement());
                        query.whereGroup = whereGroup;
                    });
                }
            }
        });

}());

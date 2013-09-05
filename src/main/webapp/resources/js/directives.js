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
                            var indicatorCategory, tr, spanTrend, indicator,
                                trend, labelClass, calc;
                            element.html('');
                            if (scope.indicatorCategories == undefined) {
                                return;
                            }
                            scope.indicatorCategories.sort(function(a, b) {
                                if (a.name > b.name) {
                                    return 1;
                                } else if (a.name < b.name) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            });
                            for (var c in scope.indicatorCategories) {
                                if (!scope.indicatorCategories.hasOwnProperty(c)) {
                                    continue;
                                }
                                indicatorCategory = scope.indicatorCategories[c];
                                tr = angular.element("<tr/>").addClass("performance-summary-category").append(
                                    angular.element("<td/>").attr("colspan", "2").html(indicatorCategory.name));
                                element.append(tr);
                                indicatorCategory.indicators.sort(function(a, b) {
                                    if (a.indicator.name > b.indicator.name) {
                                        return 1;
                                    } else if (a.indicator.name < b.indicator.name) {
                                        return -1;
                                    } else {
                                        return 0;
                                    }
                                });
                                for (var i in indicatorCategory.indicators) {
                                    if (!indicatorCategory.indicators.hasOwnProperty(i)) {
                                        continue;
                                    }
                                    indicator = indicatorCategory.indicators[i];
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

                        scope.$watch('indicatorCategories', function(oldVal, newVal) {
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
                        if (!attrs.value || !element[0] || element[0].clientWidth <= 0
                                || element[0].clientHeight <= 0) {
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
                                    + '&startDate=' + moment(report.from).format("DD/MM/YYYY")
                                    + '&endDate=' + moment(report.to).format("DD/MM/YYYY")
                                    + "&frequencyId=" + report.frequencyId
                                    + "&areaId=" + report.areaId;

                                $http.get(url).success(function(chart) {
                                    element.html = '';
                                    if (chart.settings.title != undefined) {
                                        delete chart.settings.title;
                                    }
                                    chart.settings.mouse.trackFormatter = function(obj) {
                                        return '('+moment(new Date(parseInt(obj.x))).format("MM/DD/YYYY")+', '+obj.y+')';
                                    };
                                    Flotr.draw(element[0], chart.data, chart.settings);
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
                    query: '=',
                    dialog: '='
                },
                link: function(scope, element, attrs) {
                    $timeout(function() {
                        var query = scope.query;

                        var constructFieldName = function(tableName, fieldName) {
                            return tableName + '.' + fieldName;
                        };
                        var constructFieldNameWithOffset = function(tableName, fieldName, fieldOffset) {
                            return tableName + '.' + fieldName + ' ' + ((fieldOffset < 0) ? '-' : '+') + ' ' + fieldOffset;
                        };

                        var constructCondition = function(parentGroup) {
                            return {
                                uniqueId: new Date().getTime(),
                                parentGroup: parentGroup,
                                createValueComparison: function(tableName, fieldName, operator, value) {
                                    this.type = 'value';
                                    this.tableName1 = tableName;
                                    this.fieldName1 = fieldName;
                                    this.operator = operator;
                                    this.value = value;
                                    this.displayName = constructFieldName(tableName, fieldName) + ' ' + operator
                                                       + ' ' + value;
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

                        var constructFieldComparison = function(tableName1, fieldName1, fieldOffset1,
                                                                tableName2, fieldName2, fieldOffset2,
                                                                operator) {
                            return {
                                uniqueId: new Date().getTime(),
                                type: 'field',
                                tableName1: tableName1,
                                fieldName1: fieldName1,
                                fieldOffset1: fieldOffset1,
                                operator: operator,
                                tableName2: tableName2,
                                fieldName2: fieldName2,
                                fieldOffset2: fieldOffset2,
                                displayName: constructFieldNameWithOffset(tableName1, fieldName1, fieldOffset1)
                                             + ' ' + operator + ' '
                                             + constructFieldNameWithOffset(tableName2, fieldName2, fieldOffset2)
                            };
                        };
                        var constructDateDiffComparison = function(tableName1, fieldName1, fieldOffset1,
                                                                   tableName2, fieldName2, fieldOffset2,
                                                                   operator, value) {
                            return {
                                uniqueId: new Date().getTime(),
                                type: 'dateDiff',
                                tableName1: tableName1,
                                fieldName1: fieldName1,
                                fieldOffset1: fieldOffset1,
                                tableName2: tableName2,
                                fieldName2: fieldName2,
                                fieldOffset2: fieldOffset2,
                                operator: operator,
                                value: value,
                                displayName: constructFieldNameWithOffset(tableName1, fieldName1, fieldOffset1)
                                             + ' - ' + constructFieldNameWithOffset(tableName2, fieldName2, fieldOffset2)
                                             + ' ' + operator + ' ' + value
                            };
                        };
                        var constructDateRangeComparison = function(tableName1, fieldName1, fieldOffset1,
                                                                    date1, date2) {
                            return {
                                uniqueId: new Date().getTime(),
                                type: 'dateRange',
                                tableName1: tableName1,
                                fieldName1: fieldName1,
                                fieldOffset1: fieldOffset1,
                                date1: date1,
                                date2: date2,
                                displayName: constructFieldNameWithOffset(tableName1, fieldName1, fieldOffset1)
                                             + ' between ' + date1 + ' and ' + date2
                            };
                        };
                        var constructDateValueComparison = function(tableName1, fieldName1, fieldOffset1,
                                                                    operator, value) {
                            return {
                                uniqueId: new Date().getTime(),
                                type: 'dateValue',
                                tableName1: tableName1,
                                fieldName1: fieldName1,
                                fieldOffset1: fieldOffset1,
                                operator: operator,
                                value: value,
                                displayName: constructFieldNameWithOffset(tableName1, fieldName1, fieldOffset1)
                                             + ' ' + operator + ' ' + value
                            };
                        };
                        var constructEnumRangeComparison = function(tableName1, fieldName1, values) {
                            return {
                                uniqueId: new Date().getTime(),
                                type: 'enumRange',
                                tableName1: tableName1,
                                fieldName1: fieldName1,
                                values: values,
                                displayName: constructFieldName(tableName1, fieldName1)
                                             + ' in (' + values.join(', ') + ')'
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
                                    var constructAddConditionButton = function(div, group) {
                                        return angular.element('<button />').addClass('btn btn-mini')
                                            .append(angular.element('<i />').addClass('icon-plus-sign'))
                                            .click(function() {
                                                $q.when(dialog()).then(function() {
                                                    dialog.open().then(function(result) {
                                                        /*if (result == 'cancel') {
                                                        return;
                                                        }*/

                                                        console.log(result);
                                                        var newCondition = constructCondition(group);
                                                        newCondition.createValueComparison('table', 'field', '>=', '2');
                                                        div.append(newCondition.constructElement());
                                                        group.conditions.push(newCondition);
                                                    });
                                                });
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

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
                                    angular.element("<td/>").attr("colspan", "3").html(indicatorCategory.name));
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
                                    if(indicator.indicator.isComputed) {
                                        calc = scope.msg('dashboards.trends.done');
                                    } else {
                                        calc = scope.msg('dashboards.trends.progress');
                                    }
                                    tr = angular.element("<tr/>").append(
                                        angular.element("<td/>").html(indicator.indicator.name)
                                    ).append(
                                        angular.element("<td/>").html(calc)
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

}());

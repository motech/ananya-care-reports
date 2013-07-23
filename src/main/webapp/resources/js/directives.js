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
                        title: scope.msg('users.roles.list.popoverTitle') + ":",
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
                                trend, labelClass;
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
                                        trend = scope.msg('trends.trend.neutral');
                                        labelClass = "label-info";
                                        break;
                                    case 1:
                                        trend = scope.msg('trends.trend.positive');
                                        labelClass = "label-success";
                                        break;
                                    case -1:
                                        trend = scope.msg('trends.trend.negative');
                                        labelClass = "label-important";
                                        break;
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

}());

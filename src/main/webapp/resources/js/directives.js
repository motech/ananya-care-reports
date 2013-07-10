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

    widgetModule.directive('flotrChart', function factory($http) {
        return function postLink(scope, iElement, iAttrs) {
            $(iElement).addClass('chart-container');
            $http.get('api/chart?chartType=' + iAttrs.chartType  + '&indicatorId=' + iAttrs.indicatorId).success(function(chart) {
                var graph, title, chart, wrapper, titleElement;
                if (chart.settings.title != undefined) {
                    title = chart.settings.title;
                    delete chart.settings.title;
                }
                graph = Flotr.draw(iElement[0], chart.data, chart.settings);
                if (title != undefined) {
                    wrapper = $(angular.element("<div/>"));
                    wrapper.addClass("chart-container-wrapper");
                    chart = $(iElement[0]);
                    chart.replaceWith(wrapper);
                    chart.appendTo(wrapper);
                    titleElement = $(angular.element("<p/>"));
                    titleElement.html(title);
                    titleElement.addClass("title");
                    wrapper.append(titleElement);
                }
            });
        }
    });

}());

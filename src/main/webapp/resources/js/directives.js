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
}());

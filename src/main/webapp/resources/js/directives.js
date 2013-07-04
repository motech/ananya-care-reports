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
                    title: "User roles:",
                    content: function () {
                        var html = angular.element('<ul />');
                        for (var i in scope.user.roles) {
                            if (scope.user.roles.hasOwnProperty(i)) {
                                var li = angular.element('<li />');
                                li.text(scope.user.roles[i].name);
                                html.append(li);
                            }
                        }

                        console.log(scope.user);

                        return html;
                    }
                });
           }
       };
    });
}());

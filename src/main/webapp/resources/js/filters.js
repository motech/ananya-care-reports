(function () {
    'use strict';

    angular.module('care').filter('withTrend', function() {
        return function(input) {
            var out = [];
            for (var i = 0; i < input.length; i++) {
                if (input[i].trend != undefined) {
                    out.push(input[i]);
                }
            }
            return out;
        }
   });

}());

var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

care.controller('dashboardController', function($scope, $http) {
    $scope.title = $scope.msg('dashboard.title');

    $scope.compareDashboardPositions = function(dashboardA, dashboardB) {
        return parseInt(dashboardA.tabPosition) - parseInt(dashboardB.tabPosition);
    };

    $scope.fetchAreas = function() {
        $http.get('api/dashboards/user-areas')
          .success(function(areas) {
               areas.sort(function(a, b) {
                   return a.name.localeCompare(b.name);
               });
               var arr = Array(),
                   pushAllChildrenOf = function(arr, area) {
                       for (var index=0; index<areas.length; index+=1) {
                           if (areas[index].parentAreaId == area.id) {
                               arr.push(areas[index]);
                               pushAllChildrenOf(arr, areas[index]);
                           }
                       }
                   };

               for (var index=0; index<areas.length; index+=1) {
                   if (areas[index].levelHierarchyDepth == 0) {
                       arr.push(areas[index]);
                       pushAllChildrenOf(arr, areas[index]);
                   }
               }
               $scope.areas = arr;
               $scope.area = arr[0];
          });
    };

    $scope.fetchDashboards = function() {
        $http.get('api/dashboards').success(function(dashboards) {
            dashboards.sort($scope.compareDashboardPositions);
            $scope.dashboards = dashboards;
        });
    };

    $scope.loadChart = function(container, indicatorId, chartType, areaId) {
        var url = 'api/chart?chartType=' + chartType  + '&indicatorId=' + indicatorId;
        if (areaId != undefined) {
            url += "&areaId=" + areaId;
        }
        $http.get(url).success(function(chart) {
            var graph, title, chart, wrapper, titleElement;
            if (chart.settings.title != undefined) {
                title = chart.settings.title;
                delete chart.settings.title;
            }
            graph = Flotr.draw(container[0], chart.data, chart.settings);
            if (title != undefined) {
                wrapper = $(angular.element("<div/>"));
                wrapper.addClass("chart-container-wrapper");
                chart = $(container);
                chart.replaceWith(wrapper);
                chart.appendTo(wrapper);
                titleElement = $(angular.element("<p/>"));
                titleElement.html(title);
                titleElement.addClass("title");
                wrapper.append(titleElement);
            }
        });
    };

    $scope.drawCharts = function() {
        var colCount = 0,
            div = $('#tab' + $scope.dashboard.id),
            table = angular.element('<table/>'),
            tr = angular.element('<tr/>');
        div.html('');
        div.append(table);
        table.append(tr);
        if ($scope.dashboard.indicatorCategory != null) {
            for (var i in $scope.dashboard.indicatorCategory.indicators) {
                var indicator = $scope.dashboard.indicatorCategory.indicators[i];
                if (indicator.reports == undefined) {
                    continue;
                }
                for (var r in indicator.reports) {
                    if (!indicator.reports.hasOwnProperty(r)) {
                        continue;
                    }
                    var report = indicator.reports[r], td, div;
                    if (!report.reportType.name.toLowerCase().endsWith('chart')) {
                        continue;
                    }
                    td = angular.element('<td/>');
                    div = angular.element('<div/>');
                     if (colCount == 3) {
                         tr = angular.element('<tr/>');
                         table.append(tr);
                         colCount=0;
                     }
                     tr.append(td);
                     $(div).addClass('chart-container');
                     td.append(div);
                     $scope.loadChart(div, indicator.id, report.reportType.name.toLowerCase(), $scope.areaId);
                     colCount++;
                }
            }
        }

    };

    $scope.tabChanged = function(dashboard) {
        $scope.previousAreaId = $scope.areaId;
        $scope.dashboard = dashboard;
        if (dashboard.name === "Performance summary") {
            //fetch trends...
        } else if (dashboard.name === "Map report") {

        } else {
            $scope.drawCharts();
        }

        $scope.$watch('areaId', function(newValue, oldValue) {
            if ($scope.previousAreaId != $scope.areaId) {
                $scope.drawCharts();
                $scope.previousAreaId = $scope.areaId;
            }
        }, true);
    };

    $scope.fetchDashboards();
    $scope.fetchAreas();

    $scope.indicatorCategories = [
        {name: "Category1",
         indicators: [{
            name: "indicator1",
            trend: -1
         }, {
            name: "indicator2",
            trend: 0
         }]},
         {name: "Category2",
         indicators: [{
            name: "indicator3",
            trend: 1
         }, {
            name: "indicator4",
            trend: 0
         }, {
            name: "indicator5",
            trend: 1
         }]}
        ];
});

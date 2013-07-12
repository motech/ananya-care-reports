var care = angular.module('care');

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
        console.log(container);
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
            table = $('#tab' + $scope.dashboard.id),
            tr = angular.element('<tr/>');
        table.html('');
        table.append(tr);
        if ($scope.dashboard.indicatorCategory != null) {
            for (indicatorId in $scope.dashboard.indicatorCategory.indicatorsIds) {
                if ($scope.dashboard.indicatorCategory.indicatorsIds.hasOwnProperty(indicatorId)) {
                    var td = angular.element('<td/>'),
                        div = angular.element('<div/>');
                    if (colCount == 3) {
                        tr = angular.element('<tr/>');
                        table.append(tr);
                        colCount=0;
                    }
                    tr.append(td);
                    $(div).addClass('chart-container');
                    td.append(div);
                    $scope.loadChart(div, $scope.dashboard.indicatorCategory.indicatorsIds[indicatorId], "pie", $scope.areaId);
                    colCount++;
                }
            }
        }

    };

    $scope.tabChanged = function(dashboard) {
        $scope.previousAreaId = $scope.areaId;
        $scope.dashboard = dashboard;
        $scope.drawCharts();

        $scope.$watch('areaId', function(newValue, oldValue) {
            if ($scope.previousAreaId != $scope.areaId) {
                $scope.drawCharts();
                $scope.previousAreaId = $scope.areaId;
            }
        }, true);

    };

    $scope.fetchDashboards();
    $scope.fetchAreas();

});

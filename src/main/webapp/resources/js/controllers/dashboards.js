var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function sortByDateComparisonFunction(a, b) {
    return parseInt(a) - parseInt(b);
};

care.controller('dashboardController', function($rootScope, $scope, $http, $location, $dialog, $simplifiedHttpService, $compile) {
    $scope.title = $scope.msg('dashboard.title');

    $scope.charts = {};

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
            $("#dashboards-tabs ul").sortable({update: function(event, ui) {

                var tabsPositions = [],
                    tabs = $("#dashboards-tabs").find("li"),
                    len = tabs.length;

                tabs.each(function(index) {
                    tabsPositions.push({
                        position: index,
                        name: $(this).attr("heading")
                    });
                    if (index == len-1) {
                        $http.post('api/dashboards/save-positions', tabsPositions);
                    }
                });
            }});
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
                titleElement = $(angular.element("<p/>"));
                titleElement.html(title);
                titleElement.addClass("title");
                var p = $(container).parent().find('p');
                if ($(p).length) {
                    p.replaceWith(titleElement);
                } else {
                    $(container).parent().append(titleElement);
                }
            }
        });
    };

    $scope.fetchChartData = function(element) {
        $rootScope.indicatorId = $(element).parents('td').attr('data-indicator-id');
        $rootScope.areaId = $scope.areaId;

        $simplifiedHttpService.get($scope, 'resources/partials/dashboards/chartDetails.html',
                'charts.details.cannotLoadChartDetails', function(htmlData) {
            var html = $compile(htmlData)($scope);
            $(element).html(html);
        });
    };

    $scope.toggleChartDisplay = function(element) {
        var parent = $(element).parents('td');
        var isChartType = (parent.attr('data-display-type') === 'chart');
        var indicatorId = parent.attr('data-indicator-id');
        var chartType = parent.attr('data-chart-type');

        if (isChartType) {
            parent.attr('data-display-type', 'table');
            $scope.fetchChartData(element);
        } else {
            $(parent).attr('data-display-type', 'chart');
            $scope.loadChart(element, indicatorId, chartType, $scope.areaId);
        }
    };

    $scope.drawCharts = function() {
        var colCount = 0,
            div = $('#tab' + $scope.dashboard.id),
            table = angular.element('<table/>').addClass('row-fluid'),
            tr = angular.element('<tr/>');
        div.html('');
        div.append(table);
        table.append(tr);
        var index = 0;
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
                    td = angular.element('<td />')
                            .addClass('chart-td')
                            .attr('data-indicator-id', indicator.id)
                            .attr('data-display-type', 'chart')
                            .attr('data-chart-type', report.reportType.name.toLowerCase())
                            .click(function() {
                        $scope.toggleChartDisplay($(this).find('div.chart-container'));
                    });
                    var wrapper = angular.element('<div/>').addClass('chart-container-wrapper');
                    div = angular.element('<div/>');
                    if (colCount == 3) {
                        tr = angular.element('<tr/>');
                        table.append(tr);
                        colCount=0;
                    }
                    tr.append(td);
                    $(div).addClass('chart-container');
                    wrapper.append(div);
                    td.append(wrapper);
                    $scope.loadChart(div, indicator.id, report.reportType.name.toLowerCase(), $scope.areaId);
                    colCount++;
                }
            }
        }

    };

    $scope.tabChanged = function(dashboard) {
        $scope.charts = [];
        $scope.previousAreaId = $scope.areaId;
        $scope.dashboard = dashboard;
        if (dashboard.name === "Performance summary") {
            $scope.fetchTrends();
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

    $scope.startDate = moment().subtract('months', 1).format('DD-MM-YYYY');
    $scope.endDate = moment().format('DD-MM-YYYY');

    $scope.trendPerCategory = {};

    $scope.fetchTrends = function() {
        var startDate = $("#start-date input").val(),
            endDate = $("#end-date input").val();
        if (startDate == undefined) {
            startDate = $scope.startDate;
        }
        if (endDate == undefined) {
            endDate = $scope.endDate;
        }
        $http.get('api/trend?startDate=' + startDate + '&endDate=' + endDate)
                .success(function(indicatorCategories) {
            $scope.indicatorCategories = indicatorCategories;

            for (var c = 0; c < $scope.indicatorCategories.length; c++) {
                if (!$scope.indicatorCategories.hasOwnProperty(c)) {
                    continue;
                }

                var category = $scope.indicatorCategories[c];
                var key = 'category_' + category.name;
                $('tabset').find('li[heading="' + category.name + '"]').find('a').addClass('alert alert-info tab-trend');

                for (var i = 0; i < category.indicators.length; i++) {
                    if ($scope.trendPerCategory[key] === undefined) {
                        $scope.trendPerCategory[key] = {
                            negative: 0,
                            positive: 0
                        };
                    }

                    var trend = category.indicators[i].trend;
                    if (trend < 0) {
                        $scope.trendPerCategory[key].negative++;
                    } else if (trend > 0) {
                        $scope.trendPerCategory[key].positive++;
                    }
                }

                var trend = $scope.trendPerCategory[key];
                if (trend !== undefined) {
                    if (trend.positive > trend.negative) {
                        $('tabset').find('li[heading="' + category.name + '"]').find('a')
                            .removeClass('alert-info').addClass('alert-success');
                    } else if (trend.positive < trend.negative) {
                        $('tabset').find('li[heading="' + category.name + '"]').find('a')
                            .removeClass('alert-info').addClass('alert-danger');
                    }
                }
            }
        });
    };

    $scope.analyze = function() {
        $scope.fetchTrends();
    }

    $scope.fetchTrends();
});

care.controller('chartDetailsController', function($rootScope, $scope, $http, $simplifiedHttpService, $location) {
    $scope.title = $scope.msg('charts.details.title');

    $scope.indicator = { name: null };
    $scope.chartData = [];

    var indicatorId = $rootScope.indicatorId;
    var areaId = $rootScope.areaId;
    delete $rootScope.indicatorId;
    delete $rootScope.areaId;

    var url = 'api/chart/data/?indicatorId=' + indicatorId;
    if (!isNaN(areaId) && isFinite(areaId)) {
        url += '&areaId=' + areaId;
    }

    $scope.fetchIndicator = function() {
        $simplifiedHttpService.get($scope, 'api/indicator/' + indicatorId,
                'charts.details.cannotLoadIndicator', function(indicator) {
            $scope.indicator = indicator;
        });
    };
    $scope.fetchIndicator();

    $scope.fetchChartData = function() {
        $simplifiedHttpService.get($scope, url, 'charts.details.cannotLoadChartDetails', function(chartData) {
            chartData.sort(sortByDateComparisonFunction);
            $scope.chartData = chartData;
        });
    };
    $scope.fetchChartData();

    $scope.formatDate = function(date) {
        return moment(date).format("LLL");
    };

    $scope.goBack = function() {
        $rootScope.areaId = areaId;
        $rootScope.dashboard = dashboard;

        $location.path("/");
    };
});

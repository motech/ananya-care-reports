var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function sortByDateComparisonFunction(a, b) {
    return parseInt(a) - parseInt(b);
};

care.controller('dashboardController', function($rootScope, $scope, $http, $location, $dialog, $simplifiedHttpService, $compile) {
    $scope.title = $scope.msg('dashboards.title');
    $scope.areaId = 1;

    $scope.startDate = moment().subtract('months', 1).format('DD-MM-YYYY');
    $scope.endDate = moment().format('DD-MM-YYYY');

    $scope.indicatorCategories = [];
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
            if (Object.keys($scope.dashboards).length > 0) {
                $scope.tabChanged($scope.dashboards[0]);
            }
        });
    };

    $scope.fetchChartData = function(element) {
        $rootScope.indicatorId = $(element).parents('td').attr('data-indicator-id');
        $rootScope.areaId = $scope.areaId;

        $simplifiedHttpService.get($scope, 'resources/partials/dashboards/chartDetails.html',
                'dashboards.charts.error.cannotLoadChartDetails', function(htmlData) {
            var html = $compile(htmlData)($scope);
            $(element).html(html);
        });
    };

    $scope.reportRows = [];
    $scope.fetchReportRows = function() {
        $scope.reportRows = [];
        if ($scope.dashboard === undefined
            || $scope.dashboard.indicatorCategory == undefined) {
            return;
        }

        for (var i in $scope.dashboard.indicatorCategory.indicators) {
            var indicator = $scope.dashboard.indicatorCategory.indicators[i];
            if (indicator.reports === undefined) {
                continue;
            }

            indicator.reports.sort(function(a, b) { return parseInt(a.id) - parseInt(b.id); });

            for (var r = 0; r < indicator.reports.length; r += 3) {
                if (!indicator.reports.hasOwnProperty(r)) {
                    continue;
                }

                var reportRow = [];
                for (var q = 0; q < 3; q++) {
                    var report = (indicator.reports[r + q]) ? indicator.reports[r + q] : null;
                    if (report != null) {
                        if (report.reportType.name.toLowerCase().endsWith('chart')) {
                            report.indicatorId = indicator.id;
                            report.needsRefreshing = true;
                            report.indicatorName = indicator.name;
                            report.rowIndex = $scope.reportRows.length;
                            report.index = reportRow.length;
                            report.displayType = 'chart';
                        } else {
                            report = null;
                        }
                    }
                    reportRow.push(report);
                }
                $scope.reportRows.push(reportRow);
            }
        }
    };

    $scope.tabChanged = function(dashboard) {
        $scope.reportRows = [];
        $scope.charts = [];
        $("#mapReport1").html('');
        $("#mapReport2").html('');
        $scope.previousAreaId = $scope.areaId;
        $scope.dashboard = dashboard;
        if (dashboard.name == "Performance summary") {
            $scope.fetchTrends();
        } else if (dashboard.name == "Map report") {
            for (var i in $scope.maps) {
                if ($scope.maps.hasOwnProperty(i)) {
                    $scope.fetchMapReport($scope.maps[i]);
                }
            }
        } else {
            $scope.fetchReportRows();
        }

        $scope.$watch('areaId', function(newValue, oldValue) {
            if ($scope.previousAreaId != $scope.areaId) {
                $scope.fetchTrends();
                $scope.fetchReportRows();
                $scope.previousAreaId = $scope.areaId;
                for (var i in $scope.maps) {
                    if ($scope.maps.hasOwnProperty(i)) {
                        $scope.fetchMapReport($scope.maps[i]);
                    }
                }
            }
        }, true);
    };

    $scope.fetchDashboards();
    $scope.fetchAreas();

    $scope.trendPerCategory = {};

    $scope.fetchTrends = function() {
        var startDate = $("#start-date input").val(),
            endDate = $("#end-date input").val(),
            url;
        if (startDate == undefined) {
            startDate = $scope.startDate;
        }
        if (endDate == undefined) {
            endDate = $scope.endDate;
        }
        url = 'api/trend?startDate=' + startDate + '&endDate=' + endDate;
        if ($scope.areaId != undefined) {
            url += '&areaId=' + $scope.areaId;
        }
        $http.get(url)
                .success(function(indicatorCategories) {
            $scope.indicatorCategories = indicatorCategories;

            for (var c = 0; c < $scope.indicatorCategories.length; c++) {
                if (!$scope.indicatorCategories.hasOwnProperty(c)) {
                    continue;
                }

                var category = $scope.indicatorCategories[c];
                var key = 'category_' + category.name;
                $('.tabbable').find('a[data-tab-caption="' + category.name + '"]').addClass('alert alert-info tab-trend');

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
                        $('.tabbable').find('a[data-tab-caption="' + category.name + '"]')
                            .removeClass('alert-info').addClass('alert-success');
                    } else if (trend.positive < trend.negative) {
                        $('.tabbable').find('a[data-tab-caption="' + category.name + '"]')
                            .removeClass('alert-info').addClass('alert-danger');
                    }
                }
            }
        });
    };

    $scope.maps = [];
    for (i=0; i<2; i+=1) {
        $scope.maps[i] = {
            startDate: moment().subtract('months', 1).format('DD-MM-YYYY'),
            endDate: moment().format('DD-MM-YYYY'),
            selectedIndicatorCategoryId: 1,
            selectedCategoryIndicators: [],
            selectedIndicatorId: 1,
            containerId: "mapReport" + i
        };
    }

    $scope.analyzeMap = function(map) {
      $scope.fetchMapReport(map);
    }

    $scope.analyze = function() {
        $scope.fetchTrends();
    }

    $scope.fetchCategoryIndicators = function(map) {
        $http.get('api/indicator/filter/' + map.selectedIndicatorCategoryId).success(function(indicators) {
            map.selectedCategoryIndicators = indicators;
        });
    };

    $scope.fetchCategories = function() {
        $http.get('api/indicator/category').success(function(indicatorCategories) {
            $scope.mapCategories = indicatorCategories;
            $scope.indicatorCategory1 = $scope.indicatorCategory2 = indicatorCategories[0];
            $scope.$watch('maps[0].selectedIndicatorCategoryId', function(newValue, oldValue) {
                $scope.fetchCategoryIndicators($scope.maps[0]);
            }, true);
            $scope.$watch('maps[1].selectedIndicatorCategoryId', function(newValue, oldValue) {
                $scope.fetchCategoryIndicators($scope.maps[1]);
            }, true);
        });
    };

    $scope.fetchCategories();

    $scope.fetchMapReport = function(map) {
        var url = 'api/map-report?indicatorId=' + map.selectedIndicatorId + '&startDate=' + map.startDate + '&endDate=' + map.endDate;
        if ($scope.areaId != undefined) {
            url += '&areaId=' + $scope.areaId;
        }
        $http.get(url).success(function(data) {
            for (var i in data) {
                if (data.hasOwnProperty(i)) {
                    data[i] = data[i].toString();
                }
            }
            $('#' + map.containerId).html('').vectorMap({
                map: 'bihar',
                onRegionClick: function(event, code) {
                    //change area i
                    $('.jvectormap-label').remove();
                    $scope.fetchMapReport();
                },
                series: {
                    regions: [{
                        values: data,
                        scale: {
                            "1": '#A8F022',
                            "-1": '#F1A219',
                            "0": '#82AAFF'
                        }
                    }]
                },
                regionStyle: {
                  initial: {
                    fill: 'grey',
                    "fill-opacity": 1,
                    stroke: '#444444',
                    "stroke-width": 0.5,
                    "stroke-opacity": 1
                  },
                  hover: {
                    "fill-opacity": 1,
                    "stroke-width": 2,
                    stroke: "#FFFFFF",
                    "stroke-opacity": 1
                  }
                },
                onRegionLabelShow: function(e, el, code) {
                    if (data[code] == undefined) {
                        $(el).html($scope.msg('dashboards.map.noData', code));
                    }
                    var ads = data[code];
                    $(el).removeClass("positive").removeClass("negative").removeClass("neutral").addClass(data[code]);
                    el.html('<span class="name">' + el.html() + '</span>');
                    if (data[code] == 1) {
                        el.html(el.html() + ' <img src="resources/images/trend_positive.png" />');
                    } else if (data[code] == -1) {
                        el.html(el.html() + ' <img src="resources/images/trend_negative.png" />');
                    } else if (data[code] != undefined) {
                        el.html(el.html() + ' <img src="resources/images/trend_neutral.png" />');
                    }
                }
            });
        });
    };

    $scope.indicator = { name: null };
    $scope.chartData = [];

    $scope.toggleChartDisplay = function(report) {
        if (!report) {
            return;
        }

        if (report.displayType == 'chart') {
            report.displayType = 'table';
            $scope.loadChartDetails(report);
        } else if (report.displayType == 'table') {
            report.displayType = 'chart';
        }
    };

    $scope.loadChartDetails = function(report) {
        $scope.fetchChartData(report, $scope.areaId);
    };

    $scope.fetchChartData = function(report, areaId) {
        var indicatorId = report.indicatorId;
        var url = 'api/chart/data/?indicatorId=' + indicatorId;

        if (!isNaN(areaId) && isFinite(areaId)) {
            url += '&areaId=' + areaId;
        }

        $simplifiedHttpService.get($scope, url, 'dashboards.charts.error.cannotLoadChartDetails', function(chartData) {
            chartData.sort(sortByDateComparisonFunction);
           report.chart = chartData;
        });
    };

    $scope.formatDate = function(date) {
        return moment(date).format("L HH:mm");
    };
});

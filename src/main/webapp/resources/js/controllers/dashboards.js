var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function sortByDateComparisonFunction(a, b) {
    return parseInt(a) - parseInt(b);
};

care.controller('dashboardController', function($rootScope, $scope, $http, $location, $dialog, $simplifiedHttpService, $compile) {
    $scope.title = $scope.msg('dashboards.title');

    $scope.startDate = moment().subtract('months', 1).format('L');
    $scope.endDate = moment().format('L');

    $scope.indicatorCategories = [];
    $scope.charts = {};
    $scope.frequencies = [];

    $scope.compareDashboardPositions = function(dashboardA, dashboardB) {
        return parseInt(dashboardA.tabPosition) - parseInt(dashboardB.tabPosition);
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

        $simplifiedHttpService.get($scope, 'resources/partials/dashboards/chartDetails.html',
                'dashboards.charts.error.cannotLoadChartDetails', function(htmlData) {
            var html = $compile(htmlData)($scope);
            $(element).html(html);
        });
    };

    $scope.fetchFrequencies = function() {
        $http.get('/api/indicator/calculator/frequencies').success(function(frequencies) {
            $scope.frequencies = frequencies;
        });
    };
    $scope.fetchFrequencies();
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
                            report.indicatorAreaId = indicator.area.id;
                            report.indicatorName = indicator.name;
                            report.rowIndex = $scope.reportRows.length;
                            report.index = reportRow.length;
                            report.displayType = 'chart';
                            report.from = moment().subtract('months', 1).format('L');
                            report.to = moment().format('L');
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

    $scope.reportFromDateChanged = function(report) {
        report.from = moment(report.from);
        report.to = moment(report.to);

        if (moment(report.to).diff(report.from, 'days') <= 0) {
            report.from = moment(report.to).subtract('days', 1).format('L');
        }
    };

    $scope.reportToDateChanged = function(report) {
        report.from = moment(report.from);
        report.to = moment(report.to);

        if (moment(report.to).diff(report.from, 'days') <= 0) {
            report.to = moment(report.from).add('days', 1).format('L');
        }
    };

    $scope.fetchAreas = function(report) {
        $http.get('api/dashboards/user-areas/' + report.indicatorAreaId)
          .success(function(areas) {
               areas.sort(function(a, b) {
                   return a.levelHierarchyDepth - b.levelHierarchyDepth || a.name.localeCompare(b.name);
               });
               var arr = Array();

               for (var index=0; index<areas.length; index++) {
                   arr.push(areas[index]);
               }
               report.areas = arr;
               report.areaId = areas[0].id;
               report.minDepth = areas[0].levelHierarchyDepth;
          });
    };

    $scope.tabChanged = function(dashboard) {
        $scope.reportRows = [];
        $scope.charts = [];
        $("#mapReport1").html('');
        $("#mapReport2").html('');
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
            for (var i = 0; i < $scope.reportRows.length; i++) {
                $scope.fetchAreas($scope.reportRows[i][0]);
            }
        }
    };
    $scope.fetchDashboards();

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
            startDate: moment().subtract('months', 1).format('L'),
            endDate: moment().format('L'),
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

    $scope.exportToCsv = function(report) {
        var indicatorId = report.indicatorId;
        var url = 'api/chart/data/export/?indicatorId=' + indicatorId
            + '&startDate=' + report.from
            + '&endDate=' + report.to
            + '&frequencyId=' + report.frequencyId;

        if (!isNaN(report.areaId) && isFinite(report.areaId)) {
            url += '&areaId=' + report.areaId;
        }
        window.open(url);
    }

    $scope.toggleChartDisplay = function(report) {
        if (!report) {
            return;
        }

        if (report.displayType == 'chart') {
            report.displayType = 'table';
            $scope.loadChartDetails(report);
        } else if (report.displayType == 'table') {
            report.displayType = 'chart';
            $scope.loadChartDetails(report);
        }
    };

    $scope.loadChartDetails = function(report) {
        $scope.fetchChartData(report);
    };

    $scope.fetchChartData = function(report) {
        var indicatorId = report.indicatorId;
        var url = 'api/chart/data/?indicatorId=' + indicatorId
            + '&startDate=' + report.from
            + '&endDate=' + report.to
            + '&frequencyId=' + report.frequencyId;

        if (!isNaN(report.areaId) && isFinite(report.areaId)) {
            url += '&areaId=' + report.areaId;
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

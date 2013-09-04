var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function sortByDateComparisonFunction(a, b) {
    return parseInt(a) - parseInt(b);
};

care.controller('dashboardController', function($rootScope, $scope, $http, $location, $dialog, $simplifiedHttpService, $compile) {
    $scope.title = $scope.msg('dashboards.title');

    $scope.startDate = moment().subtract('months', 1).format("L");
    $scope.endDate = moment().format("L");

    $scope.indicatorCategories = [];
    $scope.charts = {};
    $scope.frequencies = [];

    $scope.currentReportsPage = 0;
    $scope.reportsPerPage = 3;
    $scope.loading = true;

    $scope.compareDashboardPositions = function(dashboardA, dashboardB) {
        return parseInt(dashboardA.tabPosition) - parseInt(dashboardB.tabPosition);
    };

    $scope.fetchDashboards = function() {
        $http.get('api/dashboards').success(function(dashboards) {
            dashboards.sort($scope.compareDashboardPositions);
            $scope.dashboards = dashboards;
            if (Object.keys($scope.dashboards).length > 0) {
                 $scope.fetchTrends();
                 $scope.getDefaultDashboard();
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
        $http.get('api/indicator/calculator/frequencies').success(function(frequencies) {
            $scope.frequencies = frequencies;
            $scope.frequencyId = frequencies[0].id;
        });
    };
    $scope.fetchFrequencies();
    $scope.reportRows = [];
    $scope.allReportsRows = [];
    $scope.reportValues = [];

    $scope.prepareAllReportRows = function() {
        $scope.allReportsRows = [];
        if ($scope.dashboard == undefined || $scope.dashboard.indicatorCategory == undefined) {
            return;
        }
        for (var i = 0; i < $scope.dashboard.indicatorCategory.indicators.length; i++) {
            var indicator = $scope.dashboard.indicatorCategory.indicators[i];
            if (indicator.reports == undefined) {
                continue;
            }
            indicator.reports.sort(function(a, b) { return parseInt(a.id) - parseInt(b.id); });
            var reportRow = [];
            for (var r = 0; r < indicator.reports.length; r+=1) {
                if (!indicator.reports.hasOwnProperty(r)) {
                    continue;
                }
                var indicatorReport = indicator.reports[r];
                if (indicatorReport != null && indicatorReport.reportType.name.toLowerCase().endsWith('chart')) {
                    var report = indicatorReport;
                    report.indicatorId = indicator.id;
                    report.needsRefreshing = true;
                    report.computing = indicator.isComputed;
                    report.indicatorAreaId = indicator.area.id;
                    report.indicatorName = indicator.name;
                    report.frequencyId = indicator.defaultFrequency.id;
                    report.additive = indicator.isAdditive;
                    report.rowIndex = $scope.reportRows.length;
                    report.index = reportRow.length;
                    report.displayType = 'chart';
                    report.from = moment().subtract('months', 1).format("L");
                    report.to = moment().format("L");
                    report.canExportCaseListReport = true;
                    reportRow.push(report);
                }
            }
            $scope.allReportsRows.push(reportRow);
        }
    };

    $scope.prepareCurrentPageReportsRows = function() {
        var newReports = [];
        $scope.reportRows = [];
        var startIndex = $scope.currentReportsPage * $scope.reportsPerPage;
        for (var i = startIndex; i < $scope.allReportsRows.length && i < startIndex + $scope.reportsPerPage; i++) {
            newReports.push($scope.allReportsRows[i]);
        }
        $scope.reportRows = newReports;
    };

    $scope.getPageCount = function() {
        if ($scope.dashboard == undefined || $scope.dashboard.indicatorCategory == undefined) {
            return 0;
        }
        return Math.ceil($scope.dashboard.indicatorCategory.indicators.length/$scope.reportsPerPage);
    };

    $scope.getPages = function() {
        return new Array($scope.getPageCount());
    }

    $scope.reportFromDateChanged = function(report) {
        report.from = moment(report.from);
        report.to = moment(report.to);

        if (moment(report.to).diff(report.from, 'days') < 0) {
            report.from = moment(report.to).format("L");
        }
    };

    $scope.reportToDateChanged = function(report) {
        report.from = moment(report.from);
        report.to = moment(report.to);

        if (moment(report.to).diff(report.from, 'days') < 0) {
            report.to = moment(report.from).format("L");
        }
    };

    $scope.setCurrentPage = function(page) {
        $scope.currentReportsPage = page;
        $scope.showReports();
    };

    $scope.setPreviousPage = function() {
        $scope.currentReportsPage--;
        if ($scope.currentReportsPage < 0) {
            $scope.currentReportsPage = $scope.getPageCount()-1;
        }
        $scope.showReports();
    };

    $scope.setNextPage = function() {
        $scope.currentReportsPage++;
        if ($scope.currentReportsPage >= $scope.getPageCount()) {
            $scope.currentReportsPage = 0;
        }
        $scope.showReports();
    };

    $scope.fetchTrendAreas = function(area) {
        $http.get('api/dashboards/user-areas/' + area.id)
            .success(function(areas) {
               areas.sort(function(a, b) {
                    return a.level.hierarchyDepth - b.level.hierarchyDepth || a.name.localeCompare(b.name);
               });
               var arr = Array();

               $scope.topAreaLevel = areas[0].level.hierarchyDepth;

               for (var index=0; index<areas.length; index++) {
                    var levelDiff = areas[index].level.hierarchyDepth - $scope.topAreaLevel;
                    var padding = '';
                    for (var i = 0; i < levelDiff; i++) {
                        padding += '--- ';
                    }

                    areas[index].name = padding + areas[index].name;
                    arr.push(areas[index]);
               }
               $scope.areas = arr;
               $scope.areaId = areas[0].id;
        });
    };

    $scope.fetchCurrentUserAreas = function() {
        $http.get('api/users/logged_in/area')
            .success(function(areaId) {
                $scope.fetchTrendAreas(areaId);
            });
    };
    $scope.fetchCurrentUserAreas();

    $scope.fetchAreas = function(reportRow) {
        $http.get('api/dashboards/user-areas/' + reportRow[0].indicatorAreaId)
            .success(function(areas) {
               areas.sort(function(a, b) {
                   return a.level.hierarchyDepth - b.level.hierarchyDepth || a.name.localeCompare(b.name);
               });
               var arr = Array();

               $scope.topAreaLevel = areas[0].level.hierarchyDepth;

               for (var index = 0; index < areas.length; index++) {
                   var levelDiff = areas[index].level.hierarchyDepth - $scope.topAreaLevel;
                   var padding = '';
                   for (var i = 0; i < levelDiff; i++) {
                       padding += '-- ';
                   }

                   areas[index].name = padding + areas[index].name;
                   arr.push(areas[index]);
               }

               for(var i = 0; i < reportRow.length; i++) {
                    if(reportRow[i] != null) {
                        reportRow[i].areas = arr;
                        reportRow[i].areaId = arr[0].id;
                    }
               }
          });
    };

    $scope.showReports = function() {
        $scope.loading = true;
        setTimeout(function() {
            $scope.prepareCurrentPageReportsRows();
            for (var i = 0; i < $scope.reportRows.length; i++) {
                $scope.fetchAreas($scope.reportRows[i]);
            }
            $scope.loading = false;
        }, 0);
    };

    $scope.tabChanged = function(dashboard) {
        $scope.reportRows = [];
        $scope.charts = [];
        $scope.dashboard = dashboard;
        if (dashboard.name == "Performance summary") {
            $scope.loading = true;
            $scope.fetchTrends();
        } else if (dashboard.name == "Map report") {
            $("#mapReport1").html('');
            $("#mapReport2").html('');
            for (var i in $scope.maps) {
                if ($scope.maps.hasOwnProperty(i)) {
                    $scope.fetchMapReport($scope.maps[i]);
                }
            }
        } else {
            $scope.currentReportsPage = 0;
            $scope.loading = true;
            $scope.prepareAllReportRows();
            if ($scope.allReportsRows.length == 0) {
                $scope.loading = false;
            } else {
                $scope.showReports();
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
        url = 'api/trend?startDate=' + moment(startDate).format("DD/MM/YYYY") +
                '&endDate=' + moment(endDate).format("DD/MM/YYYY") +
                '&areaId=' + $scope.areaId +
                '&frequencyId=' + $scope.frequencyId;
        $http.get(url)
                .success(function(indicatorCategories) {
            $scope.indicatorCategories = indicatorCategories;
            $scope.trendPerCategory = {};

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
            $scope.loading = false;
        });
    };

    $scope.maps = [];
    for (i=0; i<2; i+=1) {
        $scope.maps[i] = {
            startDate: new Date(moment().subtract('months', 1).format("L")),
            endDate: new Date(moment().format("L")),
            selectedIndicatorCategoryId: null,
            selectedCategoryIndicators: [],
            selectedIndicatorId: null,
            containerId: "mapReport" + i,
            frequencyId: 1,
            level: "state"
        };
    }

    $scope.analyzeMap = function(map) {
        map.level = "state";
        $scope.fetchMapReport(map);
    }

    $scope.analyze = function() {
        $scope.fetchTrends();
    }

    $scope.getDefaultDashboard = function() {
        $http.get("api/users/logged_in/dashboard")
            .success(function(data) {
                for(var i = 0; i < $scope.dashboards.length; i++) {
                    if($scope.dashboards[i].id == data.id) {
                        $scope.tabChanged($scope.dashboards[i]);
                        break;
                    }
                }
             $('a[href="#tab_' + data.id + '"]').click();
            }).error(function() {
                $errorService.genericError($scope, 'dashboard.error.cannotGetDefaultDashboard');
            })
    }

    $scope.setDefaultDashboard = function() {
        $http({
            method: 'PUT',
            url: 'api/users/logged_in/dashboard/' + $scope.dashboard.id
        }).error(function() {
            $errorService.genericError($scope, 'dashboard.error.cannotUpdateDefaultDashboard');
        });
    }

    $scope.fetchCategoryIndicators = function(map) {
        if(map.selectedIndicatorCategoryId != null) {
            $http.get('api/indicator/filter/' + map.selectedIndicatorCategoryId).success(function(indicators) {
                map.selectedCategoryIndicators = indicators;
            });
        }
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

    $scope.fetchMapReport = function(map, stateCode) {
        if(map.selectedIndicatorId != null) {
            var url = 'api/map-report?indicatorId=' + map.selectedIndicatorId + '&startDate=' + moment(map.startDate).format('L') + '&endDate=' + moment(map.endDate).format('L') +
                       '&frequencyId=' + map.frequencyId + "&level=" + map.level;
            if (stateCode != undefined) {
                url += "&state=" + stateCode;
            }
            $http.get(url).success(function(data) {
                for (var i in data) {
                    if (data.hasOwnProperty(i)) {
                        data[i] = data[i].toString();
                    }
                }
                $('#' + map.containerId).html('').vectorMap({
                    map: map.level == "block" ? 'bihar-block' : "bihar-state",
                    onRegionClick: function(event, code) {
                        if (map.level == "block") {
                            return;
                        }
                        map.level = "block";
                        $('.jvectormap-label').remove();
                        $scope.fetchMapReport(map, code);
                    },
                    onViewportChange: function(e, scale) {
                        if (scale > 1 && scale < 2 && map.level == "block") {
                            map.level = "state";
                            $scope.analyzeMap(map);
                        }
                    },
                    focusOn: {
                      x: 0.5,
                      y: 0.5,
                      scale: map.level == "block" ? 3 : 1
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
        }
    };

    $scope.indicator = { name: null };
    $scope.chartData = [];

    $scope.exportToCsv = function(report) {
        var indicatorId = report.indicatorId;
        var url = 'api/chart/data/export/?indicatorId=' + indicatorId
            + '&startDate=' + moment(report.from).format("DD/MM/YYYY")
            + '&endDate=' + moment(report.to).format("DD/MM/YYYY")
            + '&frequencyId=' + report.frequencyId;

        if (!isNaN(report.areaId) && isFinite(report.areaId)) {
            url += '&areaId=' + report.areaId;
        }
        window.open(url);
    }

    $scope.exportCaseListReportToCsv = function(report) {
            var indicatorId = report.indicatorId;
            var url = 'api/indicator/' + indicatorId + '/export/caselistreport'
                + '?fromDate=' + moment(report.from).format("DD/MM/YYYY")
                + '&toDate=' + moment(report.to).format("DD/MM/YYYY")
                + '&areaId=' + report.areaId;

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
        }
    };

    $scope.loadChartDetails = function(report) {
        $scope.fetchChartData(report);
    };

    $scope.fetchChartData = function(report) {
        var indicatorId = report.indicatorId;
        var url = 'api/chart/data/?indicatorId=' + indicatorId
            + '&startDate=' + moment(report.from).format("DD/MM/YYYY")
            + '&endDate=' + moment(report.to).format("DD/MM/YYYY")
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

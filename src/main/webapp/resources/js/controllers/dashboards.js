var care = angular.module('care');

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function sortByDateComparisonFunction(a, b) {
    return parseInt(a) - parseInt(b);
};

care.controller('dashboardController', function($rootScope, $scope, $http, $location, $dialog, $simplifiedHttpService, $compile, $errorService) {

    $scope.title = 'dashboards.title';

    $scope.startDate = moment().subtract('months', 1);
    $scope.endDate = moment();

    $scope.indicatorClassifications = [];
    $scope.charts = {};
    $scope.frequencies = [];

    $scope.currentReportsPage = 0;
    $scope.reportsPerPage = 3;
    $scope.loading = true;

    $scope.fullscreenReport = null;

    $scope.fullscreen = function(report) {
        if($scope.fullscreenReport == null) {
            $scope.fullscreenReport = report;
            $scope.reportRows[report.position.x] = null;
        } else {
            $scope.reportRows[report.position.x] = report;
            $scope.fullscreenReport = null;
        }
    };

    $scope.compareDashboardPositions = function(dashboardA, dashboardB) {
        return parseInt(dashboardA.tabPosition) - parseInt(dashboardB.tabPosition);
    };

    $scope.fetchDashboards = function() {
        $http.get('api/dashboards/dto').success(function(dto) {
            $scope.dashboards = dto.dashboards;
            $scope.dashboards.sort($scope.compareDashboardPositions);
            $scope.frequencies = dto.frequencies;
            $scope.frequencyId = dto.frequencies[0].id;
            $scope.userArea = dto.area;
            $scope.areaId = dto.area.id;
            $scope.sortAreas(dto.areas);
            for(var i = 0; i < $scope.dashboards.length; i++) {
                    if($scope.dashboards[i].id == dto.defaultDashboard.id) {
                        $scope.tabChanged($scope.dashboards[i]);
                        $scope.defaultDashboard = dto.defaultDashboard.tabPosition;
                        break;
                    }
            }
        }).error(function() {
            $errorService.genericError($scope, 'error');
        });
    };

    $scope.reportRows = [];
    $scope.allReportsRows = [];
    $scope.reportValues = [];
    $scope.currentTabIndicators = [];

    $scope.fetchIndicators = function() {
        $http.get('api/users/indicators')
            .success(function(indicators) {
                $scope.indicators = indicators;
                $scope.fetchDashboards();
            }).error(function() {
                $errorService.genericError($scope, 'indicators.list.error.cannotLoadIndicatorList');
            });
    };

    $scope.fetchIndicators();

    $scope.getIndicatorsForClassification = function(indicatorClassification){
    var indicators = [];
        for (var i = 0; i < $scope.indicators.length ; i++){
            for (var j = 0; j < $scope.indicators[i].classifications.length ; j++){
                if($scope.indicators[i].classifications[j].name == indicatorClassification.name){
                    indicators.push($scope.indicators[i]);
                }
            }
        }
        return indicators;
    }

    $scope.prepareAllReportRows = function() {
        $scope.allReportsRows = [];
        if ($scope.dashboard == undefined || $scope.dashboard.indicatorClassification == undefined) {
            return;
        }
        var indicators = $scope.getIndicatorsForClassification($scope.dashboard.indicatorClassification);
        $scope.currentTabIndicators = indicators;
        for (var i = 0; i < indicators.length; i++) {
            var indicator = indicators[i];
            if (indicator.reports == undefined) {
                continue;
            }
            indicator.reports.sort(function(a, b) { return parseInt(a.reportType.id) - parseInt(b.reportType.id); });
            var indicatorReport = indicator.reports[0];
            if (indicatorReport != null && indicatorReport.reportType.name.toLowerCase().endsWith('chart')) {
                var report = indicatorReport;
                report.reportTypes = [];
                for (var r = 0; r < indicator.reports.length; r++) {
                    report.reportTypes.push(indicator.reports[r].reportType);
                }
                if (!indicator.reports.hasOwnProperty(0)) {
                    continue;
                }
                report.indicatorId = indicator.id;
                report.needsRefreshing = true;
                report.computing = !indicator.isComputed;
                report.indicatorAreaId = $scope.userArea.id;
                report.indicatorName = indicator.name;
                report.frequencyId = indicator.defaultFrequency.id;
                report.additive = indicator.isAdditive;
                report.categorized = indicator.isCategorized;
                report.rowIndex = $scope.reportRows.length;
                report.index = 0;
                report.displayType = 'chart';
                report.from = moment().subtract('months', 1).format("L");
                report.to = moment().format("L");
                report.canExportCaseListReport = true;
                report.position = {};
                report.position.x = i % $scope.reportsPerPage;
                report.position.y = 0;
            }
            $scope.allReportsRows.push(report);
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
        if ($scope.dashboard == undefined || $scope.dashboard.indicatorClassification == undefined) {
            return 0;
        }
        return Math.ceil($scope.currentTabIndicators.length/$scope.reportsPerPage);
    };

    $scope.getPages = function() {
        return new Array($scope.getPageCount());
    }

    $scope.reportFromDateChanged = function(report) {
        if (moment(report.to).diff(report.from, 'days') < 0) {
            report.from = moment(report.from).format("L");
        }
    };

    $scope.reportToDateChanged = function(report) {
        if (moment(report.to).diff(report.from, 'days') < 0) {
            report.to = moment(report.to).format("L");
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

    $scope.sortFunction = function(a,b) {
        if ( $scope.findHighestParentArea(a).id != $scope.findHighestParentArea(b).id ) {
            return a.name.localeCompare(b.name);
        } else if ( a.level.hierarchyDepth - b.level.hierarchyDepth < 0 ) {
            return -1;
        } else if ( a.level.hierarchyDepth - b.level.hierarchyDepth > 0 ) {
            return 1;
        } else if ( a.level.hierarchyDepth == b.level.hierarchyDepth ) {
            return a.name.localeCompare(b.name);
        }
    };

    $scope.findHighestParentArea = function(area) {
        switch (area.level.hierarchyDepth){
            case 1:
                return area;
                break;
            case 2:
                return area.parentArea;
                break;
            case 3:
                return area.parentArea.parentArea;
                break;
        }
    };

    $scope.sortAreas = function(areas) {
                areas.sort($scope.sortFunction);
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
    };

    $scope.fetchAreas = function(report) {
        $http.get('api/dashboards/user-areas')
            .success(function(areas) {
               areas.sort($scope.sortFunction);
               var arr = Array();

               $scope.topAreaLevel = areas[0].level.hierarchyDepth;

               for (var index = 0; index < areas.length; index++) {
                   var levelDiff = areas[index].level.hierarchyDepth - $scope.topAreaLevel;
                   var padding = '';
                   for (var i = 0; i < levelDiff; i++) {
                       padding += '--- ';
                   }

                   areas[index].name = padding + areas[index].name;
                   arr.push(areas[index]);
               }
                if(report != null) {
                    report.areas = arr;
                    report.areaId = arr[0].id;
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
            for (var i=0; i<$scope.maps.length; i+=1) {
                var map = $scope.maps[i];
                map.regionName = '';
                $('#maprange' + i).daterangepicker({
                    ranges: {
                    'Today': [moment(), moment()],
                    'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                    'Last 7 Days': [moment().subtract('days', 6), moment()],
                    'Last 30 Days': [moment().subtract('days', 29), moment()],
                    'This Month': [moment().startOf('month'), moment().endOf('month')],
                    'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                    },
                    startDate: moment().subtract('days', 29),
                    endDate: moment()
                    }, function(start, end) {
                        var index = $(this.element).attr("data-map-index");
                        $scope.maps[index].startDate = start;
                        $scope.maps[index].endDate = end;
                });
                $('#maprange' + i).val(map.startDate.format('L') + ' - ' + map.endDate.format('L'));
                $("#mapReport" + i).html('');
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

    $scope.trendPerClassification = {};

    $scope.fetchTrends = function() {
    if($scope.areaId != undefined) {
        var startDate = $scope.startDate.format('L'),
            endDate = $scope.endDate.format('L'),
            url;
        url = 'api/trend?startDate=' + moment(startDate).format("L") +
                '&endDate=' + moment(endDate).format("L") +
                '&areaId=' + $scope.areaId +
                '&frequencyId=' + $scope.frequencyId;
        $http.get(url)
                .success(function(indicatorClassifications) {
            $scope.indicatorClassifications = indicatorClassifications;
            $scope.trendPerClassification = {};

            for (var c = 0; c < $scope.indicatorClassifications.length; c++) {
                if (!$scope.indicatorClassifications.hasOwnProperty(c)) {
                    continue;
                }

                var classification = $scope.indicatorClassifications[c];
                var key = 'classification_' + classification.name;
                var indicators = $scope.getIndicatorsForClassification(classification);
                for (var i = 0; i < indicators.length; i++) {
                    if ($scope.trendPerClassification[key] === undefined) {
                        $scope.trendPerClassification[key] = {
                            negative: 0,
                            positive: 0
                        };
                    }

                    var trend = indicators[i].trend;
                    if (trend < 0) {
                        $scope.trendPerClassification[key].negative++;
                    } else if (trend > 0) {
                        $scope.trendPerClassification[key].positive++;
                    }
                }
            }
            $scope.loading = false;
        });
        }
    };

    $scope.maps = [];
    for (i=0; i<2; i+=1) {
        $scope.maps[i] = {
            startDate: moment().subtract('months', 1),
            endDate: moment(),
            selectedIndicatorClassificationId: null,
            selectedClassificationIndicators: [],
            selectedIndicator: null,
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

    $scope.setDefaultDashboard = function() {
        $http({
            method: 'PUT',
            url: 'api/users/logged_in/dashboard/' + $scope.dashboard.id
        }).error(function() {
            $errorService.genericError($scope, 'dashboard.error.cannotUpdateDefaultDashboard');
        });
    }

    $scope.fetchClassificationIndicators = function(map) {
        if(map.selectedClassification != null) {
             map.selectedClassificationIndicators = map.selectedClassification.indicators;
            if(map.selectedClassificationIndicators.length > 0) {
                map.selectedIndicator = map.selectedClassification.indicators[0];
             }
        }
    };

    $scope.fetchClassifications = function() {
        $http.get('api/indicator/classification').success(function(indicatorClassifications) {
            $scope.mapClassifications = indicatorClassifications;
            $scope.maps[0].selectedClassification = $scope.maps[1].selectedClassification = indicatorClassifications[0];
            $scope.$watch('maps[0].selectedClassification', function(newValue, oldValue) {
                $scope.fetchClassificationIndicators($scope.maps[0]);
            }, true);
            $scope.$watch('maps[1].selectedClassification', function(newValue, oldValue) {
                $scope.fetchClassificationIndicators($scope.maps[1]);
            }, true);
        });
    };

    $scope.fetchClassifications();

    $scope.fetchMapReport = function(map, stateCode) {
        if(map.selectedIndicator != null) {
            var url = 'api/map-report?indicatorId=' + map.selectedIndicator.id + '&startDate=' + map.startDate.format('L') + '&endDate=' + map.endDate.format('L') +
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
                map.regionName = (map.level == "block" && stateCode != undefined) ? stateCode : "BIHAR";
                $('#' + map.containerId).html('').vectorMap({
                    map: (map.level == "block" && stateCode != undefined) ? stateCode.toLowerCase() : "bihar-state",
                    onRegionClick: function(event, code) {
                        if (map.level == "block" || !data[code]) {
                            return;
                        }
                        map.level = "block";
                        $('.jvectormap-label').remove();
                        $scope.fetchMapReport(map, code);
                    },
                    onViewportChange: function(e, scale) {
                        if (scale <= 0.5 && map.level == "block") {
                            map.level = "state";
                            $scope.analyzeMap(map);
                        }
                    },
                    zoomMin: 0.5,
                    focusOn: {
                      x: 0.5,
                      y: 0.5,
                      scale: 1
                    },
                    series: {
                        regions: [{
                            values: data,
                            scale: {
                                "1": '#008000',
                                "0.5": '#ffc000',
                                "0": '#82AAFF',
                                "-0.5": '#FF6666',
                                "-1": '#cd0f17'
                            }
                        }]
                    },
                    regionStyle: {
                      initial: {
                        fill: '#F3F3F3',
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
                    backgroundColor: "#ffffff",
                    onRegionLabelShow: function(e, el, code) {
                        if (data[code] == undefined) {
                            $(el).html($scope.msg('dashboards.map.noData', code));
                        }
                        var ads = data[code];
                        $(el).removeClass("positive").removeClass("negative").removeClass("neutral").addClass(data[code]);
                        el.html('<span class="name">' + el.html() + '</span>');
                        if (data[code] > 1) {
                            el.html(el.html() + ' <img src="resources/images/trend_positive.png" />');
                        } else if (data[code] < 0) {
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
            + '&startDate=' + moment(report.from).format("L")
            + '&endDate=' + moment(report.to).format("L")
            + '&frequencyId=' + report.frequencyId;

        if (!isNaN(report.areaId) && isFinite(report.areaId)) {
            url += '&areaId=' + report.areaId;
        }
        window.open(url);
    }

    $scope.exportCaseListReportToCsv = function(report) {
            var indicatorId = report.indicatorId;
            var url = 'api/indicator/' + indicatorId + '/export/caselistreport'
                + '?fromDate=' + moment(report.from).format("L")
                + '&toDate=' + moment(report.to).format("L")
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
            + '&startDate=' + moment(report.from).format("L")
            + '&endDate=' + moment(report.to).format("L")
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

    $('#reportrange').daterangepicker({
    ranges: {
        'Today': [moment(), moment()],
        'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
        'Last 7 Days': [moment().subtract('days', 6), moment()],
        'Last 30 Days': [moment().subtract('days', 29), moment()],
        'This Month': [moment().startOf('month'), moment().endOf('month')],
        'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
    },
    startDate: moment().subtract('days', 29),
    endDate: moment()
    }, function(start, end) {
        $scope.startDate = start;
        $scope.endDate = end;
        $('#reportrange').val(start.format('L') + ' - ' + end.format('L'));
    });
    $('#reportrange').val($scope.startDate.format('L') + ' - ' + $scope.endDate.format('L'));

});
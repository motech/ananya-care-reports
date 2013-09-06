package org.motechproject.carereporting.domain.dto;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;

import java.util.Set;

public class DashboardDto {

    private Set<FrequencyEntity> frequencies;

    private Set<AreaEntity> areas;

    private Set<DashboardEntity> dashboards;

    private DashboardEntity defaultDashboard;

    private Set<TrendIndicatorCategoryDto> trendDtos;

    public DashboardDto(Set<FrequencyEntity> frequencies, Set<AreaEntity> areas, Set<DashboardEntity> dashboards, DashboardEntity defaultDashboard, Set<TrendIndicatorCategoryDto> trendDtos) {
        this.frequencies = frequencies;
        this.areas = areas;
        this.dashboards = dashboards;
        this.defaultDashboard = defaultDashboard;
        this.trendDtos = trendDtos;
    }

    public Set<FrequencyEntity> getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(Set<FrequencyEntity> frequencies) {
        this.frequencies = frequencies;
    }

    public Set<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaEntity> areas) {
        this.areas = areas;
    }

    public Set<DashboardEntity> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<DashboardEntity> dashboards) {
        this.dashboards = dashboards;
    }

    public DashboardEntity getDefaultDashboard() {
        return defaultDashboard;
    }

    public void setDefaultDashboard(DashboardEntity defaultDashboard) {
        this.defaultDashboard = defaultDashboard;
    }

    public Set<TrendIndicatorCategoryDto> getTrendDtos() {
        return trendDtos;
    }

    public void setTrendDtos(Set<TrendIndicatorCategoryDto> trendDtos) {
        this.trendDtos = trendDtos;
    }
}

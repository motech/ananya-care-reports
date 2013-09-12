package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.views.DashboardJsonView;

import java.util.Set;

public class DashboardDto {

    @JsonView({ DashboardJsonView.class })
    private Set<FrequencyEntity> frequencies;

    @JsonView({ DashboardJsonView.class })
    private AreaEntity area;

    @JsonView({ DashboardJsonView.class })
    private Set<AreaEntity> areas;

    @JsonView({ DashboardJsonView.class })
    private Set<DashboardEntity> dashboards;

    @JsonView({ DashboardJsonView.class })
    private DashboardEntity defaultDashboard;

    public DashboardDto(Set<FrequencyEntity> frequencies, AreaEntity area, Set<AreaEntity> areas, Set<DashboardEntity> dashboards, DashboardEntity defaultDashboard) {
        this.frequencies = frequencies;
        this.area = area;
        this.areas = areas;
        this.dashboards = dashboards;
        this.defaultDashboard = defaultDashboard;
    }

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
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
}

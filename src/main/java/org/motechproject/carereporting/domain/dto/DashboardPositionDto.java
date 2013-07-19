package org.motechproject.carereporting.domain.dto;

public class DashboardPositionDto {

    private String name;
    private Integer position;

    public DashboardPositionDto() {

    }

    public DashboardPositionDto(final String name, final Integer position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}

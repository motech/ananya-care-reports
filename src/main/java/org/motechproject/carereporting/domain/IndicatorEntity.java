package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Entity
@Table(name = "indicator")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_id"))
})
@FetchProfile(name = "indicator-with-reports", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = IndicatorEntity.class, association = "reports", mode = FetchMode.JOIN)
})
public class IndicatorEntity extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "denominator_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity denominator;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "numerator_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity numerator;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "indicator_indicator_category", joinColumns = { @JoinColumn(name = "indicator_id") },
            inverseJoinColumns = { @JoinColumn(name = "indicator_category_id") })
    @JsonView({ IndicatorJsonView.IndicatorModificationDetails.class, DashboardJsonView.class })
    private Set<IndicatorCategoryEntity> categories;

    @NotNull
    @JoinColumn(name = "area_level_id", referencedColumnName = "level_id", nullable = false)
    @ManyToOne
    private LevelEntity areaLevel;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity owner;

    @ManyToMany
    @JoinTable(name = "indicator_role", joinColumns = { @JoinColumn(name = "indicator_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "indicator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, IndicatorJsonView.IndicatorModificationDetails.class, DashboardJsonView.class })
    private Set<ReportEntity> reports;

    @OneToMany(mappedBy = "indicator", cascade = CascadeType.REMOVE)
    private Set<IndicatorValueEntity> indicatorValues;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "frequency_id", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorModificationDetails.class, DashboardJsonView.class })
    private FrequencyEntity defaultFrequency;

    @NotNull
    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    @JsonView({ BaseView.class })
    private String name;

    @Column(name = "trend", nullable = true)
    @JsonView({ BaseView.class })
    private BigDecimal trend;

    @Column(name = "is_computed", nullable = false)
    @JsonView({ BaseView.class, DashboardJsonView.class, IndicatorJsonView.ListIndicatorNames.class })
    private Boolean isComputed;

    @Column(name = "is_additive", nullable = false)
    @JsonView({ DashboardJsonView.class })
    private Boolean isAdditive;

    @Column(name = "is_categorized", nullable = true)
    private Boolean isCategorized;

    public Boolean getAdditive() {
        return isAdditive;
    }

    public void setAdditive(Boolean additive) {
        isAdditive = additive;
    }

    public Boolean getComputed() {
        return isComputed;
    }

    public void setComputed(Boolean computed) {
        isComputed = computed;
    }

    public DwQueryEntity getDenominator() {
        return denominator;
    }

    public void setDenominator(DwQueryEntity denominator) {
        this.denominator = denominator;
    }

    public DwQueryEntity getNumerator() {
        return numerator;
    }

    public void setNumerator(DwQueryEntity numerator) {
        this.numerator = numerator;
    }

    public Set<IndicatorCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<IndicatorCategoryEntity> categories) {
        this.categories = categories;
    }

    public LevelEntity getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(LevelEntity areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Set<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(Set<ReportEntity> reports) {
        this.reports = reports;
    }

    public FrequencyEntity getDefaultFrequency() {
        return defaultFrequency;
    }

    public void setDefaultFrequency(FrequencyEntity defaultFrequency) {
        this.defaultFrequency = defaultFrequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IndicatorValueEntity> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(Set<IndicatorValueEntity> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }

    public BigDecimal getTrend() {
        return trend;
    }

    public void setTrend(BigDecimal trend) {
        this.trend = trend;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Boolean isCategorized() {
        return isCategorized != null ? isCategorized : false;
    }

    public void setCategorized(Boolean categorized) {
        isCategorized = categorized;
    }
}

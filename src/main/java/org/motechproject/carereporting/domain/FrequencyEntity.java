package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.BaseView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "frequency")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "frequency_id"))
})
public class FrequencyEntity extends AbstractEntity {

    @NotEmpty
    @Column(name = "frequency_name", unique = true)
    @JsonView({ BaseView.class })
    private String frequencyName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_frequency_id")
    private FrequencyEntity parentFrequency;

    @OneToOne(mappedBy = "parentFrequency", fetch = FetchType.LAZY)
    private FrequencyEntity childFrequency;

    @OneToOne(mappedBy = "frequency", fetch = FetchType.LAZY)
    private CronTaskEntity cronTask;

    @OneToMany(mappedBy = "frequency")
    private Set<IndicatorValueEntity> indicatorValues;

    public FrequencyEntity() {

    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public FrequencyEntity getParentFrequency() {
        return parentFrequency;
    }

    public void setParentFrequency(FrequencyEntity parentFrequency) {
        this.parentFrequency = parentFrequency;
    }

    public FrequencyEntity getChildFrequency() {
        return childFrequency;
    }

    public void setChildFrequency(FrequencyEntity childFrequency) {
        this.childFrequency = childFrequency;
    }

    public CronTaskEntity getCronTask() {
        return cronTask;
    }

    public void setCronTask(CronTaskEntity cronTask) {
        this.cronTask = cronTask;
    }

    public Set<IndicatorValueEntity> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(Set<IndicatorValueEntity> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }
}

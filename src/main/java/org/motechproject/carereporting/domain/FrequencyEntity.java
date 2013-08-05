package org.motechproject.carereporting.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String frequencyName;

    @ManyToOne
    @JoinColumn(name = "parent_frequency_id")
    private FrequencyEntity parentFrequency;

    @OneToMany(mappedBy = "parentFrequency")
    private Set<FrequencyEntity> childFrequencies;

    @OneToOne(mappedBy = "frequency")
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

    public Set<FrequencyEntity> getChildFrequencies() {
        return childFrequencies;
    }

    public void setChildFrequencies(Set<FrequencyEntity> childFrequencies) {
        this.childFrequencies = childFrequencies;
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

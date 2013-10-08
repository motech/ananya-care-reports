package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "where_group")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "where_group_id"))
})
public class WhereGroupEntity extends AbstractEntity implements Cloneable {

    @Column(name = "operator")
    @JsonView({ QueryJsonView.EditForm.class })
    private String operator;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "where_group_where_group", joinColumns = { @JoinColumn(name = "where_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "where_group2_id") })
    @OrderBy
    @JsonView({ QueryJsonView.EditForm.class })
    private Set<WhereGroupEntity> whereGroups;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "where_group_condition", joinColumns = { @JoinColumn(name = "where_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "condition_id") })
    @JsonView({ QueryJsonView.EditForm.class })
    private Set<ConditionEntity> conditions;

    // don't change
    public WhereGroupEntity() {
        whereGroups = new LinkedHashSet<>();
        conditions = new LinkedHashSet<>();
    }

    public WhereGroupEntity(WhereGroupEntity whereGroup) {
        this();
        operator = whereGroup.getOperator();
        for (WhereGroupEntity whereGroupEntity : whereGroup.getWhereGroups()) {
            whereGroups.add(new WhereGroupEntity(whereGroupEntity));
        }
        for (ConditionEntity conditionEntity : whereGroup.getConditions()) {
            if (conditionEntity instanceof CalculationEndDateConditionEntity) {
                conditions.add(new CalculationEndDateConditionEntity((CalculationEndDateConditionEntity) conditionEntity));
            } else if (conditionEntity instanceof DateDiffComparisonConditionEntity) {
                conditions.add(new DateDiffComparisonConditionEntity((DateDiffComparisonConditionEntity) conditionEntity));
            } else if (conditionEntity instanceof FieldComparisonConditionEntity) {
                conditions.add(new FieldComparisonConditionEntity((FieldComparisonConditionEntity) conditionEntity));
            } else if (conditionEntity instanceof PeriodConditionEntity) {
                conditions.add(new PeriodConditionEntity((PeriodConditionEntity) conditionEntity));
            } else if (conditionEntity instanceof ValueComparisonConditionEntity) {
                conditions.add(new ValueComparisonConditionEntity((ValueComparisonConditionEntity) conditionEntity));
            }
        }
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Set<WhereGroupEntity> getWhereGroups() {
        return whereGroups;
    }

    public void setWhereGroups(Set<WhereGroupEntity> whereGroups) {
        this.whereGroups = whereGroups;
    }

    public Set<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(Set<ConditionEntity> conditions) {
        this.conditions = conditions;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        WhereGroupEntity whereGroupEntity = new WhereGroupEntity();

        whereGroupEntity.setOperator(this.getOperator());
        if (this.getWhereGroups() != null) {
            whereGroupEntity.setWhereGroups(new LinkedHashSet<WhereGroupEntity>());
            for (WhereGroupEntity group : this.getWhereGroups()) {
                whereGroupEntity.getWhereGroups().add((WhereGroupEntity) group.clone());
            }
        }

        if (this.getConditions() != null) {
            whereGroupEntity.setConditions(new LinkedHashSet<ConditionEntity>());
            for (ConditionEntity condition : this.getConditions()) {
                whereGroupEntity.getConditions().add((ConditionEntity) condition.clone());
            }
        }

        return whereGroupEntity;
    }
}

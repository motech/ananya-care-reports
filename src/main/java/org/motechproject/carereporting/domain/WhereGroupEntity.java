package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "where_group")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "where_group_id"))
})
public class WhereGroupEntity extends AbstractEntity {

    @Column(name = "operator")
    private String operator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "where_group_where_group", joinColumns = { @JoinColumn(name = "where_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "where_group2_id") })
    private Set<WhereGroupEntity> whereGroups;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "where_group_condition", joinColumns = { @JoinColumn(name = "where_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "condition_id") })
    private Set<ConditionEntity> conditions;

    public WhereGroupEntity() {
        whereGroups = new LinkedHashSet<>();
        conditions = new LinkedHashSet<>();
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
}

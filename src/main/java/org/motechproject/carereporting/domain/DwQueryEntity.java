package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "dw_query")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "dw_query_id"))
})
public class DwQueryEntity extends AbstractEntity {

    @Column(name = "table_name", length = 100)
    private String tableName;

    @OneToMany(mappedBy = "dwQuery", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SelectColumnEntity> selectColumns;

    @OneToOne(mappedBy = "dwQuery", cascade = CascadeType.ALL)
    @JoinColumn(name = "combination_id", referencedColumnName = "combination_id")
    private CombinationEntity combination;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grouped_by_id", referencedColumnName = "grouped_by_id")
    private GroupedByEntity groupedBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "where_group_id", referencedColumnName = "where_group_id")
    private WhereGroupEntity whereGroup;

    @Column(name = "has_period_condition")
    private boolean hasPeriodCondition;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity parentQuery;

    @JsonView({ BaseView.class })
    @Column(name = "name")
    private String name;

    public DwQueryEntity() {
    }

    public DwQueryEntity(DwQueryEntity dwQueryEntity) {
        tableName = dwQueryEntity.getTableName();
        hasPeriodCondition = dwQueryEntity.getHasPeriodCondition();
        selectColumns = new LinkedHashSet<>();
        for (SelectColumnEntity selectColumnEntity : dwQueryEntity.getSelectColumns()) {
            selectColumns.add(new SelectColumnEntity(selectColumnEntity));
        }
        combination = dwQueryEntity.getCombination() != null ? new CombinationEntity(dwQueryEntity.getCombination()) : null;
        groupedBy = dwQueryEntity.getGroupedBy() != null ? new GroupedByEntity(dwQueryEntity.getGroupedBy()) : null;
        whereGroup = dwQueryEntity.getWhereGroup() != null ? new WhereGroupEntity(dwQueryEntity.getWhereGroup()) : null;
        parentQuery = dwQueryEntity.getParentQuery();
        name = dwQueryEntity.getName();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<SelectColumnEntity> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(Set<SelectColumnEntity> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public CombinationEntity getCombination() {
        return combination;
    }

    public void setCombination(CombinationEntity combination) {
        this.combination = combination;
    }

    public GroupedByEntity getGroupedBy() {
        return groupedBy;
    }

    public void setGroupedBy(GroupedByEntity groupedBy) {
        this.groupedBy = groupedBy;
    }

    public WhereGroupEntity getWhereGroup() {
        return whereGroup;
    }

    public void setWhereGroup(WhereGroupEntity whereGroup) {
        this.whereGroup = whereGroup;
    }

    public boolean getHasPeriodCondition() {
        return hasPeriodCondition;
    }

    public void setHasPeriodCondition(boolean hasPeriodCondition) {
        this.hasPeriodCondition = hasPeriodCondition;
    }

    public DwQueryEntity getParentQuery() {
        return parentQuery;
    }

    public void setParentQuery(DwQueryEntity parentQuery) {
        this.parentQuery = parentQuery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

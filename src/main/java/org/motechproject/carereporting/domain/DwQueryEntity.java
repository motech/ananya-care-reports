package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "dw_query")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "dw_query_id"))
})
public class DwQueryEntity extends AbstractEntity implements Cloneable {

    @Column(name = "table_name", length = 100)
    @JsonView({ QueryJsonView.EditForm.class })
    private String tableName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "dw_query_select_column", joinColumns = { @JoinColumn(name = "dw_query_id") },
            inverseJoinColumns = { @JoinColumn(name = "select_column_id") })
    @JsonView({ QueryJsonView.EditForm.class })
    private Set<SelectColumnEntity> selectColumns;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "combination_id", referencedColumnName = "combination_id")
    @JsonView({ QueryJsonView.EditForm.class })
    private CombinationEntity combination;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "grouped_by_id", referencedColumnName = "grouped_by_id")
    @JsonView({ QueryJsonView.EditForm.class })
    private GroupedByEntity groupedBy;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "where_group_id", referencedColumnName = "where_group_id")
    private WhereGroupEntity whereGroup;

    @OneToMany(mappedBy = "dwQuery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderByEntity> orderBy;

    @Column(name = "has_period_condition")
    @JsonView({ QueryJsonView.EditForm.class })
    private boolean hasPeriodCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity parentQuery;

    @Column(name = "result_limit", nullable = true)
    private Integer limit;

    @Column(name = "name")
    @JsonView({ BaseView.class })
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
        if (dwQueryEntity.getOrderBy() != null) {
            orderBy = new LinkedHashSet<>();
            for (OrderByEntity orderByEntity : dwQueryEntity.getOrderBy()) {
                orderBy.add(new OrderByEntity(orderByEntity));
            }
        }
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

    public Set<OrderByEntity> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Set<OrderByEntity> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DwQueryEntity dwQueryEntity = new DwQueryEntity();

        dwQueryEntity.setName(this.getName());
        dwQueryEntity.setTableName(this.tableName);
        dwQueryEntity.setSelectColumns(new LinkedHashSet<SelectColumnEntity>());
        for (SelectColumnEntity column : this.getSelectColumns()) {
            dwQueryEntity.getSelectColumns().add((SelectColumnEntity) column.clone());
        }

        if (this.getGroupedBy() != null) {
            dwQueryEntity.setGroupedBy((GroupedByEntity) this.getGroupedBy().clone());
        }

        dwQueryEntity.setWhereGroup((WhereGroupEntity) this.whereGroup.clone());

        if (this.getCombination() != null) {
            dwQueryEntity.setCombination((CombinationEntity) this.getCombination().clone());
            dwQueryEntity.getCombination().getDwQuery().setParentQuery(dwQueryEntity);
        }

        if (this.getOrderBy() != null) {
            dwQueryEntity.setOrderBy(new LinkedHashSet<OrderByEntity>());
            for (OrderByEntity orderByEntity : dwQueryEntity.getOrderBy()) {
                dwQueryEntity.getOrderBy().add((OrderByEntity) orderByEntity.clone());
            }
        }

        dwQueryEntity.setLimit(this.getLimit());

        return dwQueryEntity;
    }
}

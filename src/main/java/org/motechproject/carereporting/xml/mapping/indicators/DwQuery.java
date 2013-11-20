package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dw-query")
public class DwQuery {

    private Dimension dimension;

    private GroupBy groupBy;

    private WhereGroup whereGroup;

    private CombineWith combineWith;

    private List<SelectColumn> selectColumns;

    private List<OrderBy> orderBy;

    private Integer limit;

    @XmlElement
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @XmlElement(name = "group-by")
    public GroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    @XmlElement(name = "where-group")
    public WhereGroup getWhereGroup() {
        return whereGroup;
    }

    public void setWhereGroup(WhereGroup whereGroup) {
        this.whereGroup = whereGroup;
    }

    @XmlElementWrapper(name = "select-columns")
    @XmlElement(name = "select-column")
    public List<SelectColumn> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<SelectColumn> selectColumns) {
        this.selectColumns = selectColumns;
    }

    @XmlElement(name = "combineWith")
    public CombineWith getCombineWith() {
        return combineWith;
    }

    public void setCombineWith(CombineWith combineWith) {
        this.combineWith = combineWith;
    }

    @XmlElementWrapper(name = "order-by-group")
    @XmlElement(name = "order-by")
    public List<OrderBy> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<OrderBy> orderBy) {
        this.orderBy = orderBy;
    }

    @XmlElement(name = "limit")
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

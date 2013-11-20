package org.motechproject.carereporting.xml.mapping.indicators;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order-by")
public class OrderBy {

    private SelectColumn selectColumn;

    private OrderByType type;

    @XmlElement(name = "select-column")
    public SelectColumn getSelectColumn() {
        return selectColumn;
    }

    public void setSelectColumn(SelectColumn selectColumn) {
        this.selectColumn = selectColumn;
    }

    @XmlAttribute(name = "type")
    public OrderByType getType() {
        return type;
    }

    public void setType(OrderByType type) {
        this.type = type;
    }
}

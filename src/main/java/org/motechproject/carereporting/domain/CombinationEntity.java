package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "combination")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "combination_id"))
})
public class CombinationEntity extends AbstractEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "foreign_key")
    private String foreignKey;

    @Column(name = "referenced_key")
    private String referencedKey;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dw_query_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity dwQuery;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getReferencedKey() {
        return referencedKey;
    }

    public void setReferencedKey(String referencedKey) {
        this.referencedKey = referencedKey;
    }

    public DwQueryEntity getDwQuery() {
        return dwQuery;
    }

    public void setDwQuery(DwQueryEntity dwQuery) {
        this.dwQuery = dwQuery;
    }
}

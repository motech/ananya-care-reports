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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "combination")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "combination_id"))
})
public class CombinationEntity extends AbstractEntity implements Cloneable {

    @Column(name = "type")
    @JsonView({ BaseView.class })
    private String type;

    @Column(name = "foreign_key")
    @JsonView({ BaseView.class })
    private String foreignKey;

    @Column(name = "referenced_key")
    @JsonView({ BaseView.class })
    private String referencedKey;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dw_query_id", referencedColumnName = "dw_query_id")
    @JsonView({ QueryJsonView.EditForm.class })
    private DwQueryEntity dwQuery;

    public CombinationEntity() {
    }

    public CombinationEntity(CombinationEntity combination) {
        type = combination.getType();
        foreignKey = combination.getForeignKey();
        referencedKey = combination.getReferencedKey();
        dwQuery = combination.getDwQuery() != null ? new DwQueryEntity(combination.getDwQuery()) : null;
    }

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CombinationEntity combinationEntity = new CombinationEntity();

        combinationEntity.setDwQuery((DwQueryEntity) this.getDwQuery().clone());
        combinationEntity.setType(this.getType());
        combinationEntity.setReferencedKey(this.getReferencedKey());
        combinationEntity.setForeignKey(this.getForeignKey());

        return combinationEntity;
    }
}

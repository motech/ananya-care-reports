package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "complex_dw_query")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "complex_dw_query_id"))
})
public class ComplexDwQueryEntity extends DwQueryEntity {

    @Column(name = "dimension")
    private String dimension;

    @ManyToMany
    @JoinTable(name = "complex_dw_query_fact", joinColumns = { @JoinColumn(name = "complex_dw_query_id") },
            inverseJoinColumns = { @JoinColumn(name = "fact_id") })
    private Set<FactEntity> facts;

    @ManyToOne
    @JoinColumn(name = "union_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity union;

    @ManyToOne
    @JoinColumn(name = "intersection_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity intersection;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Set<FactEntity> getFacts() {
        return facts;
    }

    public void setFacts(Set<FactEntity> facts) {
        this.facts = facts;
    }

    public DwQueryEntity getUnion() {
        return union;
    }

    public void setUnion(DwQueryEntity union) {
        this.union = union;
    }

    public DwQueryEntity getIntersection() {
        return intersection;
    }

    public void setIntersection(DwQueryEntity intersection) {
        this.intersection = intersection;
    }
}

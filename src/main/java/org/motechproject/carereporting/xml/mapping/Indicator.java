package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
public class Indicator {

    private Boolean additive;

    private String name;

    private List<Category> categories;

    private Owners owners;

    private Area area;

    private Frequency defaultFrequency;

    private Query denominator;

    private Query numerator;

    private Union union;

    private Intersection intersection;

    private BigDecimal trend;

    private List<Report> reports;

    @XmlAttribute
    public Boolean getAdditive() {
        return additive;
    }

    public void setAdditive(Boolean additive) {
        this.additive = additive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "category")
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @XmlElement(name = "default-frequency")
    public Frequency getDefaultFrequency() {
        return defaultFrequency;
    }

    public void setDefaultFrequency(Frequency defaultFrequency) {
        this.defaultFrequency = defaultFrequency;
    }

    public Query getDenominator() {
        return denominator;
    }

    public void setDenominator(Query denominator) {
        this.denominator = denominator;
    }

    public Query getNumerator() {
        return numerator;
    }

    public void setNumerator(Query numerator) {
        this.numerator = numerator;
    }

    public Union getUnion() {
        return union;
    }

    public void setUnion(Union union) {
        this.union = union;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public BigDecimal getTrend() {
        return trend;
    }

    public void setTrend(BigDecimal trend) {
        this.trend = trend;
    }

    @XmlElementWrapper(name = "reports")
    @XmlElement(name = "report")
    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Owners getOwners() {
        return owners;
    }

    public void setOwners(Owners owners) {
        this.owners = owners;
    }
}

package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement
public class Indicator {

    private String name;

    private List<Category> categories;

    private User user;

    private List<Role> roles;

    private Area area;

    private Frequency defaultFrequency;

    private Denominator denominator;

    private Numerator numerator;

    private Union union;

    private Intersection intersection;

    private BigDecimal trend;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public Denominator getDenominator() {
        return denominator;
    }

    public void setDenominator(Denominator denominator) {
        this.denominator = denominator;
    }

    public Numerator getNumerator() {
        return numerator;
    }

    public void setNumerator(Numerator numerator) {
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
}

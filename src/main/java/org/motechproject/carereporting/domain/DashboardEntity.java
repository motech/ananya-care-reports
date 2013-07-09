package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "dashboard")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "dashboard_id"))
})
@NamedQueries({
        @NamedQuery(name = "dashboardEntity.getTabPositionForNewDashboard",
            query = "select max(tabPosition) + 1 from DashboardEntity dashboardEntity"
        ),
        @NamedQuery(name = "dashboardEntity.findDashboardByName",
            query = "from DashboardEntity d where d.name=:name"
        )
})
public class DashboardEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Column(name = "tab_position", unique = true)
    private Short tabPosition;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "dashboard_user", joinColumns = { @JoinColumn(name = "dashboard_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserEntity> owners;

    public DashboardEntity() {

    }

    public DashboardEntity(String name, Short tabPosition, Set<UserEntity> owners) {
        this.name = name;
        this.tabPosition = tabPosition;
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(Short tabPosition) {
        this.tabPosition = tabPosition;
    }

    @JsonIgnore
    public Set<UserEntity> getOwners() {
        return owners;
    }

    public void setOwners(Set<UserEntity> owners) {
        this.owners = owners;
    }
}

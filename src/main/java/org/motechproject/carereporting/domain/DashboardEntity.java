package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "dashboard")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "dashboard_id"))
})
public class DashboardEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "tab_position")
    private Short tabPosition;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "dashboard_user", joinColumns = { @JoinColumn(name = "indicator_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserEntity> owners;

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

    public Set<UserEntity> getOwners() {
        return owners;
    }

    public void setOwners(Set<UserEntity> owners) {
        this.owners = owners;
    }
}

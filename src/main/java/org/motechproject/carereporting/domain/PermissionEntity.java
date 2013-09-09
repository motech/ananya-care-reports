package org.motechproject.carereporting.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "permission")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "permission_id"))
})
public class PermissionEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles;

    public PermissionEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}

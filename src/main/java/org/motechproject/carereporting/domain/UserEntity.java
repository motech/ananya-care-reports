package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSetter;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "care_user")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id"))
})
public class UserEntity extends AbstractEntity implements UserDetails {

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "salt")
    private String salt;

    @ManyToMany(mappedBy = "owners")
    private Set<DashboardEntity> dashboards;

    @ManyToMany(mappedBy = "owners")
    private Set<IndicatorEntity> indicators;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "care_user_role", joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<RoleEntity> roles;

    public UserEntity() {
        this.salt = UUID.randomUUID().toString();
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
        this.salt = UUID.randomUUID().toString();
    }

    public UserEntity(String username, String password, Set<RoleEntity> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.salt = UUID.randomUUID().toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleEntity role: roles) {
            for (PermissionEntity permission: role.getPermissions()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return grantedAuthorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonIgnore
    public Set<DashboardEntity> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<DashboardEntity> dashboards) {
        this.dashboards = dashboards;
    }

    @JsonIgnore
    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

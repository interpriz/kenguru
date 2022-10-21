package com.kenguru.demowebapp.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String username;

    @Column(name = "password", unique = true)
    private String password;

    @Column(name = "active", unique = true)
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<UsersWords> uw;

    @OneToMany(mappedBy = "user")
    private Set<UsersIrregularVerbsScores> uivs;

    @OneToMany(mappedBy = "user")
    private Set<UsersComparativeAdjectivesScores> ucas;

    @OneToMany(mappedBy = "user")
    private Set<UsersPhrasalVerbsScores> phrasVerbsScores;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_groups",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_group") })
    private Set<Groups> groups;

    public Users() {
    }

    public Users(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Users(String username, Set<UsersWords> uwt, Set<Groups> groups) {
        this.username = username;
        this.uw = uwt;
        this.groups = groups;
    }

    public Users(String username, String password, boolean active, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Set<UsersWords> getUw() {
        return uw;
    }

    public void setUw(Set<UsersWords> uwt) {
        this.uw = uwt;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public Set<UsersIrregularVerbsScores> getUivs() {
        return uivs;
    }

    public void setUivs(Set<UsersIrregularVerbsScores> uivs) {
        this.uivs = uivs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<UsersComparativeAdjectivesScores> getUcas() {
        return ucas;
    }

    public void setUcas(Set<UsersComparativeAdjectivesScores> ucas) {
        this.ucas = ucas;
    }

    public Set<UsersPhrasalVerbsScores> getPhrasVerbsScores() {
        return phrasVerbsScores;
    }

    public void setPhrasVerbsScores(Set<UsersPhrasalVerbsScores> phrasVerbsScores) {
        this.phrasVerbsScores = phrasVerbsScores;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}

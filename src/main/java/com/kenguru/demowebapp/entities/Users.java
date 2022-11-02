package com.kenguru.demowebapp.entities;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Column(name = "name")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Email(message = "Email не верен")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    private String activationCode;

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

    @ManyToMany(fetch = FetchType.EAGER)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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

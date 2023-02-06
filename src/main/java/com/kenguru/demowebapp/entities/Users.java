package com.kenguru.demowebapp.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

import static com.kenguru.demowebapp.StaticStrings.*;

@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = MESSAGE_EMPTY_USER_NAME)
    @Column(name = "name")
    private String username;

    @NotBlank(message = MESSAGE_EMPTY_PASSWORD)
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Email(message = MESSAGE_ERROR_EMAIL)
    @NotBlank(message = MESSAGE_EMPTY_EMAIL)
    private String email;

    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<UsersWords> usersWords;

    @OneToMany(mappedBy = "user")
    private Set<UsersIrregularVerbsScores> usersIrrVerbsScores;

    @OneToMany(mappedBy = "user")
    private Set<UsersComparativeAdjectivesScores> usersCompAdjScores;

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
        this.usersWords = uwt;
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

    public Set<UsersWords> getUsersWords() {
        return usersWords;
    }

    public void setUsersWords(Set<UsersWords> usersWords) {
        this.usersWords = usersWords;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public Set<UsersIrregularVerbsScores> getUsersIrrVerbsScores() {
        return usersIrrVerbsScores;
    }

    public void setUsersIrrVerbsScores(Set<UsersIrregularVerbsScores> uivs) {
        this.usersIrrVerbsScores = uivs;
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

    public Set<UsersComparativeAdjectivesScores> getUsersCompAdjScores() {
        return usersCompAdjScores;
    }

    public void setUsersCompAdjScores(Set<UsersComparativeAdjectivesScores> usersCompAdjScores) {
        this.usersCompAdjScores = usersCompAdjScores;
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

package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Users_words> uw;

    @OneToMany(mappedBy = "user")
    private Set<Users_irregular_verbs_scores> uivs;

    @OneToMany(mappedBy = "user")
    private Set<Users_comparative_adjectives_scores> ucas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_groups",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_group") })
    private Set<Groups> groups;

    public Users() {
    }

    public Users(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Users(String name, Set<Users_words> uwt, Set<Groups> groups) {
        this.name = name;
        this.uw = uwt;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Users_words> getUw() {
        return uw;
    }

    public void setUw(Set<Users_words> uwt) {
        this.uw = uwt;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public Set<Users_irregular_verbs_scores> getUivs() {
        return uivs;
    }

    public void setUivs(Set<Users_irregular_verbs_scores> uivs) {
        this.uivs = uivs;
    }
}

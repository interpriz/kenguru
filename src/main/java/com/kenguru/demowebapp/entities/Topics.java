package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topics")
public class Topics {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "topics",fetch = FetchType.LAZY)
    private Set<UsersWords> uw;

    public Topics() {
    }

    public Topics(String name, String description, Set<UsersWords> uwt) {
        this.name = name;
        this.description = description;
        this.uw = uwt;
    }

    public Topics(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UsersWords> getUw() {
        return uw;
    }

    public void setUw(Set<UsersWords> uwt) {
        this.uw = uwt;
    }
}

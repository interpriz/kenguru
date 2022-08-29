package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "words_translations")
public class Words_translations {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "translations",fetch = FetchType.LAZY)
    private Set<Users_words> uw;

    public Words_translations() {
    }

    public Words_translations(String name) {
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

    public Set<Users_words> getUw() {
        return uw;
    }

    public void setUw(Set<Users_words> uw) {
        this.uw = uw;
    }
}

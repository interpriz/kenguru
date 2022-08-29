package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "phrasal_verbs_translations")
public class Phrasal_verbs_translations {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "translations",fetch = FetchType.EAGER)
    private Set<Users_phrasal_verbs_scores> upv;

    public Phrasal_verbs_translations() {
    }

    public Phrasal_verbs_translations(String name) {
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

    public Set<Users_phrasal_verbs_scores> getUpv() {
        return upv;
    }

    public void setUpv(Set<Users_phrasal_verbs_scores> upv) {
        this.upv = upv;
    }
}

package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "words_translations")
public class WordsTranslations {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "translations",fetch = FetchType.LAZY)
    private Set<UsersWords> usersWords;

    public WordsTranslations() {
    }

    public WordsTranslations(String name) {
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

    public Set<UsersWords> getUsersWords() {
        return usersWords;
    }

    public void setUsersWords(Set<UsersWords> uw) {
        this.usersWords = uw;
    }
}

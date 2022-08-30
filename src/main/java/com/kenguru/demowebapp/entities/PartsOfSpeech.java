package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parts_of_speech")
public class PartsOfSpeech {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "partOfSpeech")
    private Set<WordsPartOfSpeech> words;

    public PartsOfSpeech() {
    }

    public PartsOfSpeech(String name) {
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

    public Set<WordsPartOfSpeech> getWords() {
        return words;
    }

    public void setWords(Set<WordsPartOfSpeech> words) {
        this.words = words;
    }
}

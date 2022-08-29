package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parts_of_speech")
public class Parts_of_speech {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "partOfSpeech")
    private Set<Words_part_of_speech> words;

    public Parts_of_speech() {
    }

    public Parts_of_speech(String name) {
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

    public Set<Words_part_of_speech> getWords() {
        return words;
    }

    public void setWords(Set<Words_part_of_speech> words) {
        this.words = words;
    }
}

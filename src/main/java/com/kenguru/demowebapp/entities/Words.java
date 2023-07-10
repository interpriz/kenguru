package com.kenguru.demowebapp.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "words")
public class Words {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "transcription")
    private String transcription;

    @OneToMany(mappedBy = "word")
    private Set<WordsPartOfSpeech> partsOfSpeeches;



    public Words() {
    }

    public Words(String name, String transcription) {
        this.name = name;
        this.transcription = transcription;
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

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public Set<WordsPartOfSpeech> getPartsOfSpeeches() {
        return partsOfSpeeches;
    }

    public void setPartsOfSpeeches(Set<WordsPartOfSpeech> partOfSpeeches) {
        this.partsOfSpeeches = partOfSpeeches;
    }
}

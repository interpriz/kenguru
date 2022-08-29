package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "words_part_of_speech")
public class Words_part_of_speech {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_word", nullable = false)
    private Words word;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_part_sp", nullable = false)
    private Parts_of_speech partOfSpeech;

    @OneToMany(mappedBy = "wps")
    private Set<Users_words> uw;

    @OneToOne(mappedBy = "wps")
    private Irregular_verbs irr_verb;

    public Words_part_of_speech() {
    }

    public Words_part_of_speech(Words word, Parts_of_speech partOfSpeech) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Words getWord() {
        return word;
    }

    public void setWord(Words word) {
        this.word = word;
    }

    public Parts_of_speech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(Parts_of_speech partsOfSpeech) {
        this.partOfSpeech = partsOfSpeech;
    }

    public Set<Users_words> getUw() {
        return uw;
    }

    public void setUw(Set<Users_words> uwt) {
        this.uw = uwt;
    }

    public Irregular_verbs getIrr_verb() {
        return irr_verb;
    }

    public void setIrr_verb(Irregular_verbs irr_verb) {
        this.irr_verb = irr_verb;
    }
}

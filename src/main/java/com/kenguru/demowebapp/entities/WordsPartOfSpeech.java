package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "words_part_of_speech")
public class WordsPartOfSpeech {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_word", nullable = false)
    private Words word;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_part_sp", nullable = false)
    private PartsOfSpeech partOfSpeech;

    @OneToMany(mappedBy = "wps")
    private Set<UsersWords> uw;

    @OneToOne(mappedBy = "wps")
    private IrregularVerbs irr_verb;

    public WordsPartOfSpeech() {
    }

    public WordsPartOfSpeech(Words word, PartsOfSpeech partOfSpeech) {
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

    public PartsOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartsOfSpeech partsOfSpeech) {
        this.partOfSpeech = partsOfSpeech;
    }

    public Set<UsersWords> getUw() {
        return uw;
    }

    public void setUw(Set<UsersWords> uwt) {
        this.uw = uwt;
    }

    public IrregularVerbs getIrr_verb() {
        return irr_verb;
    }

    public void setIrr_verb(IrregularVerbs irr_verb) {
        this.irr_verb = irr_verb;
    }
}

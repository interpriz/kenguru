package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "phrasal_verbs")
public class PhrasalVerbs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preposition")
    private String preposition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private WordsPartOfSpeech wps;

    @OneToMany(mappedBy = "phrasalVerb")
    private Set<UsersPhrasalVerbsScores> usersScores;

    public PhrasalVerbs() {
    }

    public PhrasalVerbs(String preposition, WordsPartOfSpeech wps) {
        this.preposition = preposition;
        this.wps = wps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public WordsPartOfSpeech getWps() {
        return wps;
    }

    public void setWps(WordsPartOfSpeech wps) {
        this.wps = wps;
    }

    public Set<UsersPhrasalVerbsScores> getUsersScores() {
        return usersScores;
    }

    public void setUsersScores(Set<UsersPhrasalVerbsScores> upvt) {
        this.usersScores = upvt;
    }
}

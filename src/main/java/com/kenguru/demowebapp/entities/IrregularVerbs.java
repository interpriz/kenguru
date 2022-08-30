package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "irregular_verbs")
public class IrregularVerbs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "second_form")
    private String second_form;

    @Column(name = "third_form")
    private String third_form;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private WordsPartOfSpeech wps;

    @OneToMany(mappedBy = "irregularVerb")
    private Set<UsersIrregularVerbsScores> usersIrregularVerbs;

    public IrregularVerbs() {
    }

    public IrregularVerbs(String second_form, String third_form, WordsPartOfSpeech wps) {
        this.second_form = second_form;
        this.third_form = third_form;
        this.wps = wps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecond_form() {
        return second_form;
    }

    public void setSecond_form(String second_form) {
        this.second_form = second_form;
    }

    public String getThird_form() {
        return third_form;
    }

    public void setThird_form(String third_form) {
        this.third_form = third_form;
    }

    public WordsPartOfSpeech getWps() {
        return wps;
    }

    public void setWps(WordsPartOfSpeech wps) {
        this.wps = wps;
    }

    public Set<UsersIrregularVerbsScores> getUsersIrregularVerbs() {
        return usersIrregularVerbs;
    }

    public void setUsersIrregularVerbs(Set<UsersIrregularVerbsScores> usersIrregularVerbs) {
        this.usersIrregularVerbs = usersIrregularVerbs;
    }
}

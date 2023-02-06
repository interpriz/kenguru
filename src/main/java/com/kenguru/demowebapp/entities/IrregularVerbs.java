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
    private String secondForm;

    @Column(name = "third_form")
    private String thirdForm;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private WordsPartOfSpeech wps;

    @OneToMany(mappedBy = "irregularVerb")
    private Set<UsersIrregularVerbsScores> usersIrregularVerbs;

    public IrregularVerbs() {
    }

    public IrregularVerbs(String second_form, String thirdForm, WordsPartOfSpeech wps) {
        this.secondForm = second_form;
        this.thirdForm = thirdForm;
        this.wps = wps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecondForm() {
        return secondForm;
    }

    public void setSecondForm(String secondForm) {
        this.secondForm = secondForm;
    }

    public String getThirdForm() {
        return thirdForm;
    }

    public void setThirdForm(String thirdForm) {
        this.thirdForm = thirdForm;
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

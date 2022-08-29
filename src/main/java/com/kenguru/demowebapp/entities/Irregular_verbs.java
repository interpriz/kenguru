package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "irregular_verbs")
public class Irregular_verbs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "second_form")
    private String second_form;

    @Column(name = "third_form")
    private String third_form;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private Words_part_of_speech wps;

    @OneToMany(mappedBy = "irregularVerb")
    private Set<Users_irregular_verbs_scores> usersIrregularVerbs;

    public Irregular_verbs() {
    }

    public Irregular_verbs(String second_form, String third_form, Words_part_of_speech wps) {
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

    public Words_part_of_speech getWps() {
        return wps;
    }

    public void setWps(Words_part_of_speech wps) {
        this.wps = wps;
    }

    public Set<Users_irregular_verbs_scores> getUsersIrregularVerbs() {
        return usersIrregularVerbs;
    }

    public void setUsersIrregularVerbs(Set<Users_irregular_verbs_scores> usersIrregularVerbs) {
        this.usersIrregularVerbs = usersIrregularVerbs;
    }
}

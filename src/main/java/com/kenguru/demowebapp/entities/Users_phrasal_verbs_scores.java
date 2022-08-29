package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_phrasal_verbs_scores")
public class Users_phrasal_verbs_scores {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private int score;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pv", nullable = false)
    private Phrasal_verbs phrasalVerb;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_phrasal_verbs_translations",
            joinColumns = { @JoinColumn(name = "id_pv") },
            inverseJoinColumns = { @JoinColumn(name = "id_translation") })
    private Set<Phrasal_verbs_translations> translations;

    public Users_phrasal_verbs_scores() {
    }

    public Users_phrasal_verbs_scores(int score, String description, Phrasal_verbs phrasalVerb, Users user) {
        this.score = score;
        this.description = description;
        this.phrasalVerb = phrasalVerb;
        this.user = user;
        this.translations = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Phrasal_verbs getPhrasalVerb() {
        return phrasalVerb;
    }

    public void setPhrasalVerb(Phrasal_verbs phrasalVerb) {
        this.phrasalVerb = phrasalVerb;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<Phrasal_verbs_translations> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Phrasal_verbs_translations> translations) {
        this.translations = translations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users_phrasal_verbs_scores")
public class UsersPhrasalVerbsScores {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private int score;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pv", nullable = false)
    private PhrasalVerbs phrasalVerb;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_phrasal_verbs_translations",
            joinColumns = { @JoinColumn(name = "id_pv") },
            inverseJoinColumns = { @JoinColumn(name = "id_translation") })
    private Set<PhrasalVerbsTranslations> translations;

    public UsersPhrasalVerbsScores() {
    }

    public UsersPhrasalVerbsScores(int score, String description, PhrasalVerbs phrasalVerb, Users user) {
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

    public PhrasalVerbs getPhrasalVerb() {
        return phrasalVerb;
    }

    public void setPhrasalVerb(PhrasalVerbs phrasalVerb) {
        this.phrasalVerb = phrasalVerb;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<PhrasalVerbsTranslations> getTranslations() {
        return translations;
    }
    public List<String> getListStringTranslations() {
        List<String> translationsList = new ArrayList<>();
        for(PhrasalVerbsTranslations trans : this.translations){
            translationsList.add(trans.getName());
        }
        return new ArrayList<>();
    }

    public void setTranslations(Set<PhrasalVerbsTranslations> translations) {
        this.translations = translations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addNewTranslation(PhrasalVerbsTranslations newTranslation){
        if(!translations.contains((newTranslation))){
            translations.add(newTranslation);
        }
    }
}

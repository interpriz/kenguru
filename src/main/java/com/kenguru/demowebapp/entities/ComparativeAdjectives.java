package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "comparative_adjectives")
public class ComparativeAdjectives {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comparative")
    private String comparative;

    @Column(name = "superlative")
    private String superlative;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private WordsPartOfSpeech wps;

    @OneToMany(mappedBy = "comparativeAdjective")
    private Set<UsersComparativeAdjectivesScores> usersComparativeAdjectives;

    public ComparativeAdjectives() {
    }

    public ComparativeAdjectives(String comparative, String superlative, WordsPartOfSpeech wps) {
        this.comparative = comparative;
        this.superlative = superlative;
        this.wps = wps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComparative() {
        return comparative;
    }

    public void setComparative(String comparative) {
        this.comparative = comparative;
    }

    public String getSuperlative() {
        return superlative;
    }

    public void setSuperlative(String superlative) {
        this.superlative = superlative;
    }

    public WordsPartOfSpeech getWps() {
        return wps;
    }

    public void setWps(WordsPartOfSpeech wps) {
        this.wps = wps;
    }

    public Set<UsersComparativeAdjectivesScores> getUsersComparativeAdjectives() {
        return usersComparativeAdjectives;
    }

    public void setUsersComparativeAdjectives(Set<UsersComparativeAdjectivesScores> usersComparativeAdjectives) {
        this.usersComparativeAdjectives = usersComparativeAdjectives;
    }
}

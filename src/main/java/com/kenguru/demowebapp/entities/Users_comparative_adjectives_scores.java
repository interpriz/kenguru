package com.kenguru.demowebapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_comparative_adjectives_scores")
public class Users_comparative_adjectives_scores {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private int score;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ca", nullable = false)
    private Comparative_adjectives comparativeAdjective;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    public Users_comparative_adjectives_scores() {
    }

    public Users_comparative_adjectives_scores(int score, Comparative_adjectives comparativeAdjective, Users user) {
        this.score = score;
        this.comparativeAdjective = comparativeAdjective;
        this.user = user;
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

    public Comparative_adjectives getComparativeAdjective() {
        return comparativeAdjective;
    }

    public void setComparativeAdjective(Comparative_adjectives comparativeAdjective) {
        this.comparativeAdjective = comparativeAdjective;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

package com.kenguru.demowebapp.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_irregular_verbs_scores")
public class Users_irregular_verbs_scores {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private int score;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_iv", nullable = false)
    private Irregular_verbs irregularVerb;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;



    public Users_irregular_verbs_scores() {
    }

    public Users_irregular_verbs_scores(int score, Irregular_verbs iv, Users user) {
        this.score = score;
        this.irregularVerb = iv;
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

    public Irregular_verbs getIrregularVerb() {
        return irregularVerb;
    }

    public void setIrregularVerb(Irregular_verbs irregularVerb) {
        this.irregularVerb = irregularVerb;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

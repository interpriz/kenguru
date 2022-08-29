package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "phrasal_verbs")
public class Phrasal_verbs {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preposition")
    private String preposition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private Words_part_of_speech wps;

    @OneToMany(mappedBy = "phrasalVerb")
    private Set<Users_phrasal_verbs_scores> upvt;

    public Phrasal_verbs() {
    }

    public Phrasal_verbs(String preposition, Words_part_of_speech wps) {
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

    public Words_part_of_speech getWps() {
        return wps;
    }

    public void setWps(Words_part_of_speech wps) {
        this.wps = wps;
    }

    public Set<Users_phrasal_verbs_scores> getUpvt() {
        return upvt;
    }

    public void setUpvt(Set<Users_phrasal_verbs_scores> upvt) {
        this.upvt = upvt;
    }
}

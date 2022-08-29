package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "comparative_adjectives")
public class Comparative_adjectives {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comparative")
    private String comparative;

    @Column(name = "superlative")
    private String superlative;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private Words_part_of_speech wps;

    @OneToMany(mappedBy = "comparativeAdjective")
    private Set<Users_comparative_adjectives_scores> usersComparativeAdjectives;

    public Comparative_adjectives() {
    }

    public Comparative_adjectives(String comparative, String superlative, Words_part_of_speech wps) {
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

    public Words_part_of_speech getWps() {
        return wps;
    }

    public void setWps(Words_part_of_speech wps) {
        this.wps = wps;
    }

    public Set<Users_comparative_adjectives_scores> getUsersComparativeAdjectives() {
        return usersComparativeAdjectives;
    }

    public void setUsersComparativeAdjectives(Set<Users_comparative_adjectives_scores> usersComparativeAdjectives) {
        this.usersComparativeAdjectives = usersComparativeAdjectives;
    }
}

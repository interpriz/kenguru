package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_words")
public class UsersWords {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private int score;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wps", nullable = false)
    private WordsPartOfSpeech wps;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "words_topics",
            joinColumns = { @JoinColumn(name = "id_uw") },
            inverseJoinColumns = { @JoinColumn(name = "id_topic") })
    private Set<Topics> topics;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_words_translations",
            joinColumns = { @JoinColumn(name = "id_uw") },
            inverseJoinColumns = { @JoinColumn(name = "id_translation") })
    private Set<WordsTranslations> translations;

    public UsersWords() {
    }

    public UsersWords(String translation, int score, WordsPartOfSpeech wps, Users user, Set<Topics> topics) {
        this.score = score;
        this.wps = wps;
        this.user = user;
        this.topics = topics;
        this.translations = new HashSet<>();
    }

    public UsersWords(int score, WordsPartOfSpeech wps, Users user) {
        this.score = score;
        this.wps = wps;
        this.user = user;
        this.topics = new HashSet<>();
        this.translations = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public WordsPartOfSpeech getWps() {
        return wps;
    }

    public void setWps(WordsPartOfSpeech wps) {
        this.wps = wps;
    }

    public Set<Topics> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topics> topics) {
        this.topics = topics;
    }

    public Set<WordsTranslations> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<WordsTranslations> translations) {
        this.translations = translations;
    }
}

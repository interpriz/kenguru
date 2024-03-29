package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public List<String> getListStringTopics() {
        List<String> topicsList = new ArrayList<>();
        for(Topics topic : this.topics){
            topicsList.add(topic.getName());
        }
        return new ArrayList<>();
    }

    public void setTopics(Set<Topics> topics) {
        this.topics = topics;
    }

    public Set<WordsTranslations> getTranslations() {
        return translations;
    }

    public List<String> getListStringTranslations() {
        List<String> translationsList = new ArrayList<>();
        for(WordsTranslations trans : this.translations){
            translationsList.add(trans.getName());
        }
        return new ArrayList<>();
    }




    public void setTranslations(Set<WordsTranslations> translations) {
        this.translations = translations;
    }



    public void addNewTranslation(WordsTranslations newTranslation){
        if(!translations.contains((newTranslation))){
            translations.add(newTranslation);
        }
    }

    public void addNewTopic(Topics newTopic){
        if(!topics.contains(newTopic)){
            topics.add(newTopic);
        }
    }


}

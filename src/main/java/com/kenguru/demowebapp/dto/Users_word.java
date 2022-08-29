package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Users_word {

    private Long id;

    private int score;

    private String word;

    private String partOfSpeech;

    private List<String> topics;

    private List<String> translations;

    public Users_word() {
    }

    public Users_word(Users_words usersWord) {
        this.id = usersWord.getId();
        this.score = usersWord.getScore();
        this.word = usersWord.getWps().getWord().getName();
        this.partOfSpeech = usersWord.getWps().getPartOfSpeech().getName();
        this.topics = new ArrayList<>();
        for(Topics top : usersWord.getTopics()){
            this.topics.add(top.getName());
        }
        this.translations = new ArrayList<>();
        for(Words_translations trans : usersWord.getTranslations()){
            this.translations.add(trans.getName());
        }
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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }
}

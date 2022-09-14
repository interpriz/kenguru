package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.*;

import java.util.ArrayList;
import java.util.List;

public class UsersWord {

    private Long id;

    private int score;

    private String word;

    private String transcription;

    private String partOfSpeech;

    private List<String> topics;

    private List<String> translations;

    public UsersWord() {
    }

    public UsersWord(UsersWords usersWord) {
        this.id = usersWord.getId();
        this.score = usersWord.getScore();
        this.word = usersWord.getWps().getWord().getName();
        this.transcription = usersWord.getWps().getWord().getTranscription();
        this.partOfSpeech = usersWord.getWps().getPartOfSpeech().getName();
        this.topics = new ArrayList<>();
        for(Topics top : usersWord.getTopics()){
            this.topics.add(top.getName());
        }
        this.translations = new ArrayList<>();
        for(WordsTranslations trans : usersWord.getTranslations()){
            this.translations.add(trans.getName());
        }
    }


    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
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

package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.PhrasalVerbsTranslations;
import com.kenguru.demowebapp.entities.UsersPhrasalVerbsScores;
import com.kenguru.demowebapp.entities.WordsTranslations;

import java.util.ArrayList;
import java.util.List;

public class UsersPhrasalVerb {

    private Long id;

    private int score;

    private String word;

    private String preposition;

    private String transcription;

    private List<String> translations;

    private String Description;

    public UsersPhrasalVerb(UsersPhrasalVerbsScores UsersPhrasalVerb) {
        this.id = UsersPhrasalVerb.getId();
        this.score = UsersPhrasalVerb.getScore();
        this.word = UsersPhrasalVerb.getPhrasalVerb().getWps().getWord().getName();
        this.preposition = UsersPhrasalVerb.getPhrasalVerb().getPreposition();
        this.transcription = UsersPhrasalVerb.getPhrasalVerb().getWps().getWord().getTranscription();
        this.translations = new ArrayList<>();
        for(PhrasalVerbsTranslations trans : UsersPhrasalVerb.getTranslations()){
            this.translations.add(trans.getName());
        }
        Description = UsersPhrasalVerb.getDescription();
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

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

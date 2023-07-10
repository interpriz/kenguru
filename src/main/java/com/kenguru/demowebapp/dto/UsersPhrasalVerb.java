package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.PhrasalVerbsTranslations;
import com.kenguru.demowebapp.entities.UsersPhrasalVerbsScores;

import java.util.List;
import java.util.stream.Collectors;

public class UsersPhrasalVerb {

    private Long id;

    private int score;

    private String word;

    private String preposition;

    private String transcription;

    private List<String> translations;

    private String description;

    public UsersPhrasalVerb(UsersPhrasalVerbsScores usersPhrasalVerb) {
        this.id = usersPhrasalVerb.getId();
        this.score = usersPhrasalVerb.getScore();
        this.word = usersPhrasalVerb.getPhrasalVerb()
                .getWps()
                .getWord()
                .getName();
        this.preposition = usersPhrasalVerb.getPhrasalVerb()
                .getPreposition();
        this.transcription = usersPhrasalVerb.getPhrasalVerb()
                .getWps()
                .getWord()
                .getTranscription();
        /*this.translations = new ArrayList<>();
        for(PhrasalVerbsTranslations trans : usersPhrasalVerb.getTranslations()){
            this.translations.add(trans.getName());
        }*/
        this.translations = usersPhrasalVerb.getTranslations()
                .stream()
                .map(PhrasalVerbsTranslations::getName)
                .collect(Collectors.toList());

        description = usersPhrasalVerb.getDescription();
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


public class UsersWord {

    private Long id;

    private int score;

    @NotBlank(message = "Пожалуйста введите слово")
    @Length(max = 100, message = "Слово слишком длинное")
    private String wordName;

    @NotBlank(message = "Пожалуйста введите транскрипцию")
    @Length(max = 100, message = "Транскрипция слишком длинная")
    private String transcription;

    @NotBlank(message = "Пожалуйста выберите часть речи")
    private String partOfSpeech;

    private List<String> topics;

    @NotEmpty(message = "Пожалуйста добавьте перевод")
    private List<String> translations;

    public UsersWord() {
    }

    public UsersWord(UsersWords usersWord) {
        this.id = usersWord.getId();
        this.score = usersWord.getScore();
        this.wordName = usersWord.getWps().getWord().getName();
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

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getTopics() {
        if (topics != null)
            return topics;
        else return new ArrayList<>();

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

package com.kenguru.demowebapp.dto;

import com.kenguru.demowebapp.entities.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kenguru.demowebapp.StaticStrings.*;


public class UsersWord {

    private Long id;

    private int score;

    @NotBlank(message = MESSAGE_ENTER_WORD)
    //@Length(max = 100, message = MESSAGE_LONG_WORD)
    @Pattern(regexp = "^[a-zA-Z]{1,100}$", message = MESSAGE_ONLY_ENGLISH_LETTERS)
    private String wordName;

    @NotBlank(message = MESSAGE_ENTER_TRANSCRIPTION)
    @Length(max = 100, message = MESSAGE_LONG_TRANSCRIPTION)
    private String transcription;

    @NotBlank(message = MESSAGE_CHOOSE_PART_OF_SPEECH)
    private String partOfSpeech;

    private List<String> topics;

    @NotEmpty(message = MESSAGE_ADD_TRANSLATION)
    private List<String> translations;

    public UsersWord() {
    }

    public UsersWord(UsersWords usersWord) {
        this.id = usersWord.getId();
        this.score = usersWord.getScore();
        this.wordName = usersWord.getWps()
                .getWord()
                .getName();
        this.transcription = usersWord.getWps()
                .getWord()
                .getTranscription();
        this.partOfSpeech = usersWord.getWps()
                .getPartOfSpeech()
                .getName();
        /*this.topics = new ArrayList<>();
        for (Topics top : usersWord.getTopics()) {
            this.topics.add(top.getName());
        }*/
        this.topics = usersWord.getTopics()
                .stream()
                .map(Topics::getName)
                .collect(Collectors.toList());
        /*this.translations = new ArrayList<>();
        for (WordsTranslations trans : usersWord.getTranslations()) {
            this.translations.add(trans.getName());
        }*/
        this.translations = usersWord.getTranslations()
                .stream()
                .map(WordsTranslations::getName)
                .collect(Collectors.toList());
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

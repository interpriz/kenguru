package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UsersWordsService {

    @Autowired
    private WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;

    @Autowired
    private UsersWordsRepository usersWordsRepository;

    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private PartsOfSpeechRepository partsOfSpeechRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository;

    @Autowired
    private IrregularVerbsRepository irregularVerbsRepository;

    @Autowired
    private UsersComparativeAdjectivesScoresRepository usersComparativeAdjectivesScoresRepository;

    @Autowired
    private ComparativeAdjectivesRepository comparativeAdjectivesRepository;

    @Autowired
    private UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;

    @Autowired
    private WordsTranslationsRepository wordsTranslationsRepository;

    @Autowired
    private PhrasalVerbsRepository phrasalVerbsRepository;

    @Autowired
    private PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;




    public Words saveOrGetWord(String wordName, String transcription){
        Words word;
        List<Words> words= wordsRepository.findWordsByName(wordName);
        if(words.size()==0)
        {
            word = new Words(wordName,transcription);
            wordsRepository.save(word);
        }else{
            word = words.get(0);
        }
        return word;
    }

    public WordsPartOfSpeech saveOrGetWPS(PartsOfSpeech pos, Words word){
        WordsPartOfSpeech wps;
        List<WordsPartOfSpeech> wpsList =  wordsPartsOfSpeechRepository.findWordsPartOfSpeechByPartOfSpeechAndWord(pos,word);
        if(wpsList.size()==0)
        {
            wps = new WordsPartOfSpeech(word,pos);
            wordsPartsOfSpeechRepository.save(wps);
        }else{
            wps = wpsList.get(0);
        }
        return wps;
    }

    public UsersWords saveOrGetUsersWord(WordsPartOfSpeech wps, Users usr){
        UsersWords uw;
        List<UsersWords> uwList = usersWordsRepository.findUsersWordsByUserAndWps(usr,wps);
        if(uwList.size()==0)
        {
            uw = new UsersWords(0,wps,usr);
            usersWordsRepository.save(uw);
        }else{
            uw = uwList.get(0);
        }
        return uw;
    }

    public void saveNewWordTranslation(String newTranslation, UsersWords usersWord){
        if(!newTranslation.equals(""))
        {
            WordsTranslations wordTranslation;
            List<WordsTranslations> translationsList = wordsTranslationsRepository.findWordsTranslationsByName(newTranslation);
            if(translationsList.size()==0)
            {
                wordTranslation = new WordsTranslations(newTranslation);
                wordsTranslationsRepository.save(wordTranslation);
            }else{
                wordTranslation = translationsList.get(0);
            }

            Set<WordsTranslations> uwTranslations = usersWord.getTranslations();
            if(!uwTranslations.contains((wordTranslation))){
                uwTranslations.add(wordTranslation);
                usersWord.setTranslations(uwTranslations);
                usersWordsRepository.save(usersWord);
            }
        }
    }

    public void saveNewTopic(String newTopic, UsersWords usersWord){
        if(!newTopic.equals(""))
        {
            Topics topic;
            List<Topics> topicList = topicsRepository.findTopicsByName(newTopic);
            if(topicList.size()==0)
            {
                topic = new Topics(newTopic);
                topicsRepository.save(topic);
            }else{
                topic = topicList.get(0);
            }

            Set<Topics> uwtTopics = usersWord.getTopics();
            if(!uwtTopics.contains(topic)){
                uwtTopics.add(topic);
                usersWord.setTopics(uwtTopics);
                usersWordsRepository.save(usersWord);
            }
        }
    }

    public void saveNewUsersWord(
            String partOfSpeech,
            String wordName,
            String transcription,
            String translation,
            String topicName) {
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = saveOrGetWPS(pos, word);

        UsersWords uw = saveOrGetUsersWord(wps, usr);

        //сохранение нового перевода
        saveNewWordTranslation(translation, uw);

        //сохранение новой темы
        saveNewTopic(topicName, uw);

    }

    public void editOldUsersWord(
            Long userWordId,
            String wordName,
            String partOfSpeech,
            String transcription,
            List<String> newTranslations,
            List<String> newTopics
    ){
        UsersWords oldUsersWord = usersWordsRepository.findUsersWordsById(userWordId);
        UsersWord oldUsersWordDTO = new UsersWord(oldUsersWord);

        boolean newWordName = !oldUsersWordDTO.getWord().equals(wordName);
        boolean newWordPartOfSpeech = !oldUsersWordDTO.getPartOfSpeech().equals(partOfSpeech);
        boolean newWordTranscription = !oldUsersWordDTO.getTranscription().equals(transcription);

        //если слово изменило написание или транскрипцию
        if (newWordName || newWordTranscription) {
            //создать новое слово и привязать его к пользователю
            Words newWord = new Words(wordName, transcription);
            oldUsersWord.getWps().setWord(newWord);
        }

        //если слово изменило часть речи
        if (newWordPartOfSpeech) {
            //добавить новое сочетание слова и части речи и привязать его к пользователю
            PartsOfSpeech newPartOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
            WordsPartOfSpeech newWPS = new WordsPartOfSpeech(oldUsersWord.getWps().getWord(),newPartOfSpeech);
            oldUsersWord.setWps(newWPS);

        }

        //удаление старых переводов
        oldUsersWord.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans.getName()));
        oldUsersWordDTO.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans));

        //добавление новых переводов
        if (newTranslations.size() != 0)
            for (String newTranslation : newTranslations) {
                if (!oldUsersWordDTO.getTranslations().contains(newTranslation)) {
                    //добавить новый перевод
                    saveNewWordTranslation(newTranslation, oldUsersWord);
                }
            }

        //удаление старых тем
        oldUsersWord.getTopics().removeIf(oldTopic -> !newTopics.contains(oldTopic.getName()));
        oldUsersWordDTO.getTopics().removeIf(oldTopic -> !newTopics.contains(oldTopic));


        //добавление новых тем
        if (newTopics.size() != 0)
            for (String newTopic : newTopics) {
                if (!oldUsersWordDTO.getTopics().contains(newTopic)) {
                    //добавить новую тему
                    saveNewTopic(newTopic, oldUsersWord);
                }
            }

        usersWordsRepository.save(oldUsersWord);
    }

    public List<PartsOfSpeech> getAllPartsOfSpeech(){
        return partsOfSpeechRepository.findAll();
    }

    public List<Topics> getAllUsersTopics(){
        Users usr  = usersRepository.getById(1L);
        return topicsRepository.findDistinctTopicsByUwIn(usersWordsRepository.findByUser(usr));
    }

    public UsersWords getUsersWord(Long userWordId){
        return usersWordsRepository.findUsersWordsById(userWordId);
    }

}

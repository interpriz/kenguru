package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersWordsService {


    private WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;
    private UsersWordsRepository usersWordsRepository;
    private WordsRepository wordsRepository;
    private PartsOfSpeechRepository partsOfSpeechRepository;
    private UsersRepository usersRepository;
    private TopicsRepository topicsRepository;
    private WordsTranslationsRepository wordsTranslationsRepository;

    public UsersWordsService(WordsPartOfSpeechRepository wordsPartsOfSpeechRepository,
                             UsersWordsRepository usersWordsRepository,
                             WordsRepository wordsRepository,
                             PartsOfSpeechRepository partsOfSpeechRepository,
                             UsersRepository usersRepository,
                             TopicsRepository topicsRepository,
                             WordsTranslationsRepository wordsTranslationsRepository) {
        this.wordsPartsOfSpeechRepository = wordsPartsOfSpeechRepository;
        this.usersWordsRepository = usersWordsRepository;
        this.wordsRepository = wordsRepository;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersRepository = usersRepository;
        this.topicsRepository = topicsRepository;
        this.wordsTranslationsRepository = wordsTranslationsRepository;
    }

    public Words saveOrGetWord(String wordName, String transcription){
        Words word = wordsRepository.findByName(wordName);
        if(word==null)
        {
            word = new Words(wordName,transcription);
            wordsRepository.save(word);
        }
        return word;
    }

    public WordsPartOfSpeech saveOrGetWPS(PartsOfSpeech pos, Words word){
        WordsPartOfSpeech wps = wordsPartsOfSpeechRepository.findByPartOfSpeechAndWord(pos,word);
        if(wps==null)
        {
            wps = new WordsPartOfSpeech(word,pos);
            wordsPartsOfSpeechRepository.save(wps);
        }
        return wps;
    }

    public UsersWords saveOrGetUsersWord(WordsPartOfSpeech wps, Users usr){
        UsersWords userWord = usersWordsRepository.findByUserAndWps(usr,wps);
        if(userWord==null)
        {
            userWord = new UsersWords(0,wps,usr);
            usersWordsRepository.save(userWord);
        }
        return userWord;
    }

    public void addNewUsersWordTranslation(String newTranslation, UsersWords usersWord){
        if(!newTranslation.isBlank())
        {
            WordsTranslations newWordTranslation = wordsTranslationsRepository.findByName(newTranslation);
            if(newWordTranslation==null)
            {
                newWordTranslation = new WordsTranslations(newTranslation);
                wordsTranslationsRepository.save(newWordTranslation);
            }

            usersWord.addNewTranslation(newWordTranslation);
        }
    }

    public void addNewUsersWordTopic(String newTopic, UsersWords usersWord){
        if(!newTopic.isBlank())
        {
            Topics newUsersWordTopic = topicsRepository.findByName(newTopic);
            if(newUsersWordTopic==null)
            {
                newUsersWordTopic = new Topics(newTopic);
                topicsRepository.save(newUsersWordTopic);
            }

            usersWord.addNewTopic(newUsersWordTopic);
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

        UsersWords usersWord = saveOrGetUsersWord(wps, usr);

        //сохранение нового перевода
        addNewUsersWordTranslation(translation, usersWord);

        //сохранение новой темы
        addNewUsersWordTopic(topicName, usersWord);

        usersWordsRepository.save(usersWord);

    }
    public void updateTranslations(UsersWords usersWord, List<String> newTranslations){
        //удаление старых переводов
        usersWord.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans.getName()));

        //добавление новых переводов
        if (!newTranslations.isEmpty())
            for (String newTranslation : newTranslations) {
                if (!usersWord.getListStringTranslations().contains(newTranslation)) {
                    //добавить новый перевод
                    addNewUsersWordTranslation(newTranslation, usersWord);
                }
            }
    }

    public void updateTopics(UsersWords usersWord, List<String> newTopics){
        //удаление старых тем
        usersWord.getTopics().removeIf(oldTopic -> !newTopics.contains(oldTopic.getName()));


        //добавление новых тем
        if (newTopics.size() != 0)
            for (String newTopic : newTopics) {
                if (!usersWord.getListStringTopics().contains(newTopic)) {
                    //добавить новую тему
                    addNewUsersWordTopic(newTopic, usersWord);
                }
            }
    }

    //решить проблему с транскрипцией(должна быть единственной)
    public void editOldUsersWord(
            Long userWordId,
            String wordName,
            String partOfSpeech,
            String transcription,
            List<String> newTranslations,
            List<String> newTopics
    ){
        UsersWords oldUsersWord = usersWordsRepository.findUsersWordsById(userWordId);

        boolean newWordName = !oldUsersWord.getWps().getWord().getName().equals(wordName);
        boolean newWordPartOfSpeech = !oldUsersWord.getWps().getPartOfSpeech().getName().equals(partOfSpeech);
        boolean newWordTranscription = !oldUsersWord.getWps().getWord().getTranscription().equals(transcription);

        //если слово изменило написание (транскрипция остается неизменной)
        transcription = oldUsersWord.getWps().getWord().getTranscription();
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

        //обновление списка переводов
        updateTranslations(oldUsersWord, newTranslations);

        //обновление списка тем
        updateTopics(oldUsersWord, newTopics);


        usersWordsRepository.save(oldUsersWord);
    }

    public List<PartsOfSpeech> getAllPartsOfSpeech(){
        return partsOfSpeechRepository.findAll();
    }

    public List<Topics> getAllUsersTopics(Users usr){
        return topicsRepository.findDistinctTopicsByUwIn(usersWordsRepository.findByUser(usr));
    }

    public UsersWords getUsersWord(Long userWordId){
        return usersWordsRepository.findUsersWordsById(userWordId);
    }

    public List<UsersWords> getAllUsersWords(Users usr){
        return usersWordsRepository.findByUser(usr);
    }

    public Words getWordByName(String word){
        return wordsRepository.findByName(word);
    }



}

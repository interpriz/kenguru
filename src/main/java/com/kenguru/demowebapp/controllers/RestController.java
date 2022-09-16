package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.dto.searchingObjects;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;

    @Autowired
    private UsersWordsRepository usersWordsRepository;

    @Autowired
    private WordsRepository wordsRepository;


    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private WordsTranslationsRepository wordsTranslationsRepository;

    @Autowired
    private UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;

    @Autowired
    private PhrasalVerbsRepository phrasalVerbsRepository;

    @Autowired
    private PartsOfSpeechRepository partsOfSpeechRepository;


    @Autowired
    private PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;


    @GetMapping("/hellow")
    public List<String> hellowWorld(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        return list;
    }
    @GetMapping(value = "/findTranscriptionByWord")
    public String findTranscriptionByWord(@RequestParam String name){

        List<Words> words= wordsRepository.findWordsByName(name);

        return words.get(0).getTranscription();
    }


    @GetMapping("/findWords")
    public searchingObjects findWords(
            @RequestParam String word){


        Users usr  = usersRepository.getById(1L);
        List<UsersWords> searchWords = new ArrayList<>();
        List<PhrasalVerbs> searchPhrasalVerbs = new ArrayList<>();
        List<UsersPhrasalVerbsScores> searchUsersPhrasalVerbsScores = new ArrayList<>();
        // поиск по английскому переводу
        if(word.matches("[a-zA-Z]+"))
        {
            //если введено слово
            if(!word.contains(" ")){
                //поиск слов
                Words wrd = wordsRepository.findByName(word);
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository.findByWord(wrd);
                searchWords = usersWordsRepository.findByUserAndWpsIn(usr,listWPS);
                //поиск фразового глагола со всеми предлогами
                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS));
            }else
            //если введен фразовый глагол с предлогом
            {
                Words wrd = wordsRepository.findByName(word.split(" ")[0]);
                PartsOfSpeech partOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName("verb");
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository.findWordsPartOfSpeechByPartOfSpeechAndWord(partOfSpeech,wrd);
                if(listWPS.size()!=0)
                searchPhrasalVerbs.add(phrasalVerbsRepository.findPhrasalVerbsByWpsAndPreposition(listWPS.get(0),word.split(" ")[1]).get(0));
            }



        }else
            // поиск по русскому переводу
        if(word.matches("[а-яА-Я]+"))
        {
            //поиск слов по переводу
            WordsTranslations wordTranslation = wordsTranslationsRepository.findByName(word);
            if(wordTranslation!=null)
            searchWords.addAll(wordTranslation.getUw());

            //поиск всех фразовых глаголов по переводу глагола
            PartsOfSpeech partOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName("verb");
                //поиск всех WPS в searchWords где часть речи - глагол
            List<WordsPartOfSpeech> listWPS = searchWords.stream()
                    .filter(i->i.getWps().getPartOfSpeech()==partOfSpeech)
                    .map((i)->i.getWps())
                    .collect(Collectors.toList());
            searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS));

            //поиск фразового глагола по его переводу
            List<PhrasalVerbsTranslations> phrasalVerbsTranslations =  phrasalVerbsTranslationsRepository.findByName(word);
            if(phrasalVerbsTranslations.size()!=0)
                searchUsersPhrasalVerbsScores.addAll(phrasalVerbsTranslations.get(0).getUpv());

        }

        //заполнение списков объектов DTO
        List<UsersWord> searchUserWords = new ArrayList<>();
        for(UsersWords userWord : searchWords){
            searchUserWords.add(new UsersWord(userWord));
        }

        searchUsersPhrasalVerbsScores.addAll(
                usersPhrasalVerbsScoresRepository.findByUserAndPhrasalVerbIn(usr,searchPhrasalVerbs));
        List<UsersPhrasalVerb> searchUsersPhrasalVerbs = new ArrayList<>();
        for( UsersPhrasalVerbsScores usersPhrasalVerb : searchUsersPhrasalVerbsScores){
            searchUsersPhrasalVerbs.add(new UsersPhrasalVerb(usersPhrasalVerb));
        }



        //model.addAttribute("usersWords", searchWords);
        searchingObjects result = new searchingObjects(searchUserWords,searchUsersPhrasalVerbs);

        return result;
    }
}

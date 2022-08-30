package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private WordsPartOfSpeechRepository words_parts_of_speechRepository;

    @Autowired
    private UsersWordsRepository users_wordsRepository;

    @Autowired
    private WordsRepository wordsRepository;


    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private WordsTranslationsRepository words_translationsRepository;

    @Autowired

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
    public List<UsersWord> findWords(
            @RequestParam String word){


        Users usr  = usersRepository.getById(1L);
        List<UsersWords> searchWords = new ArrayList<>();
        if(word.matches("[a-zA-Z]+"))
        {
            Words wrd = wordsRepository.findByName(word);
            List<WordsPartOfSpeech> wps = words_parts_of_speechRepository.findByWord(wrd);
            searchWords = users_wordsRepository.findByUserAndWpsIn(usr,wps);
        }else
        if(word.matches("[а-яА-Я]+"))
        {
            WordsTranslations wordTranslation = words_translationsRepository.findByName(word);
            if(wordTranslation!=null)
            searchWords.addAll(wordTranslation.getUw());
        }
        List<UsersWord> searchUserWords = new ArrayList<>();
        for(int i=0; i< searchWords.size(); i++){
            searchUserWords.add(new UsersWord(searchWords.get(i)));
        }


        //model.addAttribute("usersWords", searchWords);


        return searchUserWords;
    }
}

package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.Users_word;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private Words_part_of_speechRepository words_parts_of_speechRepository;

    @Autowired
    private Users_wordsRepository users_wordsRepository;

    @Autowired
    private WordsRepository wordsRepository;


    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private Words_translationsRepository words_translationsRepository;

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
    public List<Users_word> findWords(
            @RequestParam String word){


        Users usr  = usersRepository.getById(1L);
        List<Users_words> searchWords = new ArrayList<>();
        if(word.matches("[a-zA-Z]+"))
        {
            Words wrd = wordsRepository.findByName(word);
            List<Words_part_of_speech> wps = words_parts_of_speechRepository.findByWord(wrd);
            searchWords = users_wordsRepository.findByUserAndWpsIn(usr,wps);
        }else
        if(word.matches("[а-яА-Я]+"))
        {
            Words_translations wordTranslation = words_translationsRepository.findByName(word);
            if(wordTranslation!=null)
            searchWords.addAll(wordTranslation.getUw());
        }
        List<Users_word> searchUserWords = new ArrayList<>();
        for(int i=0; i< searchWords.size(); i++){
            searchUserWords.add(new Users_word(searchWords.get(i)));
        }


        //model.addAttribute("usersWords", searchWords);


        return searchUserWords;
    }
}

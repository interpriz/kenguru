package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.services.SearchingService;
import com.kenguru.demowebapp.services.UsersWordsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {


    private final SearchingService searchingService;
    private final UsersWordsService usersWordsService;

    public RestController(SearchingService searchingService, UsersWordsService usersWordsService) {
        this.searchingService = searchingService;
        this.usersWordsService = usersWordsService;
    }

    @GetMapping("/hellow")
    public List<String> hellowWorld(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        return list;
    }

    @GetMapping(value = "/find/transcription-by-word")
    public String findTranscriptionByWord(@RequestParam String name){

        Words word= usersWordsService.getWordByName(name);

        return word.getTranscription();
    }


    /*@GetMapping("/find/words")
    public SearchingObjects findWords(
            @AuthenticationPrincipal Users usr,
            @RequestParam String word){

        //Users usr  = usersRepository.getById(1L);
        return searchingService.findWords(usr,word);
    }*/

    @GetMapping("/find/words")
    public List<UsersWord> findWords(
            @AuthenticationPrincipal Users usr,
            @RequestParam String word){

        //Users usr  = usersRepository.getById(1L);
        return searchingService.findWordsV2(usr,word);
    }

    @GetMapping("/find/phrasal-verbs")
    public List<UsersPhrasalVerb> findPhrasalVerbs(
            @AuthenticationPrincipal Users usr,
            @RequestParam String word){

        //Users usr  = usersRepository.getById(1L);
        return searchingService.findPhrasalVerbs(usr,word);
    }
}

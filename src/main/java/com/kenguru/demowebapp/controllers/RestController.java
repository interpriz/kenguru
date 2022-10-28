package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.SearchingObjects;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import com.kenguru.demowebapp.services.SearchingService;
import com.kenguru.demowebapp.services.UsersWordsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value = "/findTranscriptionByWord")
    public String findTranscriptionByWord(@RequestParam String name){

        Words words= usersWordsService.getWordByName(name);

        return words.getTranscription();
    }


    @GetMapping("/findWords")
    public SearchingObjects findWords(
            @AuthenticationPrincipal Users usr,
            @RequestParam String word){

        //Users usr  = usersRepository.getById(1L);

        return searchingService.findWords(usr,word);
    }
}

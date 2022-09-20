package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import com.kenguru.demowebapp.services.UsersWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class UsersWordsController {



    private UsersWordsService service;

    public UsersWordsController(UsersWordsService service) {
        this.service = service;
    }


    @GetMapping("/addNewWord")
    public String addNewWord(Model model) {
        model.addAttribute("title", "Добавление нового слова");

        List<PartsOfSpeech> ps = service.getAllPartsOfSpeech();
        model.addAttribute("ps", ps);

        List<Topics> topics = service.getAllUsersTopics();
        model.addAttribute("topics", topics);

        return "addNewWord";
    }

    @PostMapping("/addNewWord")
    public String addNewWordPost(
            @RequestParam String partOfSpeech,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String translation,
            @RequestParam String topicName,
            Model model) {
        service.saveNewUsersWord(partOfSpeech,wordName,transcription,translation,topicName);


        return "redirect:/history";
    }

    @GetMapping("/editWord")
    public String editWord(@RequestParam Long userWordId, Model model){
        model.addAttribute("title", "Редактирование слова");

        UsersWords usersWord = service.getUsersWord(userWordId);

        model.addAttribute("usersWord", usersWord);

        List<PartsOfSpeech> ps = service.getAllPartsOfSpeech();
        model.addAttribute("ps", ps);

        List<Topics> topics = service.getAllUsersTopics();
        model.addAttribute("topics", topics);


        return "/editWord";
    }

    @PostMapping("/editWord")
    public String editWord(
            @RequestParam Long userWordId,
            @RequestParam String wordName,
            @RequestParam String partOfSpeech,
            @RequestParam String transcription,
            //@RequestParam List<String> oldTranslations,
            @RequestParam(required = false) List<String> newTranslations,
            //@RequestParam(required = false) List<String> oldTopics,
            @RequestParam(required = false) List<String> newTopics,
            Model model) {
        service.editOldUsersWord(
                userWordId,
                wordName,
                partOfSpeech,
                transcription,
                newTranslations,
                newTopics);


        return "redirect:/history";
    }






}

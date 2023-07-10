package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.services.UsersPhrasalVerbsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.kenguru.demowebapp.StaticStrings.*;

@Controller
public class UsersPhrasalVerbsController {

    private final UsersPhrasalVerbsService service;

    public UsersPhrasalVerbsController(UsersPhrasalVerbsService service) {
        this.service = service;
    }

    @GetMapping("/add/new-phrasal-verb")
    public String addNewPhrasalVerb(Model model) {
        model.addAttribute("title", ADD_NEW_PHRASAL_VERB_TITLE);

        return "addNewPhrasalVerb";
    }

    @PostMapping("/add/new-phrasal-verb")
    public String addNewPhrasalVerb(
            @AuthenticationPrincipal Users usr,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String preposition,
            @RequestParam String translation,
            @RequestParam String description,
            Model model) {

        service.addNewPhrasalVerb(wordName,
                transcription,
                preposition,
                translation,
                description,
                usr);



        return "redirect:/history";
    }

    @GetMapping("/edit/phrasal-verb")
    public String editPhrasalVerb( @RequestParam Long userPhrasalVerbId, Model model){
        model.addAttribute("title", EDIT_PHRASAL_VERB_TITLE);

        UsersPhrasalVerbsScores usersPhrasalVerb = service.getUsersPhrasalVerbById(userPhrasalVerbId);

        model.addAttribute(ATTRIBUTE_USERS_PHRASAL_VERB, usersPhrasalVerb);

        return "/editPhrasalVerb";
    }

    @PostMapping("/edit/phrasal-verb")
    public String editPhrasalVerb(
            @RequestParam Long userPhrasalVerbId,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String preposition,
            @RequestParam(required = false) List<String> newTranslations,
            @RequestParam String description,
            Model model){

        service.editPhrasalVerb(
                userPhrasalVerbId,
                wordName,
                transcription,
                preposition,
                newTranslations,
                description);

        return "redirect:/history";
    }

}

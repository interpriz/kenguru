package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.services.UsersPhrasalVerbsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsersPhrasalVerbsController {

    private UsersPhrasalVerbsService service;

    public UsersPhrasalVerbsController(UsersPhrasalVerbsService service) {
        this.service = service;
    }

    @GetMapping("/addNewPhrasalVerb")
    public String addNewPhrasalVerb(Model model) {
        model.addAttribute("title", "Добавление нового фразового глагола");

        return "addNewPhrasalVerb";
    }

    @PostMapping("/addNewPhrasalVerb")
    public String addNewPhrasalVerb(
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
                description);



        return "redirect:/history";
    }

    @GetMapping("/editPhrasalVerb")
    public String editPhrasalVerb( @RequestParam Long userPhrasalVerbId, Model model){
        model.addAttribute("title", "Редактирование фразового глагола");

        UsersPhrasalVerbsScores usersPhrasalVerb = service.getUsersPhrasalVerbById(userPhrasalVerbId);

        model.addAttribute("usersPhrasalVerb", usersPhrasalVerb);

        return "/editPhrasalVerb";
    }

    @PostMapping("/editPhrasalVerb")
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

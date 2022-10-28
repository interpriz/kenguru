package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import com.kenguru.demowebapp.services.UsersCompAdjectivesService;
import com.kenguru.demowebapp.services.UsersIrregularVerbService;
import com.kenguru.demowebapp.services.UsersPhrasalVerbsService;
import com.kenguru.demowebapp.services.UsersWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private final UsersIrregularVerbService usersIrrVerbService;
    private final UsersWordsService usersWordsService;
    private final UsersPhrasalVerbsService usersPhrasVerbsService;
    private final UsersCompAdjectivesService usersCompAdjectivesService;

    public MainController(UsersIrregularVerbService usersIrrVerbService,
                          UsersWordsService usersWordsService,
                          UsersPhrasalVerbsService usersPhrasVerbsService,
                          UsersCompAdjectivesService usersCompAdjectivesService) {
        this.usersIrrVerbService = usersIrrVerbService;
        this.usersWordsService = usersWordsService;
        this.usersPhrasVerbsService = usersPhrasVerbsService;
        this.usersCompAdjectivesService = usersCompAdjectivesService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/history")
    public String history(
            @AuthenticationPrincipal Users usr,
            Model model) {
        model.addAttribute("title", "История");

        //Users usr  = usersRepository.getById(1L);
        List<UsersWords> usersWords = usersWordsService.getAllUsersWords(usr);
        model.addAttribute("usersWords", usersWords);

        List<UsersIrregularVerbsScores> usersIrrVerbs = usersIrrVerbService.getUsersIrrVerbsByUser(usr);
        model.addAttribute("usersIrrVerbs", usersIrrVerbs);

        List<UsersComparativeAdjectivesScores> usersCompAdj = usersCompAdjectivesService.getUsersCompAdjecByUser(usr);
        model.addAttribute("usersCompAdj", usersCompAdj);

        List<UsersPhrasalVerbsScores> usersPhrasVerbs = usersPhrasVerbsService.getUsersPhrasalVerbByUser(usr);
        model.addAttribute("usersPhrasVerbs", usersPhrasVerbs);

        return "history";
    }

    @GetMapping("/addNewIrregularVerb")
    public String addNewIrregularVerb(Model model) {

        model.addAttribute("title", "Добавление форм неправильного глагола");

        return "addNewIrregularVerb";
    }

    @PostMapping("/addNewIrregularVerb")
    public String addNewIrregularVerb(
            @AuthenticationPrincipal Users usr,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String secondForm,
            @RequestParam String thirdForm,
            Model model) {

        usersIrrVerbService.addNewIrregularVerb(
                wordName,
                transcription,
                secondForm,
                thirdForm,
                usr
        );

        return "redirect:/history";
    }



    @GetMapping("/addNewComparativeAdjective")
    public String addNewComparativeAdjective(Model model) {

        model.addAttribute("title", "Добавление сравнительных степеней прилагательного");

        return "addNewComparativeAdjective";
    }


    @PostMapping("/addNewComparativeAdjective")
    public String addNewComparativeAdjective(
            @AuthenticationPrincipal Users usr,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String comparative,
            @RequestParam String superlative,
            Model model) {

        usersCompAdjectivesService.addNewComparativeAdjective(
                wordName,
                transcription,
                comparative,
                superlative,
                usr
        );

        return "redirect:/history";
    }




}


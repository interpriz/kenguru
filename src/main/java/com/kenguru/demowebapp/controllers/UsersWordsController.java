package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.services.UsersWordsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.kenguru.demowebapp.StaticStrings.*;

@Controller
public class UsersWordsController {


    private final UsersWordsService service;

    public UsersWordsController(UsersWordsService service) {
        this.service = service;
    }


    @GetMapping("/add/new-word")
    public String addNewWord(
            @AuthenticationPrincipal Users usr,
            Model model
    ) {
        model.addAttribute("title", TITLE_ADD_NEW_WORD);

        List<PartsOfSpeech> partOfSpeech = service.getAllPartsOfSpeech();
        model.addAttribute(ATTRIBUTE_PART_OF_SPEECH, partOfSpeech);


        //Users usr  = usersRepository.getById(1L);
        List<Topics> topics = service.getAllUsersTopics(usr);
        model.addAttribute(ATTRIBUTE_TOPICS, topics);

        return "addNewWord";
    }

    @PostMapping("/add/new-word")
    public String addNewWordPost(
            @AuthenticationPrincipal Users usr,
           /* @RequestParam String partOfSpeech,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam(required = false) List<String> translations,
            @RequestParam(required = false) List<String> topics,*/
            @Valid UsersWord newWord,
            BindingResult bindingResult,
            Model model) {
        //service.saveNewUsersWord(partOfSpeech,wordName,transcription,translations,topics,usr);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute(ATTRIBUTE_NEW_WORD, newWord);

            List<PartsOfSpeech> partOfSpeech = service.getAllPartsOfSpeech();
            model.addAttribute(ATTRIBUTE_PART_OF_SPEECH, partOfSpeech);


            //Users usr  = usersRepository.getById(1L);
            List<Topics> topics = service.getAllUsersTopics(usr);
            model.addAttribute(ATTRIBUTE_TOPICS, topics);
            return "addNewWord";
        }else{
            service.saveNewUsersWord(newWord,usr);
            return "redirect:/history";
        }



    }

    @GetMapping("/edit/word")
    public String editWord(
            @AuthenticationPrincipal Users usr,
            @RequestParam Long userWordId,
            Model model){
        model.addAttribute("title", TITLE_EDIT_WORD);

        UsersWords usersWord = service.getUsersWord(userWordId);

        model.addAttribute(ATTRIBUTE_USERS_WORD, usersWord);

        List<PartsOfSpeech> ps = service.getAllPartsOfSpeech();
        model.addAttribute(ATTRIBUTE_PART_OF_SPEECH, ps);

        //Users usr  = usersRepository.getById(1L);
        List<Topics> topics = service.getAllUsersTopics(usr);
        model.addAttribute(ATTRIBUTE_TOPICS, topics);


        return "/editWord";
    }

    @PostMapping("/edit/word")
    public String editWord(
            @RequestParam Long userWordId,
            @RequestParam String wordName,
            @RequestParam String partOfSpeech,
            @RequestParam String transcription,
            @RequestParam(required = false) List<String> translations,
            @RequestParam(required = false) List<String> topics,
            Model model) {
        service.saveEditedOldUsersWord(
                userWordId,
                wordName,
                partOfSpeech,
                transcription,
                translations,
                topics);


        return "redirect:/history";
    }
}

package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private Words_part_of_speechRepository wordsPartsOfSpeechRepository;

    @Autowired
    private Users_wordsRepository users_wordsRepository;

    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private Parts_of_speechRepository parts_of_speechRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private Users_irregular_verbs_scoresRepository users_irregular_verbs_scoresRepository;

    @Autowired
    private Irregular_verbsRepository irregular_verbsRepository;

    @Autowired
    private Users_comparative_adjectives_scoresRepository users_comparative_adjectives_scoresRepository;

    @Autowired
    private Comparative_adjectivesRepository comparative_adjectivesRepository;

    @Autowired
    private Users_phrasal_verbs_scoresRepository users_phrasal_verbs_scoresRepository;

    @Autowired
    private Words_translationsRepository words_translationsRepository;

    @Autowired
    private Phrasal_verbsRepository phrasal_verbsRepository;

    @Autowired
    private Phrasal_verbs_translationsRepository phrasal_verbs_translationsRepository;



    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("title", "История");

        Iterable<Words_part_of_speech> wps = wordsPartsOfSpeechRepository.findAll();
        model.addAttribute("wps", wps);

        Iterable<Users_words> uw = users_wordsRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("uw", uw);

        Iterable<Users_irregular_verbs_scores> uivs = users_irregular_verbs_scoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("uivs", uivs);

        Iterable<Users_comparative_adjectives_scores> ucas = users_comparative_adjectives_scoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("ucas", ucas);

        Iterable<Users_phrasal_verbs_scores> upv = users_phrasal_verbs_scoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("upv", upv);

        return "history";
    }

    @GetMapping("/addNewWord")
    public String addNewWord(Model model) {
        model.addAttribute("title", "Добавление нового слова");

        Iterable<Parts_of_speech> ps = parts_of_speechRepository.findAll();
        model.addAttribute("ps", ps);

        Users usr  = usersRepository.getById(1L);
        Iterable<Topics> topics = topicsRepository.findDistinctTopicsByUwIn(users_wordsRepository.findByUser(usr));
        model.addAttribute("topics", topics);

        return "addNewWord";
    }

    private Words saveOrGetWord(String wordName, String transcription){
        Words word;
        List<Words> words= wordsRepository.findWordsByName(wordName);
        if(words.size()==0)
        {
            word = new Words(wordName,transcription);
            wordsRepository.save(word);
        }else{
            word = words.get(0);
        }
        return word;
    }

    private Words_part_of_speech saveOrGetWPS(Parts_of_speech pos, Words word){
        Words_part_of_speech wps;
        List<Words_part_of_speech> wpsList =  wordsPartsOfSpeechRepository.findWords_part_of_speechByPartOfSpeechAndWord(pos,word);
        if(wpsList.size()==0)
        {
            wps = new Words_part_of_speech(word,pos);
            wordsPartsOfSpeechRepository.save(wps);
        }else{
            wps = wpsList.get(0);
        }
        return wps;
    }

    private Users_words saveOrGetUsersWord(Words_part_of_speech wps, Users usr){
        Users_words uw;
        List<Users_words> uwList = users_wordsRepository.findUsers_wordsByUserAndWps(usr,wps);
        if(uwList.size()==0)
        {
            uw = new Users_words(0,wps,usr);
            users_wordsRepository.save(uw);
        }else{
            uw = uwList.get(0);
        }
        return uw;
    }


    @PostMapping("/addNewWord")
    public String addNewWordPost(
            @RequestParam String partOfSpeech,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String translation,
            @RequestParam String topicName,
            Model model) {


        Parts_of_speech pos = parts_of_speechRepository.findParts_of_speechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        Words_part_of_speech wps = saveOrGetWPS(pos, word);

        Users_words uw = saveOrGetUsersWord(wps, usr);


        if(translation !="")
        {
            Words_translations wordTranslation;
            List<Words_translations> translationsList = words_translationsRepository.findWords_translationsByName(translation);
            if(translationsList.size()==0)
            {
                wordTranslation = new Words_translations(translation);
                words_translationsRepository.save(wordTranslation);
            }else{
                wordTranslation = translationsList.get(0);
            }

            Set<Words_translations> uwTranslations = uw.getTranslations();
            if(!uwTranslations.contains((wordTranslation))){
                uwTranslations.add(wordTranslation);
                uw.setTranslations(uwTranslations);
                users_wordsRepository.save(uw);
            }
        }

        if(topicName!="")
        {
            Topics topic;
            List<Topics> topicList = topicsRepository.findTopicsByName(topicName);
            if(topicList.size()==0)
            {
                topic = new Topics(topicName);
                topicsRepository.save(topic);
            }else{
                topic = topicList.get(0);
            }

            Set<Topics> uwtTopics = uw.getTopics();
            if(!uwtTopics.contains(topic)){
                uwtTopics.add(topic);
                uw.setTopics(uwtTopics);
                users_wordsRepository.save(uw);
            }
        }
        return "redirect:/history";
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



        String partOfSpeech = "verb";
        Parts_of_speech pos = parts_of_speechRepository.findParts_of_speechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        Words_part_of_speech wps = saveOrGetWPS(pos, word);

        Users_words uw = saveOrGetUsersWord(wps, usr);

        Phrasal_verbs phrasalVerb;
        List<Phrasal_verbs> pvList = phrasal_verbsRepository.findPhrasal_verbsByWpsAndPreposition(wps, preposition);
        if(pvList.size()==0)
        {
            phrasalVerb = new Phrasal_verbs(preposition,wps);
            phrasal_verbsRepository.save(phrasalVerb);
        }else{
            phrasalVerb = pvList.get(0);
        }

        Users_phrasal_verbs_scores usersPhrasalVerb;
        List<Users_phrasal_verbs_scores> upvList = users_phrasal_verbs_scoresRepository.findUsers_phrasal_verbsByUserAndPhrasalVerb(usr,phrasalVerb);
        if(upvList.size()==0)
        {
            usersPhrasalVerb = new Users_phrasal_verbs_scores(0,description,phrasalVerb,usr);
            users_phrasal_verbs_scoresRepository.save(usersPhrasalVerb);
        }else{
            usersPhrasalVerb = upvList.get(0);
        }

        if(translation !="")
        {
            Phrasal_verbs_translations pvTranslation;
            List<Phrasal_verbs_translations> pvtList = phrasal_verbs_translationsRepository.findPhrasal_verbs_translationsByName(translation);
            if(pvtList.size()==0)
            {
                pvTranslation = new Phrasal_verbs_translations(translation);
                phrasal_verbs_translationsRepository.save(pvTranslation);
            }else{
                pvTranslation = pvtList.get(0);
            }



            Set<Phrasal_verbs_translations> pvTranslations = usersPhrasalVerb.getTranslations();
            if(!pvTranslations.contains(pvTranslation)){
                pvTranslations.add(pvTranslation);
                usersPhrasalVerb.setTranslations(pvTranslations);
                users_phrasal_verbs_scoresRepository.save(usersPhrasalVerb);
            }
        }
        return "redirect:/history";
    }


    @GetMapping("/addNewIrregularVerb")
    public String addNewIrregularVerb(Model model) {
        model.addAttribute("title", "Добавление форм неправильного глагола");

        return "addNewIrregularVerb";
    }

    @PostMapping("/addNewIrregularVerb")
    public String addNewIrregularVerb(
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String secondForm,
            @RequestParam String thirdForm,
            Model model) {


        String partOfSpeech = "verb";
        Parts_of_speech pos = parts_of_speechRepository.findParts_of_speechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        Words_part_of_speech wps = saveOrGetWPS(pos, word);

        Users_words uw = saveOrGetUsersWord(wps, usr);

        Irregular_verbs irregularVerb;
        List<Irregular_verbs> ivList = irregular_verbsRepository.findIrregular_verbsByWps(wps);
        if(ivList.size()==0)
        {
            irregularVerb = new Irregular_verbs(secondForm,thirdForm,wps);
            irregular_verbsRepository.save(irregularVerb);
        }else{
            irregularVerb = ivList.get(0);
        }

        Users_irregular_verbs_scores usersIrregularVerb;
        List<Users_irregular_verbs_scores> uivList = users_irregular_verbs_scoresRepository.findByUserAndIrregularVerb(usr,irregularVerb);
        if(uivList.size()==0)
        {
            usersIrregularVerb = new Users_irregular_verbs_scores(0,irregularVerb,usr);
            users_irregular_verbs_scoresRepository.save(usersIrregularVerb);
        }else{
            usersIrregularVerb = uivList.get(0);
        }
        return "redirect:/history";
    }



    @GetMapping("/addNewComparativeAdjective")
    public String addNewComparativeAdjective(Model model) {
        model.addAttribute("title", "Добавление сравнительных степеней прилагательного");

        return "addNewComparativeAdjective";
    }


    @PostMapping("/addNewComparativeAdjective")
    public String addNewComparativeAdjective(
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String comparative,
            @RequestParam String superlative,
            Model model) {


        String partOfSpeech = "adjective";
        Parts_of_speech pos = parts_of_speechRepository.findParts_of_speechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        Words_part_of_speech wps = saveOrGetWPS(pos, word);

        Users_words uw = saveOrGetUsersWord(wps, usr);

        Comparative_adjectives comparativeAdjective;
        List<Comparative_adjectives> caList = comparative_adjectivesRepository.findComparative_adjectivesByWps(wps);
        if(caList.size()==0)
        {
            comparativeAdjective = new Comparative_adjectives(comparative,superlative,wps);
            comparative_adjectivesRepository.save(comparativeAdjective);
        }else{
            comparativeAdjective = caList.get(0);
        }

        Users_comparative_adjectives_scores usersComparativeAdjective;
        List<Users_comparative_adjectives_scores> ucaList = users_comparative_adjectives_scoresRepository.findByUserAndComparativeAdjective(usr,comparativeAdjective);
        if(ucaList.size()==0)
        {
            usersComparativeAdjective = new Users_comparative_adjectives_scores(0,comparativeAdjective,usr);
            users_comparative_adjectives_scoresRepository.save(usersComparativeAdjective);
        }else{
            usersComparativeAdjective = ucaList.get(0);
        }
        return "redirect:/history";
    }

    @GetMapping("/editWord")
    public String editWord( @RequestParam Long userWordId, Model model){
        model.addAttribute("title", "Редактирование слова");

        Users_words usersWord = users_wordsRepository.findUsers_wordsById(userWordId);

        model.addAttribute("usersWord", usersWord);


        return "/editWord";
    }

    @PostMapping("/editWord")
    public String editWord(
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String newTranslation,
            @RequestParam List<String> Translations,
            @RequestParam String newTopic,
            @RequestParam List<String> Topics,
            Model model){
        return "redirect:/history";
    }






}


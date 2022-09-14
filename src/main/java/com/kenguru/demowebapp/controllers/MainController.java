package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.dto.UsersWord;
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
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;

    @Autowired
    private UsersWordsRepository usersWordsRepository;

    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private PartsOfSpeechRepository partsOfSpeechRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository;

    @Autowired
    private IrregularVerbsRepository irregular_verbsRepository;

    @Autowired
    private UsersComparativeAdjectivesScoresRepository usersComparativeAdjectivesScoresRepository;

    @Autowired
    private ComparativeAdjectivesRepository comparativeAdjectivesRepository;

    @Autowired
    private UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;

    @Autowired
    private WordsTranslationsRepository wordsTranslationsRepository;

    @Autowired
    private PhrasalVerbsRepository phrasalVerbsRepository;

    @Autowired
    private PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;



    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("title", "История");

        Iterable<WordsPartOfSpeech> wps = wordsPartsOfSpeechRepository.findAll();
        model.addAttribute("wps", wps);

        Iterable<UsersWords> uw = usersWordsRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("uw", uw);

        Iterable<UsersIrregularVerbsScores> uivs = usersIrregularVerbsScoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("uivs", uivs);

        Iterable<UsersComparativeAdjectivesScores> ucas = usersComparativeAdjectivesScoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("ucas", ucas);

        Iterable<UsersPhrasalVerbsScores> upv = usersPhrasalVerbsScoresRepository.findByUser(new Users(1L,"u"));
        model.addAttribute("upv", upv);

        return "history";
    }

    @GetMapping("/addNewWord")
    public String addNewWord(Model model) {
        model.addAttribute("title", "Добавление нового слова");

        Iterable<PartsOfSpeech> ps = partsOfSpeechRepository.findAll();
        model.addAttribute("ps", ps);

        Users usr  = usersRepository.getById(1L);
        Iterable<Topics> topics = topicsRepository.findDistinctTopicsByUwIn(usersWordsRepository.findByUser(usr));
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

    private WordsPartOfSpeech saveOrGetWPS(PartsOfSpeech pos, Words word){
        WordsPartOfSpeech wps;
        List<WordsPartOfSpeech> wpsList =  wordsPartsOfSpeechRepository.findWordsPartOfSpeechByPartOfSpeechAndWord(pos,word);
        if(wpsList.size()==0)
        {
            wps = new WordsPartOfSpeech(word,pos);
            wordsPartsOfSpeechRepository.save(wps);
        }else{
            wps = wpsList.get(0);
        }
        return wps;
    }

    private UsersWords saveOrGetUsersWord(WordsPartOfSpeech wps, Users usr){
        UsersWords uw;
        List<UsersWords> uwList = usersWordsRepository.findUsersWordsByUserAndWps(usr,wps);
        if(uwList.size()==0)
        {
            uw = new UsersWords(0,wps,usr);
            usersWordsRepository.save(uw);
        }else{
            uw = uwList.get(0);
        }
        return uw;
    }

    private void saveNewTranslation(String newTranslation, UsersWords usersWord){
        if(newTranslation !="")
        {
            WordsTranslations wordTranslation;
            List<WordsTranslations> translationsList = wordsTranslationsRepository.findWordsTranslationsByName(newTranslation);
            if(translationsList.size()==0)
            {
                wordTranslation = new WordsTranslations(newTranslation);
                wordsTranslationsRepository.save(wordTranslation);
            }else{
                wordTranslation = translationsList.get(0);
            }

            Set<WordsTranslations> uwTranslations = usersWord.getTranslations();
            if(!uwTranslations.contains((wordTranslation))){
                uwTranslations.add(wordTranslation);
                usersWord.setTranslations(uwTranslations);
                usersWordsRepository.save(usersWord);
            }
        }
    }

    private void saveNewTopic(String newTopic, UsersWords usersWord){
        if(newTopic!="")
        {
            Topics topic;
            List<Topics> topicList = topicsRepository.findTopicsByName(newTopic);
            if(topicList.size()==0)
            {
                topic = new Topics(newTopic);
                topicsRepository.save(topic);
            }else{
                topic = topicList.get(0);
            }

            Set<Topics> uwtTopics = usersWord.getTopics();
            if(!uwtTopics.contains(topic)){
                uwtTopics.add(topic);
                usersWord.setTopics(uwtTopics);
                usersWordsRepository.save(usersWord);
            }
        }
    }


    @PostMapping("/addNewWord")
    public String addNewWordPost(
            @RequestParam String partOfSpeech,
            @RequestParam String wordName,
            @RequestParam String transcription,
            @RequestParam String translation,
            @RequestParam String topicName,
            Model model) {


        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = saveOrGetWPS(pos, word);

        UsersWords uw = saveOrGetUsersWord(wps, usr);

        //сохранение нового перевода
        saveNewTranslation(translation, uw);
//        if(translation !="")
//        {
//            WordsTranslations wordTranslation;
//            List<WordsTranslations> translationsList = wordsTranslationsRepository.findWordsTranslationsByName(translation);
//            if(translationsList.size()==0)
//            {
//                wordTranslation = new WordsTranslations(translation);
//                wordsTranslationsRepository.save(wordTranslation);
//            }else{
//                wordTranslation = translationsList.get(0);
//            }
//
//            Set<WordsTranslations> uwTranslations = uw.getTranslations();
//            if(!uwTranslations.contains((wordTranslation))){
//                uwTranslations.add(wordTranslation);
//                uw.setTranslations(uwTranslations);
//                usersWordsRepository.save(uw);
//            }
//        }
        //сохранение новой темы
        saveNewTopic(topicName, uw);
//        if(topicName!="")
//        {
//            Topics topic;
//            List<Topics> topicList = topicsRepository.findTopicsByName(topicName);
//            if(topicList.size()==0)
//            {
//                topic = new Topics(topicName);
//                topicsRepository.save(topic);
//            }else{
//                topic = topicList.get(0);
//            }
//
//            Set<Topics> uwtTopics = uw.getTopics();
//            if(!uwtTopics.contains(topic)){
//                uwtTopics.add(topic);
//                uw.setTopics(uwtTopics);
//                usersWordsRepository.save(uw);
//            }
//        }
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
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = saveOrGetWPS(pos, word);

        UsersWords uw = saveOrGetUsersWord(wps, usr);

        PhrasalVerbs phrasalVerb;
        List<PhrasalVerbs> pvList = phrasalVerbsRepository.findPhrasalVerbsByWpsAndPreposition(wps, preposition);
        if(pvList.size()==0)
        {
            phrasalVerb = new PhrasalVerbs(preposition,wps);
            phrasalVerbsRepository.save(phrasalVerb);
        }else{
            phrasalVerb = pvList.get(0);
        }

        UsersPhrasalVerbsScores usersPhrasalVerb;
        List<UsersPhrasalVerbsScores> upvList = usersPhrasalVerbsScoresRepository.findUsersPhrasalVerbsByUserAndPhrasalVerb(usr,phrasalVerb);
        if(upvList.size()==0)
        {
            usersPhrasalVerb = new UsersPhrasalVerbsScores(0,description,phrasalVerb,usr);
            usersPhrasalVerbsScoresRepository.save(usersPhrasalVerb);
        }else{
            usersPhrasalVerb = upvList.get(0);
        }

        if(translation !="")
        {
            PhrasalVerbsTranslations pvTranslation;
            List<PhrasalVerbsTranslations> pvtList = phrasalVerbsTranslationsRepository.findPhrasal_verbs_translationsByName(translation);
            if(pvtList.size()==0)
            {
                pvTranslation = new PhrasalVerbsTranslations(translation);
                phrasalVerbsTranslationsRepository.save(pvTranslation);
            }else{
                pvTranslation = pvtList.get(0);
            }



            Set<PhrasalVerbsTranslations> pvTranslations = usersPhrasalVerb.getTranslations();
            if(!pvTranslations.contains(pvTranslation)){
                pvTranslations.add(pvTranslation);
                usersPhrasalVerb.setTranslations(pvTranslations);
                usersPhrasalVerbsScoresRepository.save(usersPhrasalVerb);
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
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = saveOrGetWPS(pos, word);

        UsersWords uw = saveOrGetUsersWord(wps, usr);

        IrregularVerbs irregularVerb;
        List<IrregularVerbs> ivList = irregular_verbsRepository.findIrregularVerbsByWps(wps);
        if(ivList.size()==0)
        {
            irregularVerb = new IrregularVerbs(secondForm,thirdForm,wps);
            irregular_verbsRepository.save(irregularVerb);
        }else{
            irregularVerb = ivList.get(0);
        }

        UsersIrregularVerbsScores usersIrregularVerb;
        List<UsersIrregularVerbsScores> uivList = usersIrregularVerbsScoresRepository.findByUserAndIrregularVerb(usr,irregularVerb);
        if(uivList.size()==0)
        {
            usersIrregularVerb = new UsersIrregularVerbsScores(0,irregularVerb,usr);
            usersIrregularVerbsScoresRepository.save(usersIrregularVerb);
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
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        Users usr  = usersRepository.getById(1L);

        Words word = saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = saveOrGetWPS(pos, word);

        UsersWords uw = saveOrGetUsersWord(wps, usr);

        ComparativeAdjectives comparativeAdjective;
        List<ComparativeAdjectives> caList = comparativeAdjectivesRepository.findComparativeAdjectivesByWps(wps);
        if(caList.size()==0)
        {
            comparativeAdjective = new ComparativeAdjectives(comparative,superlative,wps);
            comparativeAdjectivesRepository.save(comparativeAdjective);
        }else{
            comparativeAdjective = caList.get(0);
        }

        UsersComparativeAdjectivesScores usersComparativeAdjective;
        List<UsersComparativeAdjectivesScores> ucaList = usersComparativeAdjectivesScoresRepository.findByUserAndComparativeAdjective(usr,comparativeAdjective);
        if(ucaList.size()==0)
        {
            usersComparativeAdjective = new UsersComparativeAdjectivesScores(0,comparativeAdjective,usr);
            usersComparativeAdjectivesScoresRepository.save(usersComparativeAdjective);
        }else{
            usersComparativeAdjective = ucaList.get(0);
        }
        return "redirect:/history";
    }

    @GetMapping("/editWord")
    public String editWord( @RequestParam Long userWordId, Model model){
        model.addAttribute("title", "Редактирование слова");

        UsersWords usersWord = usersWordsRepository.findUsersWordsById(userWordId);

        model.addAttribute("usersWord", usersWord);

        Iterable<PartsOfSpeech> ps = partsOfSpeechRepository.findAll();
        model.addAttribute("ps", ps);


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
            Model model){

        UsersWords oldUsersWord = usersWordsRepository.findUsersWordsById(userWordId);
        UsersWord oldUsersWordDTO = new UsersWord(oldUsersWord);



        if(oldUsersWordDTO.getWord() != wordName){

        }else{

        }

        if(oldUsersWordDTO.getPartOfSpeech() != partOfSpeech){

        }else{

        }

        if(oldUsersWordDTO.getTranscription() != transcription){

        }else{

        }

        //удаление старых переводов
        oldUsersWord.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans.getName()));
        oldUsersWordDTO.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans));


        //добавление новых переводов
        if(newTranslations!=null)
        for( String newTranslation: newTranslations){
            if(!oldUsersWordDTO.getTranslations().contains(newTranslation)){
                //добавить новый перевод
                saveNewTranslation(newTranslation, oldUsersWord);
            }
        }

//        if(oldTopics!=null)
//        for( String topic: oldUsersWordDTO.getTopics()){
//
//        }
//
//        if(newTopics!=null)
//        for( String newtopic: newTopics){
//
//
//        }

        usersWordsRepository.save(oldUsersWord);

        return "redirect:/history";
    }






}


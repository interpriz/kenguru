package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersPhrasalVerbsService {


    private final UsersWordsService usersWordsService;
    private final PartsOfSpeechRepository partsOfSpeechRepository;
    private final UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;
    private final PhrasalVerbsRepository phrasalVerbsRepository;
    private final PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;

    private final UserService userService;

    public UsersPhrasalVerbsService(UsersWordsService usersWordsService,
                                    PartsOfSpeechRepository partsOfSpeechRepository,
                                    UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository,
                                    PhrasalVerbsRepository phrasalVerbsRepository,
                                    PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository,
                                    UserService userService) {
        this.usersWordsService = usersWordsService;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersPhrasalVerbsScoresRepository = usersPhrasalVerbsScoresRepository;
        this.phrasalVerbsRepository = phrasalVerbsRepository;
        this.phrasalVerbsTranslationsRepository = phrasalVerbsTranslationsRepository;
        this.userService = userService;
    }

    private void addNewUsersPhrasalVerbTranslation(String newTranslation, UsersPhrasalVerbsScores usersPhrasalVerb){
        if(!newTranslation.isBlank())
        {
            PhrasalVerbsTranslations phrasVerbTranslation= phrasalVerbsTranslationsRepository.findByName(newTranslation);
            if(phrasVerbTranslation==null)
            {
                phrasVerbTranslation = new PhrasalVerbsTranslations(newTranslation);
                phrasalVerbsTranslationsRepository.save(phrasVerbTranslation);
            }

            usersPhrasalVerb.addNewTranslation(phrasVerbTranslation);
        }
    }

    public void addNewPhrasalVerb(
            String wordName,
            String transcription,
            String preposition,
            String translation,
            String description,
            Users usr
    ){
        Users user = userService.loadUserById(usr.getId());

        String partOfSpeech = "verb";
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, user);

        PhrasalVerbs phrasalVerb = phrasalVerbsRepository.findByWpsAndPreposition(wps, preposition);
        if(phrasalVerb==null)
        {
            phrasalVerb = new PhrasalVerbs(preposition,wps);
            phrasalVerbsRepository.save(phrasalVerb);
        }

        UsersPhrasalVerbsScores usersPhrasalVerb= usersPhrasalVerbsScoresRepository.findByUserAndPhrasalVerb(user,phrasalVerb);
        if(usersPhrasalVerb==null)
        {
            usersPhrasalVerb = new UsersPhrasalVerbsScores(0,description,phrasalVerb,user);
            usersPhrasalVerbsScoresRepository.save(usersPhrasalVerb);
        }

        addNewUsersPhrasalVerbTranslation(translation,usersPhrasalVerb);

        usersPhrasalVerbsScoresRepository.save(usersPhrasalVerb);

    }

    public void updateTranslations(UsersPhrasalVerbsScores usersPhrasalVerb, List<String> newTranslations){
        //удаление старых переводов
        usersPhrasalVerb.getTranslations().removeIf(oldTrans -> !newTranslations.contains(oldTrans.getName()));

        //добавление новых переводов
        if (newTranslations!=null)
            for (String newTranslation : newTranslations) {
                if (!usersPhrasalVerb.getListStringTranslations().contains(newTranslation)) {
                    //добавить новый перевод
                    addNewUsersPhrasalVerbTranslation(newTranslation, usersPhrasalVerb);
                }
            }
    }

    public void editPhrasalVerb(
            Long userPhrasalVerbId,
            String wordName,
            String transcription,
            String preposition,
            List<String> newTranslations,
            String description
    ){
        UsersPhrasalVerbsScores oldUsersPhrasalVerb = usersPhrasalVerbsScoresRepository.findUsersPhrasalVerbsScoresById(userPhrasalVerbId);
        UsersPhrasalVerb oldUsersPhrasalVerbDTO = new UsersPhrasalVerb(oldUsersPhrasalVerb);

        //если у глагола изменился предлог
        if(!oldUsersPhrasalVerb.getPhrasalVerb().getPreposition().equals(preposition)){
            WordsPartOfSpeech oldWPS = oldUsersPhrasalVerb.getPhrasalVerb().getWps();
            PhrasalVerbs newPhrasalVerb = new PhrasalVerbs(preposition,oldWPS);
            oldUsersPhrasalVerb.setPhrasalVerb(newPhrasalVerb);
        }

        //если у глагола изменилось описание
        if(!oldUsersPhrasalVerb.getDescription().equals(description)){
            oldUsersPhrasalVerb.setDescription(description);
        }

        //обновление списка переводов
        updateTranslations(oldUsersPhrasalVerb, newTranslations);

        usersPhrasalVerbsScoresRepository.save(oldUsersPhrasalVerb);
    }



    public UsersPhrasalVerbsScores getUsersPhrasalVerbById(Long userPhrasalVerbId){
        return  usersPhrasalVerbsScoresRepository.getById(userPhrasalVerbId);
    }

    public List<UsersPhrasalVerbsScores> getUsersPhrasalVerbByUser(Users usr){
        return  usersPhrasalVerbsScoresRepository.findByUser(usr);
    }
}

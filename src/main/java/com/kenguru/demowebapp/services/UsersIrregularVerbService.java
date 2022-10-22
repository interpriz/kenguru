package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersIrregularVerbService {


    private UsersWordsService usersWordsService;
    private PartsOfSpeechRepository partsOfSpeechRepository;
    private UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository;
    private IrregularVerbsRepository irregularVerbsRepository;

    private UserService userService;

    public UsersIrregularVerbService(UsersWordsService usersWordsService,
                                     PartsOfSpeechRepository partsOfSpeechRepository,
                                     UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository,
                                     IrregularVerbsRepository irregularVerbsRepository,
                                     UserService userService) {
        this.usersWordsService = usersWordsService;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersIrregularVerbsScoresRepository = usersIrregularVerbsScoresRepository;
        this.irregularVerbsRepository = irregularVerbsRepository;
        this.userService = userService;
    }

    public void addNewIrregularVerb(
            String wordName,
            String transcription,
            String secondForm,
            String thirdForm,
            Users usr
    ){
        usr = userService.loadUserById(usr.getId());

        String partOfSpeech = "verb";
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, usr);

        IrregularVerbs irregularVerb = irregularVerbsRepository.findByWps(wps);
        if(irregularVerb==null)
        {
            irregularVerb = new IrregularVerbs(secondForm,thirdForm,wps);
            irregularVerbsRepository.save(irregularVerb);
        }

        UsersIrregularVerbsScores usersIrregularVerb = usersIrregularVerbsScoresRepository.findByUserAndIrregularVerb(usr,irregularVerb);
        if(usersIrregularVerb==null)
        {
            usersIrregularVerb = new UsersIrregularVerbsScores(0,irregularVerb,usr);
            usersIrregularVerbsScoresRepository.save(usersIrregularVerb);
        }
    }

    public List<UsersIrregularVerbsScores> getUsersIrrVerbsByUser(Users usr){
        return usersIrregularVerbsScoresRepository.findByUser(usr);
    }

}

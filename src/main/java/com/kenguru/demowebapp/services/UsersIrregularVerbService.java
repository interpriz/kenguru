package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kenguru.demowebapp.StaticStrings.*;

@Service
public class UsersIrregularVerbService {


    private final UsersWordsService usersWordsService;
    private final PartsOfSpeechRepository partsOfSpeechRepository;
    private final UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository;
    private final IrregularVerbsRepository irregularVerbsRepository;

    private final UserService userService;

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
        Users user = userService.loadUserById(usr.getId());

        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_VERB).get();

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, user);

        /*IrregularVerbs irregularVerb = irregularVerbsRepository.findByWps(wps);
        if(irregularVerb==null)
        {
            irregularVerb = new IrregularVerbs(secondForm,thirdForm,wps);
            irregularVerbsRepository.save(irregularVerb);
        }*/
        IrregularVerbs irregularVerb = irregularVerbsRepository
                .findByWps(wps).orElseGet(
                        ()->{
                            IrregularVerbs newIrregularVerb = new IrregularVerbs(secondForm,thirdForm,wps);
                            irregularVerbsRepository.save(newIrregularVerb);
                            return newIrregularVerb;
                        }
                );



        /*UsersIrregularVerbsScores usersIrregularVerb = usersIrregularVerbsScoresRepository.findByUserAndIrregularVerb(user,irregularVerb);
        if(usersIrregularVerb==null)
        {
            usersIrregularVerb = new UsersIrregularVerbsScores(0,irregularVerb,user);
            usersIrregularVerbsScoresRepository.save(usersIrregularVerb);
        }*/
        UsersIrregularVerbsScores usersIrregularVerb =
                usersIrregularVerbsScoresRepository
                        .findByUserAndIrregularVerb(user,irregularVerb)
                        .orElseGet(
                                ()->{
                                    UsersIrregularVerbsScores newUsersIrregularVerb = new UsersIrregularVerbsScores(0,irregularVerb,user);
                                    usersIrregularVerbsScoresRepository.save(newUsersIrregularVerb);
                                    return newUsersIrregularVerb;
                                }
                        );
    }

    public List<UsersIrregularVerbsScores> getUsersIrrVerbsByUser(Users usr){
        return usersIrregularVerbsScoresRepository
                .findByUser(usr)
                .orElseGet(
                        ArrayList::new
                );
    }

}

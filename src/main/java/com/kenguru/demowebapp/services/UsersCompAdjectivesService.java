package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.ComparativeAdjectivesRepository;
import com.kenguru.demowebapp.repositories.PartsOfSpeechRepository;
import com.kenguru.demowebapp.repositories.UsersComparativeAdjectivesScoresRepository;
import com.kenguru.demowebapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kenguru.demowebapp.StaticStrings.*;

@Service
public class UsersCompAdjectivesService {


    private final UsersWordsService usersWordsService;
    private final PartsOfSpeechRepository partsOfSpeechRepository;
    private final UsersComparativeAdjectivesScoresRepository usersCompAdjectivesScoresRepository;
    private final ComparativeAdjectivesRepository compAdjectivesRepository;

    private final UserService userService;

    public UsersCompAdjectivesService(UsersWordsService usersWordsService,
                                      PartsOfSpeechRepository partsOfSpeechRepository,
                                      UsersComparativeAdjectivesScoresRepository usersCompAdjectivesScoresRepository,
                                      ComparativeAdjectivesRepository compAdjectivesRepository,
                                      UserService userService) {
        this.usersWordsService = usersWordsService;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersCompAdjectivesScoresRepository = usersCompAdjectivesScoresRepository;
        this.compAdjectivesRepository = compAdjectivesRepository;
        this.userService = userService;
    }

    public void addNewComparativeAdjective(
            String wordName,
            String transcription,
            String comparative,
            String superlative,
            Users usr
    ){
        Users user = userService.loadUserById(usr.getId());

        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_ADJECTIVE).get();

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, user);

        /*ComparativeAdjectives compAdjective = compAdjectivesRepository.findByWps(wps);
        if(compAdjective==null)
        {
            compAdjective = new ComparativeAdjectives(comparative,superlative,wps);
            compAdjectivesRepository.save(compAdjective);
        }*/
        ComparativeAdjectives compAdjective = compAdjectivesRepository
                .findByWps(wps).orElseGet(
                        ()->{
                            ComparativeAdjectives newCompAdjective = new ComparativeAdjectives(comparative,superlative,wps);
                            compAdjectivesRepository.save(newCompAdjective);
                            return newCompAdjective;
                        }
                );

        /*UsersComparativeAdjectivesScores usersCompAdjective = usersCompAdjectivesScoresRepository.findByUserAndComparativeAdjective(user,compAdjective);
        if(usersCompAdjective == null)
        {
            usersCompAdjective = new UsersComparativeAdjectivesScores(0,compAdjective,user);
            usersCompAdjectivesScoresRepository.save(usersCompAdjective);
        }*/
        UsersComparativeAdjectivesScores usersCompAdj =
                usersCompAdjectivesScoresRepository
                        .findByUserAndComparativeAdjective(user,compAdjective)
                        .orElseGet(
                                ()->{
                                    UsersComparativeAdjectivesScores newUsersCompAdj = new UsersComparativeAdjectivesScores(0,compAdjective,user);
                                    usersCompAdjectivesScoresRepository.save(newUsersCompAdj);
                                    return newUsersCompAdj;
                                }
                        );

    }

    public List<UsersComparativeAdjectivesScores> getUsersCompAdjecByUser(Users usr){
        return  usersCompAdjectivesScoresRepository
                .findByUser(usr)
                .orElseGet(
                        ArrayList::new
                );
    }

}

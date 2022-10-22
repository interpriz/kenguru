package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.ComparativeAdjectivesRepository;
import com.kenguru.demowebapp.repositories.PartsOfSpeechRepository;
import com.kenguru.demowebapp.repositories.UsersComparativeAdjectivesScoresRepository;
import com.kenguru.demowebapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersCompAdjectivesService {


    private UsersWordsService usersWordsService;
    private PartsOfSpeechRepository partsOfSpeechRepository;
    private UsersComparativeAdjectivesScoresRepository usersCompAdjectivesScoresRepository;
    private ComparativeAdjectivesRepository compAdjectivesRepository;

    public UsersCompAdjectivesService(UsersWordsService usersWordsService,
                                      PartsOfSpeechRepository partsOfSpeechRepository,
                                      UsersComparativeAdjectivesScoresRepository usersCompAdjectivesScoresRepository,
                                      ComparativeAdjectivesRepository compAdjectivesRepository) {
        this.usersWordsService = usersWordsService;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersCompAdjectivesScoresRepository = usersCompAdjectivesScoresRepository;
        this.compAdjectivesRepository = compAdjectivesRepository;
    }

    public void addNewComparativeAdjective(
            String wordName,
            String transcription,
            String comparative,
            String superlative,
            Users usr
    ){
        String partOfSpeech = "adjective";
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);
        //Users usr  = usersRepository.getById(1L);

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, usr);

        ComparativeAdjectives compAdjective = compAdjectivesRepository.findByWps(wps);
        if(compAdjective!=null)
        {
            compAdjective = new ComparativeAdjectives(comparative,superlative,wps);
            compAdjectivesRepository.save(compAdjective);
        }

        UsersComparativeAdjectivesScores usersCompAdjective = usersCompAdjectivesScoresRepository.findByUserAndComparativeAdjective(usr,compAdjective);
        if(usersCompAdjective != null)
        {
            usersCompAdjective = new UsersComparativeAdjectivesScores(0,compAdjective,usr);
            usersCompAdjectivesScoresRepository.save(usersCompAdjective);
        }
    }

    public List<UsersComparativeAdjectivesScores> getUsersCompAdjecByUser(Users usr){
        return  usersCompAdjectivesScoresRepository.findByUser(usr);
    }

}

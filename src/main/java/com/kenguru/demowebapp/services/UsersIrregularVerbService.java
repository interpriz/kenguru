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
    private UsersRepository usersRepository;
    private UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository;
    private IrregularVerbsRepository irregularVerbsRepository;

    public UsersIrregularVerbService(UsersWordsService usersWordsService,
                                     PartsOfSpeechRepository partsOfSpeechRepository,
                                     UsersRepository usersRepository,
                                     UsersIrregularVerbsScoresRepository usersIrregularVerbsScoresRepository,
                                     IrregularVerbsRepository irregularVerbsRepository) {
        this.usersWordsService = usersWordsService;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.usersRepository = usersRepository;
        this.usersIrregularVerbsScoresRepository = usersIrregularVerbsScoresRepository;
        this.irregularVerbsRepository = irregularVerbsRepository;
    }

    public void addNewIrregularVerb(
            String wordName,
            String transcription,
            String secondForm,
            String thirdForm
    ){
        String partOfSpeech = "verb";
        PartsOfSpeech pos = partsOfSpeechRepository.findPartsOfSpeechByName(partOfSpeech);

        Users usr  = usersRepository.getById(1L);

        Words word = usersWordsService.saveOrGetWord(wordName, transcription);

        WordsPartOfSpeech wps = usersWordsService.saveOrGetWPS(pos, word);

        UsersWords uw = usersWordsService.saveOrGetUsersWord(wps, usr);

        IrregularVerbs irregularVerb = irregularVerbsRepository.findByWps(wps);
        if(irregularVerb!=null)
        {
            irregularVerb = new IrregularVerbs(secondForm,thirdForm,wps);
            irregularVerbsRepository.save(irregularVerb);
        }

        UsersIrregularVerbsScores usersIrregularVerb = usersIrregularVerbsScoresRepository.findByUserAndIrregularVerb(usr,irregularVerb);
        if(usersIrregularVerb!=null)
        {
            usersIrregularVerb = new UsersIrregularVerbsScores(0,irregularVerb,usr);
            usersIrregularVerbsScoresRepository.save(usersIrregularVerb);
        }
    }

    public List<UsersIrregularVerbsScores> getUsersIrrVerbsByUser(Users usr){
        return usersIrregularVerbsScoresRepository.findByUser(usr);
    }

}

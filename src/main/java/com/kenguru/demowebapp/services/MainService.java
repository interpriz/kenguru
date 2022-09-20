package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

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
}

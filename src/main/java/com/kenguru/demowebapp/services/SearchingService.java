package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.dto.SearchingObjects;
import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchingService {


    private final WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;

    private final UsersWordsRepository usersWordsRepository;

    private final WordsRepository wordsRepository;

    private final WordsTranslationsRepository wordsTranslationsRepository;

    private final UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;

    private final PhrasalVerbsRepository phrasalVerbsRepository;

    private final PartsOfSpeechRepository partsOfSpeechRepository;

    private final PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;

    public SearchingService(WordsPartOfSpeechRepository wordsPartsOfSpeechRepository,
                            UsersWordsRepository usersWordsRepository,
                            WordsRepository wordsRepository,
                            WordsTranslationsRepository wordsTranslationsRepository,
                            UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository,
                            PhrasalVerbsRepository phrasalVerbsRepository,
                            PartsOfSpeechRepository partsOfSpeechRepository,
                            PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository) {
        this.wordsPartsOfSpeechRepository = wordsPartsOfSpeechRepository;
        this.usersWordsRepository = usersWordsRepository;
        this.wordsRepository = wordsRepository;
        this.wordsTranslationsRepository = wordsTranslationsRepository;
        this.usersPhrasalVerbsScoresRepository = usersPhrasalVerbsScoresRepository;
        this.phrasalVerbsRepository = phrasalVerbsRepository;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.phrasalVerbsTranslationsRepository = phrasalVerbsTranslationsRepository;
    }

    public SearchingObjects findWords(Users usr, String searchStr) {

        List<UsersWords> searchUsersWords = new ArrayList<>();
        List<PhrasalVerbs> searchPhrasalVerbs = new ArrayList<>();
        List<UsersPhrasalVerbsScores> searchUsersPhrasalVerbsScores = new ArrayList<>();

        // поиск по английскому переводу
        if (searchStr.matches("[a-zA-Z]+")) {
            //если введено слово без пробелов
            if (!searchStr.contains(" ")) {
                //поиск слов
                Words wrd = wordsRepository.findByName(searchStr);
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository.findByWord(wrd);
                searchUsersWords = usersWordsRepository.findByUserAndWpsIn(usr, listWPS);
                //поиск фразового глагола со всеми предлогами
                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS));
            } else
            //если введен фразовый глагол с предлогом
            {
                Words wrd = wordsRepository.findByName(searchStr.split(" ")[0]);
                PartsOfSpeech partOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName("verb");
                WordsPartOfSpeech WPS = wordsPartsOfSpeechRepository.findByPartOfSpeechAndWord(partOfSpeech, wrd);
                if (WPS != null)
                    searchPhrasalVerbs.add(phrasalVerbsRepository.findByWpsAndPreposition(WPS, searchStr.split(" ")[1]));
            }

        } else
            // поиск по русскому переводу
            if (searchStr.matches("[а-яА-Я]+")) {
                //поиск слов по переводу
                WordsTranslations wordTranslation = wordsTranslationsRepository.findByName(searchStr);
                if (wordTranslation != null)
                    searchUsersWords.addAll(wordTranslation.getUsersWords());

                //поиск всех фразовых глаголов по переводу глагола
                PartsOfSpeech verb = partsOfSpeechRepository.findPartsOfSpeechByName("verb");
                //поиск всех WPS в searchUsersWords где часть речи - глагол
                List<WordsPartOfSpeech> listWPS = searchUsersWords.stream()
                        .filter(i -> i.getWps().getPartOfSpeech() == verb)
                        .map((i) -> i.getWps())
                        .collect(Collectors.toList());

                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS));

                //поиск фразового глагола по его переводу
                PhrasalVerbsTranslations phrasalVerbsTranslations = phrasalVerbsTranslationsRepository.findByName(searchStr);
                if (phrasalVerbsTranslations != null)
                    searchUsersPhrasalVerbsScores.addAll(phrasalVerbsTranslations.getUsersScores());

            }

        //заполнение списков объектов DTO
        List<UsersWord> searchUsersWordsDTO = new ArrayList<>();
        for (UsersWords userWord : searchUsersWords) {
            searchUsersWordsDTO.add(new UsersWord(userWord));
        }

        searchUsersPhrasalVerbsScores.addAll(
                usersPhrasalVerbsScoresRepository.findByUserAndPhrasalVerbIn(usr, searchPhrasalVerbs));
        List<UsersPhrasalVerb> searchUsersPhrasalVerbsDTO = new ArrayList<>();
        for (UsersPhrasalVerbsScores usersPhrasalVerb : searchUsersPhrasalVerbsScores) {
            searchUsersPhrasalVerbsDTO.add(new UsersPhrasalVerb(usersPhrasalVerb));
        }

        //model.addAttribute("usersWords", searchWords);
        SearchingObjects result = new SearchingObjects(searchUsersWordsDTO, searchUsersPhrasalVerbsDTO);

        return result;
    }
}

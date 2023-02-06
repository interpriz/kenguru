package com.kenguru.demowebapp.services;

import ch.qos.logback.core.util.COWArrayList;
import com.kenguru.demowebapp.dto.SearchingObjects;
import com.kenguru.demowebapp.dto.UsersPhrasalVerb;
import com.kenguru.demowebapp.dto.UsersWord;
import com.kenguru.demowebapp.entities.*;
import com.kenguru.demowebapp.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kenguru.demowebapp.StaticStrings.*;

@Service
public class SearchingService {


    private final WordsPartOfSpeechRepository wordsPartsOfSpeechRepository;

    private final UsersWordsRepository usersWordsRepository;

    private final WordsTranslationsRepository wordsTranslationsRepository;

    private final UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository;

    private final PhrasalVerbsRepository phrasalVerbsRepository;

    private final PartsOfSpeechRepository partsOfSpeechRepository;

    private final PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository;

    private final UsersWordsService usersWordsService;

    //TODO
    // Разбить на 2 функции поиска слов и фразовых глаголов.
    // Убрать SearchingObjects.
    // Сделать 2 endPoints в RestController вместо одного.
    // Разбить search() в header.js на 2 функции, которые вызывать внутри нее.

    public SearchingService(WordsPartOfSpeechRepository wordsPartsOfSpeechRepository,
                            UsersWordsRepository usersWordsRepository,
                            WordsTranslationsRepository wordsTranslationsRepository,
                            UsersPhrasalVerbsScoresRepository usersPhrasalVerbsScoresRepository,
                            PhrasalVerbsRepository phrasalVerbsRepository,
                            PartsOfSpeechRepository partsOfSpeechRepository,
                            PhrasalVerbsTranslationsRepository phrasalVerbsTranslationsRepository, UsersWordsService usersWordsService) {
        this.wordsPartsOfSpeechRepository = wordsPartsOfSpeechRepository;
        this.usersWordsRepository = usersWordsRepository;
        this.wordsTranslationsRepository = wordsTranslationsRepository;
        this.usersPhrasalVerbsScoresRepository = usersPhrasalVerbsScoresRepository;
        this.phrasalVerbsRepository = phrasalVerbsRepository;
        this.partsOfSpeechRepository = partsOfSpeechRepository;
        this.phrasalVerbsTranslationsRepository = phrasalVerbsTranslationsRepository;
        this.usersWordsService = usersWordsService;
    }

    public List<UsersWord> findWords_(Users usr, String searchStr){
        List<UsersWords> searchUsersWords = new ArrayList<>();

        // поиск по английскому переводу
        if (searchStr.matches("[a-zA-Z]+")) {
            //если введено слово без пробелов
            if (!searchStr.contains(" ")) {
                //поиск слов
                //Words wrd = wordsRepository.findByName(searchStr);
                Words wrd = usersWordsService.getWordByName(searchStr);
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository
                        .findByWord(wrd)
                        .orElseGet(ArrayList::new);
                searchUsersWords = usersWordsRepository
                        .findByUserAndWpsIn(usr, listWPS)
                        .orElseGet(ArrayList::new);
            }
        } else
            // поиск слов пользователя по русскому переводу
            if (searchStr.matches("[а-яА-Я]+")) {
                //поиск слов по переводу
                Optional<WordsTranslations> wordTranslation = wordsTranslationsRepository.findByName(searchStr);
                if (wordTranslation.isPresent())
                    searchUsersWords.addAll(
                            usersWordsRepository
                                    .findByUserAndTranslations(usr,wordTranslation.get())
                                    .get()
                    );

            }

        return  searchUsersWords.stream().map(userWords -> new UsersWord(userWords)).collect(Collectors.toList());
    }

    public List<UsersPhrasalVerb> findPhrasalVerbs(Users usr, String searchStr){

        List<PhrasalVerbs> searchPhrasalVerbs = new ArrayList<>();
        List<UsersPhrasalVerbsScores> searchUsersPhrasalVerbsScores = new ArrayList<>();

        // поиск по английскому переводу
        if (searchStr.matches("[a-zA-Z]+")) {
            //если введено слово без пробелов
            if (!searchStr.contains(" ")) {
                //поиск слов
                //Words wrd = wordsRepository.findByName(searchStr);
                Words wrd = usersWordsService.getWordByName(searchStr);
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository
                        .findByWord(wrd)
                        .orElseGet(ArrayList::new);

                //поиск фразового глагола со всеми предлогами
                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS).orElseGet(ArrayList::new));
            } else
            //если введен фразовый глагол с предлогом
            {
                //Words wrd = wordsRepository.findByName(searchStr.split(" ")[0]);
                Words wrd = usersWordsService.getWordByName(searchStr.split(" ")[0]);
                PartsOfSpeech partOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_VERB).get();

                Optional<WordsPartOfSpeech> WPS = wordsPartsOfSpeechRepository
                        .findByPartOfSpeechAndWord(partOfSpeech, wrd);
                WPS.ifPresent(
                        (wps) -> {
                            //TODO переделать get()
                            searchPhrasalVerbs.add(
                                    phrasalVerbsRepository
                                            .findByWpsAndPreposition(wps, searchStr.split(" ")[1])
                                            .get()
                            );
                        }
                );

            }

        } else
            // поиск по русскому переводу
            if (searchStr.matches("[а-яА-Я]+")) {
                //поиск слов по переводу
                //TODO переделать get()
               /* Optional<WordsTranslations> wordTranslation = wordsTranslationsRepository.findByName(searchStr);
                if (wordTranslation.isPresent())
                    searchUsersWords.addAll(wordTranslation.get().getUsersWords());*/

                List<UsersWords> searchUsersWords = new ArrayList<>();
                wordsTranslationsRepository.findByName(searchStr).ifPresent(
                        (translation)->{
                            searchUsersWords.addAll(translation.getUsersWords());
                        }
                );

                //поиск всех фразовых глаголов по переводу глагола
                PartsOfSpeech verb = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_VERB).get();
                //поиск всех WPS в searchUsersWords где часть речи - глагол
                List<WordsPartOfSpeech> listWPS = searchUsersWords.stream()
                        .filter(i -> i.getWps().getPartOfSpeech() == verb)
                        .map((i) -> i.getWps())
                        .collect(Collectors.toList());

                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS).orElseGet(ArrayList::new));

                //поиск фразовых глаголов пользователя по их переводу
                /*PhrasalVerbsTranslations phrasalVerbsTranslations = phrasalVerbsTranslationsRepository.findByName(searchStr).get();
                if (phrasalVerbsTranslations != null)
                    searchUsersPhrasalVerbsScores.addAll(phrasalVerbsTranslations.getUsersScores());*/
                phrasalVerbsTranslationsRepository.findByName(searchStr).ifPresent(
                        phrasVerbTranslation -> {
                            searchUsersPhrasalVerbsScores
                                    .addAll(
                                            usersPhrasalVerbsScoresRepository
                                                    .findByUserAndTranslations(usr,phrasVerbTranslation)
                                                    .get()
                                    );
                        }
                );



            }

        searchUsersPhrasalVerbsScores.addAll(
                usersPhrasalVerbsScoresRepository
                        .findByUserAndPhrasalVerbIn(usr, searchPhrasalVerbs)
                        .orElseGet(ArrayList::new)
        );

        return  searchUsersPhrasalVerbsScores.stream().map(usersPhrasalVerb -> new UsersPhrasalVerb(usersPhrasalVerb)).collect(Collectors.toList());
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
                //Words wrd = wordsRepository.findByName(searchStr);
                Words wrd = usersWordsService.getWordByName(searchStr);
                List<WordsPartOfSpeech> listWPS = wordsPartsOfSpeechRepository
                        .findByWord(wrd)
                        .orElseGet(ArrayList::new);
                searchUsersWords = usersWordsRepository
                        .findByUserAndWpsIn(usr, listWPS)
                        .orElseGet(ArrayList::new);
                //поиск фразового глагола со всеми предлогами
                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS).orElseGet(ArrayList::new));
            } else
            //если введен фразовый глагол с предлогом
            {
                //Words wrd = wordsRepository.findByName(searchStr.split(" ")[0]);
                Words wrd = usersWordsService.getWordByName(searchStr.split(" ")[0]);
                PartsOfSpeech partOfSpeech = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_VERB).get();

                Optional<WordsPartOfSpeech> WPS = wordsPartsOfSpeechRepository
                        .findByPartOfSpeechAndWord(partOfSpeech, wrd);
                WPS.ifPresent(
                        (wps) -> {
                            //TODO переделать get()
                            searchPhrasalVerbs.add(
                                    phrasalVerbsRepository
                                            .findByWpsAndPreposition(wps, searchStr.split(" ")[1])
                                            .get()
                            );
                        }
                );

            }

        } else
            // поиск по русскому переводу
            if (searchStr.matches("[а-яА-Я]+")) {
                //поиск слов по переводу
                //TODO переделать get()
                Optional<WordsTranslations> wordTranslation = wordsTranslationsRepository.findByName(searchStr);
                if (wordTranslation.isPresent())
                    searchUsersWords.addAll(wordTranslation.get().getUsersWords());



                //поиск всех фразовых глаголов по переводу глагола
                PartsOfSpeech verb = partsOfSpeechRepository.findPartsOfSpeechByName(PART_OF_SPEECH_VERB).get();
                //поиск всех WPS в searchUsersWords где часть речи - глагол
                List<WordsPartOfSpeech> listWPS = searchUsersWords.stream()
                        .filter(i -> i.getWps().getPartOfSpeech() == verb)
                        .map((i) -> i.getWps())
                        .collect(Collectors.toList());

                searchPhrasalVerbs.addAll(phrasalVerbsRepository.findByWpsIn(listWPS).orElseGet(ArrayList::new));

                //поиск фразового глагола по его переводу
                /*PhrasalVerbsTranslations phrasalVerbsTranslations = phrasalVerbsTranslationsRepository.findByName(searchStr).get();
                if (phrasalVerbsTranslations != null)
                    searchUsersPhrasalVerbsScores.addAll(phrasalVerbsTranslations.getUsersScores());*/
                phrasalVerbsTranslationsRepository.findByName(searchStr).ifPresent(
                        phrasVerbtranslation -> {
                            searchUsersPhrasalVerbsScores.addAll(phrasVerbtranslation.getUsersScores());
                        }
                );


            }

        //заполнение списков объектов DTO
        List<UsersWord> searchUsersWordsDTO = new ArrayList<>();
        for (UsersWords userWord : searchUsersWords) {
            searchUsersWordsDTO.add(new UsersWord(userWord));
        }

        searchUsersPhrasalVerbsScores.addAll(
                usersPhrasalVerbsScoresRepository
                        .findByUserAndPhrasalVerbIn(usr, searchPhrasalVerbs)
                        .orElseGet(ArrayList::new)
        );
        List<UsersPhrasalVerb> searchUsersPhrasalVerbsDTO = new ArrayList<>();
        for (UsersPhrasalVerbsScores usersPhrasalVerb : searchUsersPhrasalVerbsScores) {
            searchUsersPhrasalVerbsDTO.add(new UsersPhrasalVerb(usersPhrasalVerb));
        }

        //model.addAttribute("usersWords", searchWords);
        SearchingObjects result = new SearchingObjects(searchUsersWordsDTO, searchUsersPhrasalVerbsDTO);

        return result;
    }
}

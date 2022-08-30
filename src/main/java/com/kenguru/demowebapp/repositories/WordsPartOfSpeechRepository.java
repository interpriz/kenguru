package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PartsOfSpeech;
import com.kenguru.demowebapp.entities.Words;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsPartOfSpeechRepository extends JpaRepository<WordsPartOfSpeech, Long> {
    List<WordsPartOfSpeech> findWordsPartOfSpeechByPartOfSpeechAndWord(PartsOfSpeech pos, Words word);
    List<WordsPartOfSpeech> findByWord(Words word);
}

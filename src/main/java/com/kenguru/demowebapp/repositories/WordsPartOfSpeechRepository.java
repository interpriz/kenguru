package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PartsOfSpeech;
import com.kenguru.demowebapp.entities.Words;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordsPartOfSpeechRepository extends JpaRepository<WordsPartOfSpeech, Long> {
    Optional<WordsPartOfSpeech> findByPartOfSpeechAndWord(PartsOfSpeech pos, Words word);
    Optional<List<WordsPartOfSpeech>> findByWord(Words word);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PhrasalVerbs;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhrasalVerbsRepository extends JpaRepository<PhrasalVerbs, Long> {

    Optional<PhrasalVerbs> findByWpsAndPreposition(WordsPartOfSpeech wps, String preposition);

    Optional<List<PhrasalVerbs>> findByWpsIn(List<WordsPartOfSpeech> listWPS);
}

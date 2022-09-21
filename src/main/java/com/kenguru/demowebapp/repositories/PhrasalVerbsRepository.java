package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PhrasalVerbs;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhrasalVerbsRepository extends JpaRepository<PhrasalVerbs, Long> {

    PhrasalVerbs findByWpsAndPreposition(WordsPartOfSpeech wps, String preposition);

    List<PhrasalVerbs> findByWpsIn(List<WordsPartOfSpeech> listWPS);
}

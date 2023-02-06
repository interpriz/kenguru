package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.IrregularVerbs;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IrregularVerbsRepository extends JpaRepository<IrregularVerbs, Long> {
    Optional<IrregularVerbs> findByWps(WordsPartOfSpeech wps);
}

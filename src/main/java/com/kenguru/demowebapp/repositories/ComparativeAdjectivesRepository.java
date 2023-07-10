package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.ComparativeAdjectives;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComparativeAdjectivesRepository extends JpaRepository<ComparativeAdjectives,Long> {
    Optional<ComparativeAdjectives> findByWps(WordsPartOfSpeech wps);
}

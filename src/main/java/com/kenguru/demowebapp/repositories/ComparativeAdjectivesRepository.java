package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.ComparativeAdjectives;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComparativeAdjectivesRepository extends JpaRepository<ComparativeAdjectives,Long> {
    ComparativeAdjectives findByWps(WordsPartOfSpeech wps);
}

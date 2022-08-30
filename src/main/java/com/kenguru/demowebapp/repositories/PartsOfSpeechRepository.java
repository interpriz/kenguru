package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PartsOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartsOfSpeechRepository extends JpaRepository<PartsOfSpeech, Long>{
    PartsOfSpeech findPartsOfSpeechByName(String name);
}

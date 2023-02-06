package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PartsOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartsOfSpeechRepository extends JpaRepository<PartsOfSpeech, Long>{
    Optional<PartsOfSpeech> findPartsOfSpeechByName(String name);
}

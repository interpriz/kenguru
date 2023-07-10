package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordsRepository extends JpaRepository<Words, Long> {

    Optional<Words> findByNameAndTranscription(String name, String transcr);

    Optional<Words> findByName(String name);
}

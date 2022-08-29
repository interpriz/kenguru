package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsRepository extends JpaRepository<Words, Long> {
    List<Words> findWordsByName(String name);
    Words findByName(String name);
}

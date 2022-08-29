package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Comparative_adjectives;
import com.kenguru.demowebapp.entities.Words_part_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Comparative_adjectivesRepository extends JpaRepository<Comparative_adjectives,Long> {
    List<Comparative_adjectives> findComparative_adjectivesByWps(Words_part_of_speech wps);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Phrasal_verbs;
import com.kenguru.demowebapp.entities.Words_part_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Phrasal_verbsRepository extends JpaRepository<Phrasal_verbs, Long> {

    List<Phrasal_verbs> findPhrasal_verbsByWpsAndPreposition(Words_part_of_speech wps, String preposition);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Irregular_verbs;
import com.kenguru.demowebapp.entities.Words_part_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Irregular_verbsRepository extends JpaRepository<Irregular_verbs, Long> {
    List<Irregular_verbs> findIrregular_verbsByWps(Words_part_of_speech wps);
}

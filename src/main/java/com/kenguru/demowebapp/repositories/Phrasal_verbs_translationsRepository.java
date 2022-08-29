package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface Phrasal_verbs_translationsRepository extends JpaRepository<Phrasal_verbs_translations, Long> {
    List<Phrasal_verbs_translations> findPhrasal_verbs_translationsByName(String name);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhrasalVerbsTranslationsRepository extends JpaRepository<PhrasalVerbsTranslations, Long> {
    List<PhrasalVerbsTranslations> findByName(String name);

}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhrasalVerbsTranslationsRepository extends JpaRepository<PhrasalVerbsTranslations, Long> {
    Optional<PhrasalVerbsTranslations> findByName(String name);

}

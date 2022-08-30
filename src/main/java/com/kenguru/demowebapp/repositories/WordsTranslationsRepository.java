package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.UsersWords;
import com.kenguru.demowebapp.entities.WordsTranslations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WordsTranslationsRepository extends JpaRepository<WordsTranslations, Long> {

    List<WordsTranslations> findDistinctWords_translationsByUwIn(Collection<UsersWords> uw);
    List<WordsTranslations> findWordsTranslationsByName(String name);
    WordsTranslations findByName(String name);
}

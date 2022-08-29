package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Users_words;
import com.kenguru.demowebapp.entities.Words_translations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface Words_translationsRepository extends JpaRepository<Words_translations, Long> {

    List<Words_translations> findDistinctWords_translationsByUwIn(Collection<Users_words> uw);
    List<Words_translations> findWords_translationsByName(String name);
    Words_translations findByName(String name);
}

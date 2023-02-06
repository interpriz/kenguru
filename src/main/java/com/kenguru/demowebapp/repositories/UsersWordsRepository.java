package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersWords;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import com.kenguru.demowebapp.entities.WordsTranslations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface UsersWordsRepository extends JpaRepository<UsersWords, Long> {
    Optional<List<UsersWords>> findByUser(Users usr);
    Optional<UsersWords> findByUserAndWps(Users usr, WordsPartOfSpeech wps);
    Optional<List<UsersWords>> findByUserAndWpsIn(Users usr, List<WordsPartOfSpeech> wps);
    Optional<UsersWords> findUsersWordsById(Long id);
    Optional<List<UsersWords>> findByUserAndTranslations(Users usr, WordsTranslations translation);
}


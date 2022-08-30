package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersWords;
import com.kenguru.demowebapp.entities.WordsPartOfSpeech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersWordsRepository extends JpaRepository<UsersWords, Long> {
    List<UsersWords> findByUser(Users usr);
    List<UsersWords> findUsersWordsByUserAndWps(Users usr, WordsPartOfSpeech wps);
    List<UsersWords> findByUserAndWpsIn(Users usr, List<WordsPartOfSpeech> wps);
    UsersWords findUsersWordsById(Long id);
}


package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.Users_words;
import com.kenguru.demowebapp.entities.Words_part_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_wordsRepository extends JpaRepository<Users_words, Long> {
    List<Users_words> findByUser(Users usr);
    List<Users_words> findUsers_wordsByUserAndWps(Users usr, Words_part_of_speech wps);
    List<Users_words> findByUserAndWpsIn(Users usr, List<Words_part_of_speech> wps);
    Users_words findUsers_wordsById(Long id);
}


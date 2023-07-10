package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PhrasalVerbs;
import com.kenguru.demowebapp.entities.PhrasalVerbsTranslations;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersPhrasalVerbsScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersPhrasalVerbsScoresRepository extends JpaRepository<UsersPhrasalVerbsScores, Long> {
    Optional<List<UsersPhrasalVerbsScores>> findByUser(Users usr);
    Optional<UsersPhrasalVerbsScores> findUsersPhrasalVerbsScoresById(Long id);
    Optional<UsersPhrasalVerbsScores> findByUserAndPhrasalVerb(Users usr, PhrasalVerbs pv);
    Optional<List<UsersPhrasalVerbsScores>> findByUserAndPhrasalVerbIn(Users usr, List<PhrasalVerbs> lstPhrasalVerbs);
    Optional<List<UsersPhrasalVerbsScores>> findByUserAndTranslations(Users usr, PhrasalVerbsTranslations translation);
}

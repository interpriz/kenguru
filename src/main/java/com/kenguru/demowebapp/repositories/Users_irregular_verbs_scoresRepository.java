package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Irregular_verbs;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.Users_irregular_verbs_scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Users_irregular_verbs_scoresRepository extends JpaRepository<Users_irregular_verbs_scores, Long> {
    List<Users_irregular_verbs_scores> findByUser(Users usr);
    List<Users_irregular_verbs_scores> findByUserAndIrregularVerb(Users usr, Irregular_verbs irregularVerb);
}

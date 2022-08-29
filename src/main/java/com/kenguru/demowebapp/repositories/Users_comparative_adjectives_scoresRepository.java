package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Comparative_adjectives;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.Users_comparative_adjectives_scores;
import com.kenguru.demowebapp.entities.Users_irregular_verbs_scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Users_comparative_adjectives_scoresRepository extends JpaRepository<Users_comparative_adjectives_scores, Long> {

    List<Users_comparative_adjectives_scores> findByUser(Users usr);
    List<Users_comparative_adjectives_scores> findByUserAndComparativeAdjective(Users usr, Comparative_adjectives comparativeAdjective);
}

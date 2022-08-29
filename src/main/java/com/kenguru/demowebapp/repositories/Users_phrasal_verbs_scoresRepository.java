package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Phrasal_verbs;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.Users_phrasal_verbs_scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Users_phrasal_verbs_scoresRepository extends JpaRepository<Users_phrasal_verbs_scores, Long> {
    List<Users_phrasal_verbs_scores> findByUser(Users usr);
    List<Users_phrasal_verbs_scores> findUsers_phrasal_verbsByUserAndPhrasalVerb(Users usr, Phrasal_verbs pv);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.PhrasalVerbs;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersPhrasalVerbsScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersPhrasalVerbsScoresRepository extends JpaRepository<UsersPhrasalVerbsScores, Long> {
    List<UsersPhrasalVerbsScores> findByUser(Users usr);
    UsersPhrasalVerbsScores findUsersPhrasalVerbsScoresById(Long id);
    UsersPhrasalVerbsScores findByUserAndPhrasalVerb(Users usr, PhrasalVerbs pv);
    List<UsersPhrasalVerbsScores> findByUserAndPhrasalVerbIn(Users usr, List<PhrasalVerbs> lstPhrasalVerbs);
}

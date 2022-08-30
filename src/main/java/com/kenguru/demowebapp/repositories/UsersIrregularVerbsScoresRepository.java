package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.IrregularVerbs;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersIrregularVerbsScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersIrregularVerbsScoresRepository extends JpaRepository<UsersIrregularVerbsScores, Long> {
    List<UsersIrregularVerbsScores> findByUser(Users usr);
    List<UsersIrregularVerbsScores> findByUserAndIrregularVerb(Users usr, IrregularVerbs irregularVerb);
}

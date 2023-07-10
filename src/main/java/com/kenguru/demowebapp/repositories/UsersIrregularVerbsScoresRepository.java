package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.IrregularVerbs;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersIrregularVerbsScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersIrregularVerbsScoresRepository extends JpaRepository<UsersIrregularVerbsScores, Long> {
    Optional<List<UsersIrregularVerbsScores>> findByUser(Users usr);
    Optional<UsersIrregularVerbsScores> findByUserAndIrregularVerb(Users usr, IrregularVerbs irregularVerb);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.ComparativeAdjectives;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersComparativeAdjectivesScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersComparativeAdjectivesScoresRepository extends JpaRepository<UsersComparativeAdjectivesScores, Long> {

    List<UsersComparativeAdjectivesScores> findByUser(Users usr);
    List<UsersComparativeAdjectivesScores> findByUserAndComparativeAdjective(Users usr, ComparativeAdjectives comparativeAdjective);
}

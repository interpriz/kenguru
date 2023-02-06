package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.ComparativeAdjectives;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.entities.UsersComparativeAdjectivesScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersComparativeAdjectivesScoresRepository extends JpaRepository<UsersComparativeAdjectivesScores, Long> {

    Optional<List<UsersComparativeAdjectivesScores>> findByUser(Users usr);

    Optional<UsersComparativeAdjectivesScores> findByUserAndComparativeAdjective(Users usr, ComparativeAdjectives comparativeAdjective);
}

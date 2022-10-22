package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Topics;
import com.kenguru.demowebapp.entities.UsersWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
    List<Topics> findDistinctTopByUsersWordsIn(Collection<UsersWords> uw);
    Topics findByName(String name);
}

package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Parts_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Parts_of_speechRepository extends JpaRepository<Parts_of_speech, Long>{
    Parts_of_speech findParts_of_speechByName(String name);
}

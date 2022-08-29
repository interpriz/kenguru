package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Parts_of_speech;
import com.kenguru.demowebapp.entities.Words;
import com.kenguru.demowebapp.entities.Words_part_of_speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Words_part_of_speechRepository  extends JpaRepository<Words_part_of_speech, Long> {
    List<Words_part_of_speech> findWords_part_of_speechByPartOfSpeechAndWord(Parts_of_speech pos, Words word);
    List<Words_part_of_speech> findByWord( Words word);
}

package com.kenguru.demowebapp.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "words")
public class Words {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "transcription")
    private String transcription;

    @OneToMany(mappedBy = "word")
    private Set<Words_part_of_speech> parts_of_speeches;



    public Words() {
    }

    public Words(String name, String transcription) {
        this.name = name;
        this.transcription = transcription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public Set<Words_part_of_speech> getParts_of_speeches() {
        return parts_of_speeches;
    }

    public void setParts_of_speeches(Set<Words_part_of_speech> part_of_speeches) {
        this.parts_of_speeches = part_of_speeches;
    }
}

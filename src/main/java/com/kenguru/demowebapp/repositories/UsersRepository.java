package com.kenguru.demowebapp.repositories;

import com.kenguru.demowebapp.entities.Topics;
import com.kenguru.demowebapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}

package com.cognizant.Backend.repository;

import com.cognizant.Backend.entity.Session;
import com.cognizant.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionIdRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByUser(User user);
}

package com.cognizant.Backend.service;

import com.cognizant.Backend.entity.Session;
import com.cognizant.Backend.entity.User;
import com.cognizant.Backend.repository.SessionIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionIdService {

    private SessionIdRepository sessionIdRepository;

    public void save(Session session){
        sessionIdRepository.save(session);
    }
    public Optional<Session> findByUserId(User user){
       return sessionIdRepository.findByUser(user);
    }

    public void delete(Session session) {
        sessionIdRepository.delete(session);
    }
}

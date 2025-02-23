package com.cognizant.Backend.service;

import com.cognizant.Backend.entity.Session;
import com.cognizant.Backend.entity.User;
import com.cognizant.Backend.repository.UserRepository;
import com.cognizant.Backend.entity.UserDetails;
import com.cognizant.Backend.dto.RegisterDto;
import com.cognizant.Backend.exception.DuplicateException;
import com.cognizant.Backend.exception.InvalidDataException;
import com.cognizant.Backend.mapper.UserDetailsMapper;
import com.cognizant.Backend.mapper.UserMapper;
import com.cognizant.Backend.repository.UserDetailsRepository;
import com.cognizant.Backend.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final SessionIdService sessionIdService;
    private final PasswordEncoder passwordEncoder;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Session login(String loginEmail, String loginPassword) throws Exception {


        //Searching if user exists in DB based on the received email in body of the request
        Optional<User> user = userRepository.findByEmail(loginEmail);
        if (user.isPresent()) {//If user is present we get the encoded password from DB
            Session session;
            String userPassword = user.get().getPassword();

            if (bCryptPasswordEncoder.matches(loginPassword, userPassword)) {

                Optional<Session> existingSessionId = sessionIdService.findByUserId(user.get());
                if (existingSessionId.isPresent()) {
                    if (existingSessionId.get().getExpirationTime().isAfter(LocalDateTime.now())) {
                        return existingSessionId.get();
                    } else {
                        sessionIdService.delete(existingSessionId.get());
                        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(360);
                        session = new Session(UUID.randomUUID(), expirationDate, user.get());
                        sessionIdService.save(session);
                        return session;
                    }
                } else {
                    LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(360);
                    session = new Session(UUID.randomUUID(), expirationDate, user.get());
                    sessionIdService.save(session);
                    return session;
                }
            } else {
                throw new Exception("Invalid password!");
            }
        }
        throw new Exception("User not found!");
    }

    public void saveUser(RegisterDto registerDto) throws InvalidDataException, DuplicateException {
        UserValidator.validateRegister(registerDto);

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new DuplicateException("Email is already in use!");
        }

        if (userDetailsRepository.existsByPhoneNumber(registerDto.getPhoneNumber())) {
            throw new DuplicateException("Phone number already in use!");
        }

        User user = UserMapper.toUser(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        UserDetails userDetails = userDetailsRepository.save(UserDetailsMapper.toUserDetails(registerDto));
        userDetails.setUser(user);
    }
}
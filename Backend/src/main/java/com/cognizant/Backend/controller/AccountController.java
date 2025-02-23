package com.cognizant.Backend.controller;

import com.cognizant.Backend.dto.RegisterDto;
import com.cognizant.Backend.exception.DuplicateException;
import com.cognizant.Backend.exception.InvalidDataException;
import com.cognizant.Backend.service.UserService;
import com.cognizant.Backend.entity.Session;
import com.cognizant.Backend.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;
import java.time.LocalDateTime;

import java.util.Collections;

@AllArgsConstructor
@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api")
public class AccountController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        try {
            userService.saveUser(registerDto);
        }
        catch (InvalidDataException | DuplicateException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", exception.getMessage()));
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Account created successfully!"));
    }
   @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        try{
                Session session = userService.login(loginDTO.getEmail(), loginDTO.getPassword());

                Cookie sessionCookie = new Cookie("sessionID", session.getId().toString());
                Long cookieAge = Duration.between(LocalDateTime.now(), session.getExpirationTime()).getSeconds();
                sessionCookie.setHttpOnly(true);
                sessionCookie.setPath("/");
                sessionCookie.setMaxAge(cookieAge.intValue());
                response.addCookie(sessionCookie);

                return ResponseEntity.ok(session.getUser().getRole());

        } catch(Exception e){
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(e.getMessage());
        }
  }
}

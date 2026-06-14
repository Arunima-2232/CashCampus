package com.expanse.expanse.manager.controller;

import com.expanse.expanse.manager.dto.UserLoginRequestDto;
import com.expanse.expanse.manager.dto.UserRegisterRequestDto;
import com.expanse.expanse.manager.entity.User;
import com.expanse.expanse.manager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/user")

    public class UserController{

        @Autowired
        UserService service;


        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userCred)
        {
            return service.login(userCred);
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequestDto userCred)
        {
            return service.register(userCred);
        }

        @PostMapping("/logout/{userId}")
        public ResponseEntity<?> logout()
        {
            String userId= SecurityContextHolder.getContext().getAuthentication().getName();
            return service.logout(userId);
        }

        @PostMapping("/allUsers")
        public List<User> allUsers()
        {
            return service.allUsers();
        }

    }

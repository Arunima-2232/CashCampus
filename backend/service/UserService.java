package com.expanse.expanse.manager.service;

import com.expanse.expanse.manager.dto.UserLoginRequestDto;
import com.expanse.expanse.manager.dto.UserRegisterRequestDto;
import com.expanse.expanse.manager.dto.UserResponseDto;
import com.expanse.expanse.manager.entity.User;
import com.expanse.expanse.manager.exception.*;
import com.expanse.expanse.manager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<User> allUsers()
    {
        return userRepo.findAll();
    }

    public ResponseEntity<?> register(UserRegisterRequestDto userCreds)
    {
        User user=new User();
        user.setEmailId(userCreds.getEmailId());
        user.setPassword(passwordEncoder.encode(userCreds.getPassword()));
        user.setName(userCreds.getName());
        user.setMobileNumber(userCreds.getMobileNumber());
        user.setRole(userCreds.getRole());
        User targetUser=userRepo.findById(user.getEmailId()).orElse(null);
        if(targetUser!=null)
            throw new UserAlreadyExistsException("User already exists, please login.");
        try{
            userRepo.save(user);
            return ResponseEntity.ok("User registered.");
        }
        catch(DBException e){
            throw new DBException("Database Exception");
        }
    }

    public ResponseEntity<?> login(UserLoginRequestDto userCreds)
    {
        User user=new User();
        user.setEmailId(userCreds.getEmailId());
        user.setPassword(userCreds.getPassword());

        try {
            Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmailId(),user.getPassword());
            Authentication auth = authenticationManager.authenticate(authentication);

            String token=jwtService.generateToken(user.getEmailId());

            User targetEx=userRepo.findById(userCreds.getEmailId()).orElse(null);
            UserResponseDto loggedUser=new UserResponseDto();
            loggedUser.setEmailId(targetEx.getEmailId());
            loggedUser.setName(targetEx.getName());
            loggedUser.setMobileNumber(targetEx.getMobileNumber());
            loggedUser.setRole(targetEx.getRole());
            loggedUser.setToken(token);
            return ResponseEntity.ok(loggedUser);

        } catch (BadCredentialsException ex){
            throw new WrongPasswordException("Wrong password");
        }

    }

    public ResponseEntity<?> logout(String userId)
    {
        User user=userRepo.findById(userId).orElse(null);
        if(user==null)
            throw new UserNotFoundException("User Not Found.");
        userRepo.save(user);
        return ResponseEntity.ok("Successfully logged out.");
    }
}

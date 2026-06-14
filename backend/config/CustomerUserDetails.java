package com.expanse.expanse.manager.config;

import com.expanse.expanse.manager.entity.User;
import com.expanse.expanse.manager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerUserDetails implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findById(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException("User not found");
        List<GrantedAuthority> authorities =  List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return  new org.springframework.security.core.userdetails.User(user.getEmailId(),user.getPassword(),authorities);
    }
}
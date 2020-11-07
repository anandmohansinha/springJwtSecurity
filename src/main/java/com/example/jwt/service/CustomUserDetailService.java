package com.example.jwt.service;

import com.example.jwt.entity.jwt.User;
import com.example.jwt.entity.jwt.UsersRoles;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * we wrote this class to get the data from Database
 */
// It is used by the DaoAuthenticationProvider to load details about the user during authentication.
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("******************************* CustomUserDetailService loadUserByUsername START");
        Optional<User> user = repository.findByUsername(s);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // here we are returning Spring User class to validate user details.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(user.isPresent()){
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(), authorities);
        }
        return null;
    }
}

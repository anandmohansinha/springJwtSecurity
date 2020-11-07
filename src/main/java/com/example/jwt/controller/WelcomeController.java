package com.example.jwt.controller;

import com.example.jwt.entity.AuthRequest;
import com.example.jwt.entity.jwt.MinimalJWTUser;
import com.example.jwt.entity.jwt.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome(){
        return  "Welcome to admin channel";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        Optional<User> user = this.userRepository.findByUsername(authRequest.getUsername());
        if(user.isPresent()){
            try {
                // here we are permitting this request without token. So we have to explicitly call  authenticationManager.authenticate() method
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(authRequest.getPassword());
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            }catch (Exception e){
                throw new Exception("Invalid username and password");
            }
           /* MinimalJWTUser jwtUser = new MinimalJWTUser(authRequest.getUsername(),
                                                        user.get().getUsersRoleses().stream()
                                                            .map(u ->u.getRole()).collect(Collectors.toSet())
                                                        );*/
            return ResponseEntity.ok(jwtUtil.generateToken(user.get().getUsername()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}

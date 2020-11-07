package com.example.jwt;

import com.example.jwt.entity.jwt.User;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringjwtApplication {

	// This is particular Entity eg. User
	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringjwtApplication.class, args);
	}


	@PostConstruct
	public void initUsers(){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<User> users = Stream.of(new User(101, "xadmin",passwordEncoder.encode("xpassword"), "xadmin@gmail.com" ),
									new User(102, "anand",passwordEncoder.encode("anandpassword"), "anand@gmail.com" ),
									new User(103, "mohan",passwordEncoder.encode("mohanpassword"), "mohan@gmail.com" ),
									new User(104, "neha",passwordEncoder.encode("nehapassword"), "neha@gmail.com" )
									).collect(Collectors.toList());
				repository.saveAll(users);
	}

}

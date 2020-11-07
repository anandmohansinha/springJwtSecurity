package com.example.jwt.entity.jwt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_tbl")
public class User {
    @Id
    private int id;
    private String username;
    private String password;
    private String email;
   // private Set<UsersRoles> usersRoleses = new HashSet<>(0);

    public User() {}

    public User(int id, String userName, String password, String email) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.jwt.entity.jwt;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
public class UsersRoles {
    private Integer userRoleId;
    private User users;
    private String role;

    public UsersRoles() {
    }

    public UsersRoles(User users, String role) {
        this.users = users;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username")
    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    @Column(name="role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

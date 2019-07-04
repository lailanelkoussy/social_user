package com.social.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Table(name = "user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    private String email;

    private String username;

    @Column(nullable = false)
    private String password;

    private boolean active;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    private Calendar birthday;

    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns ={@JoinColumn(name = "following_user_id")} )
    private List<User> following;



}

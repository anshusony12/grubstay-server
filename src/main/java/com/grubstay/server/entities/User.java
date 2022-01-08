package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private long phone;
    private long whatsapp;
    private String password;
    private Date dob;
    private boolean enabled = true;

    // user has many roles
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<UserRoles> userRoles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private UserIdProof userIdProof;

}

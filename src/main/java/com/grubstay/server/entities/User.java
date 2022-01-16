package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private String roles;

    // user has many roles
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private Set<UserRoles> userRoles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private UserIdProof userIdProof;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(long whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    public UserIdProof getUserIdProof() {
        return userIdProof;
    }

    public void setUserIdProof(UserIdProof userIdProof) {
        this.userIdProof = userIdProof;
    }

    public String getRoles() { return roles; }

    public void setRoles(String roles) { this.roles = roles;}

    public User() {
    }

    public User(String username, String email, String firstName, String lastName, String gender, long phone, long whatsapp, String password, Date dob, boolean enabled, Set<UserRoles> userRoles, UserIdProof userIdProof) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.password = password;
        this.dob = dob;
        this.enabled = enabled;
        this.userRoles = userRoles;
        this.userIdProof = userIdProof;
    }

    public User(long userId, String username, String email, String firstName, String lastName, String gender, long phone, long whatsapp, String password, Date dob, boolean enabled, Set<UserRoles> userRoles, UserIdProof userIdProof) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.password = password;
        this.dob = dob;
        this.enabled = enabled;
        this.userRoles = userRoles;
        this.userIdProof = userIdProof;
    }
}

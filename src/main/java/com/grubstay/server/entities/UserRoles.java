package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @ManyToOne
    private Role role;

    @Override
    public String toString() {
        return "UserRoles{" +
                "userRoleId=" + userRoleId +
                '}';
    }
}

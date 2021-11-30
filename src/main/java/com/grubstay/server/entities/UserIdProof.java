package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserIdProof {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userProofId;

    private String proofName;
    private String frontPic;
    private String backPic;

    @OneToOne
    private User user;
}

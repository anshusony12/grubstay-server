package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class StayGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long galId;

    private String galName;

    private String stayId;
}

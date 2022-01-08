package com.grubstay.server.services;

import com.grubstay.server.entities.LandMarks;

import java.util.List;

public interface LandMarkService {
    public LandMarks findLandMarksByLandMarkNameAndPgStayId(String landMarkName, String pgId);
    public List deleteLandMarkById(int landMarkId) throws Exception;
}

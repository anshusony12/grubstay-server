package com.grubstay.server.repos;

import com.grubstay.server.entities.LandMarks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PGLandMarksRepository extends JpaRepository<LandMarks, Long> {
}

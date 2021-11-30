package com.grubstay.server.repos;

import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserIdProof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIdProofRepository extends JpaRepository<UserIdProof,Long> {
}

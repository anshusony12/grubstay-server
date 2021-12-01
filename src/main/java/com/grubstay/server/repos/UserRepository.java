package com.grubstay.server.repos;

import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserIdProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

}

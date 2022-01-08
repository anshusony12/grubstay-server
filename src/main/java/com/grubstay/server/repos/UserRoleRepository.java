package com.grubstay.server.repos;

import com.grubstay.server.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoles,Long> {
}

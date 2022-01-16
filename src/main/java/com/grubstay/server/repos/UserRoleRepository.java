package com.grubstay.server.repos;

import com.grubstay.server.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles,Long> {
    public UserRoles findUserRolesByUserRoleId(long userRoleId);
}

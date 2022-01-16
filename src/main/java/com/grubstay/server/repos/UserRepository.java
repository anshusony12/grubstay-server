package com.grubstay.server.repos;

import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserIdProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    public User findUserByUserId(long userId);

    @Query(value = "select * from user " +
            "inner join user_roles on user_roles.user_user_id=user.user_id  " +
            "inner join role on role.role_id = user_roles.role_role_id " +
            "where role.role_name in ('ADMIN','SUB-ADMIN') ", nativeQuery = true)
    public List<User> loadAllAdmin();
}

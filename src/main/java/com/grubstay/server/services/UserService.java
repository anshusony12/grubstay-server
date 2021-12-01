package com.grubstay.server.services;

import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserIdProof;
import com.grubstay.server.entities.UserRoles;

import java.util.Set;

public interface UserService {
    public User createUser(User user, Set<UserRoles> userRoles) throws Exception;

    //get user by username
    public User getUser(String username);

    public void deleteUser(Long userId);

    public boolean updateUserIdProof(long userId, UserIdProof userIdProof);
}

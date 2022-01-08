package com.grubstay.server.services.impl;

import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserIdProof;
import com.grubstay.server.entities.UserRoles;
import com.grubstay.server.helper.UserFoundException;
import com.grubstay.server.repos.RoleRepository;
import com.grubstay.server.repos.UserRepository;
import com.grubstay.server.repos.UserRoleRepository;
import com.grubstay.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User createUser(User user, Set<UserRoles> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already there..");
            throw new UserFoundException();
        }
        else{
            // user create
            for(UserRoles ur: userRoles){
                roleRepository.save(ur.getRole());
            }
            local = this.userRepository.save(user);
            if(local!=null){
                for(UserRoles u : userRoles){
                    u.setUser(local);
                    this.userRoleRepository.save(u);
                }
            }
        }
        return local;
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public boolean updateUserIdProof(long userId, UserIdProof userIdProof) {
        User user = this.userRepository.getById(userId);
        user.setUserIdProof(userIdProof);
        this.userRepository.save(user);
        return true;
    }
}

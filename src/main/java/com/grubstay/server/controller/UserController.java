package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Role;
import com.grubstay.server.entities.User;
import com.grubstay.server.entities.UserRoles;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //creating user
    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody User user) throws Exception{
        ResultData resultData=new ResultData();
        try{
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            Set<UserRoles> roles = new HashSet<>();
            Role role1 = new Role();
            role1.setRoleId(1L);
            role1.setRoleName("NORMAL");
            UserRoles userRoles = new UserRoles();
            userRoles.setUser(user);
            userRoles.setRole(role1);
            roles.add(userRoles);
            User user1 = this.userService.createUser(user, roles);
            resultData.success = "saved";
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/checkUser/{username}")
    public ResponseEntity checkUser(@PathVariable("username") String username) throws Exception{
        ResultData resultData=new ResultData();
        try{
            User user = this.userService.getUser(username);
            if(user!=null){
                resultData.success = "found";
            }else{
                resultData.success = "notFound";
            }
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }



    // getting user by username
    @PostMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable("username") String username) throws Exception{
        ResultData resultData=new ResultData();
        try{
            User user = this.userService.getUser(username);
            ArrayList<Object> list = new ArrayList<>();
            list.add(user);
            resultData.data = list;
            resultData.total = list.size();
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

}
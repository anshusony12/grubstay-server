package com.grubstay.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubstay.server.config.UserDetailsServiceImpl;
import com.grubstay.server.entities.*;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(path="/")
    public ResponseEntity login(@RequestBody String userData) throws Exception{
        ResultData resultData=new ResultData();
        HashMap<String, String> user=null;
        String username=null;
        String password=null;
        try{
            try {
                user = new ObjectMapper().readValue(userData, HashMap.class);
                if(user.get("username")!=null){
                    username=user.get("username").toString();
                }
                if(user.get("password")!=null){
                    password=user.get("password").toString();
                }

            }
            catch (Exception e){
                resultData.error="Unable to Parse Data!";
                e.printStackTrace();
            }
            if(username!=null && password!=null){
                User local = this.userService.getUser(username);
                if(this.passwordEncoder.matches(password,local.getPassword())){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    ArrayList list = new ArrayList<>();
                    list.add(userDetails);
                    resultData.data = list;
                    resultData.total = 1;
                    resultData.success = "success";
                }else{
                    resultData.success = "failed";
                }
            }
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}

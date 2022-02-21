package com.grubstay.server.controller;

import com.grubstay.server.entities.*;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.*;
import com.grubstay.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminHomeController {

    @Autowired
    private PGRepository pgRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SubLocationRepository subLocationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CallbackRepository callbackRepository;

    @Autowired
    private StayFormRepository stayFormRepository;

    @GetMapping("/")
    public ResponseEntity loadAllCounts() throws Exception{
        ResultData resultData=new ResultData();
        try {
            Map<String,Integer> totalCounts = null;
            int citiesCount = this.cityRepository.getCitiesCount();
            int locationsCount = this.locationRepository.getLocationsCount();
            int subLocationsCount = this.subLocationRepository.getSubLocationsCount();
            int pgCounts = this.pgRepository.getPgCounts();
            int pgMaleCounts = this.pgRepository.getPgMaleCounts();
            int pgFemaleCounts = this.pgRepository.getPgFemaleCounts();
            totalCounts = new HashMap<String,Integer>();
            totalCounts.put("citiesCount",citiesCount);
            totalCounts.put("locationsCount",locationsCount);
            totalCounts.put("subLocationsCount",subLocationsCount);
            totalCounts.put("pgCounts",pgCounts);
            totalCounts.put("pgMaleCounts",pgMaleCounts);
            totalCounts.put("pgFemaleCounts",pgFemaleCounts);

            ArrayList<Map<String,Integer>> list = new ArrayList<>();
            list.add(0,totalCounts);
            resultData.data = list;
            resultData.total = totalCounts.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity createAdmin(@RequestBody User user) throws Exception{
        ResultData resultData=new ResultData();
        try{
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            Set<UserRoles> roles = new HashSet<>();
            Role role1 = new Role();
            role1.setRoleId(3L);
            role1.setRoleName("SUB-ADMIN");
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

    @PostMapping("/sendRequest")
    public ResponseEntity sendRequest(@RequestBody CallbackRequest request) throws Exception{
        ResultData resultData=new ResultData();
        try{
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String date = now.format(format);
            request.setStatus("Request");
            request.setDate(date.toString());
            //System.out.println(request);
            this.callbackRepository.save(request);
            resultData.success = "saved";
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/updateRequest")
    public ResponseEntity updateRequest(@RequestBody CallbackRequest request) throws Exception{
        ResultData resultData=new ResultData();
        try{
            //System.out.println(request);
            this.callbackRepository.save(request);
            resultData.success = "saved";
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRequest/{reqId}")
    public ResponseEntity updateRequest(@PathVariable("reqId") Long reqId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            //System.out.println(request);
            this.callbackRepository.deleteById(reqId);
            resultData.success = "deleted";
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAllAdminData() {
        ResultData resultData=new ResultData();
        try{
            List<User> adminData=this.userService.getAllAdmin();
            for(User user : adminData){
                String userAuthorities="";
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(user.getUsername());
                Collection<? extends  GrantedAuthority> authorities=userDetails.getAuthorities();
                if(authorities.size() > 0){
                    for (GrantedAuthority authority : authorities) {
                        userAuthorities=userAuthorities+" "+authority.getAuthority();
                    }
                    user.setRoles(userAuthorities);
                }

            }
            resultData.data=(ArrayList)adminData;
            resultData.success="fetched";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/callRequests")
    public ResponseEntity getCallRequests() {
        ResultData resultData=new ResultData();
        try{
            List<CallbackRequest> all = this.callbackRepository.findAll();
            resultData.data = (ArrayList) all;
            resultData.success="fetched";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity updateAdmin(@RequestBody User user){
        ResultData resultData=new ResultData();
        try{
            if(user!=null) {
                User existingUser=this.userRepository.findByUsername(user.getUsername());
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setPhone(user.getPhone());
                existingUser.setWhatsapp(user.getWhatsapp());
                if(user.getPassword()!="" && user.getPassword()!=null){
                    existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                }
                existingUser.setEmail(user.getEmail());
                existingUser.setDob(user.getDob());
                existingUser.setGender(user.getGender());
                this.userRepository.save(existingUser);
                resultData.success="updated";
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteAdmin(@PathVariable("userId") String userId){
        ResultData resultData=new ResultData();
        try{
             User user=this.userRepository.findUserByUserId(Long.parseLong(userId));
             if(user!=null){
                    Set<UserRoles> userRoles=user.getUserRoles();
                    for(UserRoles userrole : userRoles){
                        UserRoles userRole=this.userRoleRepository.findUserRolesByUserRoleId(userrole.getUserRoleId());
                        if(userRole!=null){
                            this.userRoleRepository.deleteById(userrole.getUserRoleId());
                        }
                    }
                    this.userRepository.deleteById(Long.parseLong(userId));
             }
             else{
                 throw new HelperException("User Not Present!");
             }
             resultData.success="Deleted";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    // get all stay-form enquiries data
    @GetMapping("/stay-form")
    public ResponseEntity getAllStayFormEnquiries() {
        ResultData resultData=new ResultData();
        try{
            List<StayForm> all = this.stayFormRepository.findAll();
            resultData.data = (ArrayList) all;
            resultData.success="fetched";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEnquiry/{enquiryId}")
    public ResponseEntity deleteEnquiry(@PathVariable("enquiryId") Long enquiryId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            //System.out.println(request);
            this.stayFormRepository.deleteById(enquiryId);
            resultData.success = "deleted";
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}

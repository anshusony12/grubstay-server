package com.grubstay.server;

import com.grubstay.server.entities.*;
import com.grubstay.server.services.StorageService;
import com.grubstay.server.services.UserService;
import com.grubstay.server.services.impl.PGServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@SpringBootApplication
public class GrubstayApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(GrubstayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			User admin = this.userService.getUser("admin");
			if(admin==null) {
				Date date = new Date();
				User user = new User();
				user.setDob(date);
				user.setEmail("info@grubstay.com");
				user.setPassword(this.bCryptPasswordEncoder.encode("admin@grubstay"));
				user.setEnabled(true);
				user.setFirstName("Grubstay");
				user.setLastName("User");
				user.setGender("male");
				user.setPhone(Long.parseLong("8050163861"));
				user.setWhatsapp(Long.parseLong("8050163861"));
				user.setUsername("admin");
				Set<UserRoles> roles = new HashSet<>();
				Role role1 = new Role();
				role1.setRoleId(2L);
				role1.setRoleName("ADMIN");
				UserRoles userRoles = new UserRoles();
				userRoles.setUser(user);
				userRoles.setRole(role1);
				roles.add(userRoles);
				User user1 = this.userService.createUser(user, roles);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

package com.grubstay.server;

import com.grubstay.server.entities.LandMarks;
import com.grubstay.server.entities.PGAmenitiesServices;
import com.grubstay.server.entities.PGRoomFacility;
import com.grubstay.server.entities.PayingGuest;
import com.grubstay.server.services.impl.PGServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GrubstayApplication implements CommandLineRunner {

	@Autowired
	private PGServiceImpl pgService;

	public static void main(String[] args) {
		SpringApplication.run(GrubstayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PayingGuest pg=new PayingGuest();
		pg.setPgName("Posh PG");
		pg.setPgDesc("Post PG for boys and girls");
		pg.setPgAddress("Agra Chowk Palwal");
		pg.setPgType("PG");
		pg.setPgForMale(true);
		pg.setPgForFemale(true);
		pg.setPgForBoth(true);
		pg.setPgImage("default.png");
		pg.setSingleMemPgPrc(5000);
		pg.setDistFromSubLoc(7000);
		pg.setTripleMemPgPrc(9000);
		pg.setDistFromSubLoc(500);

		PGAmenitiesServices amenitiesServices=new PGAmenitiesServices();
		amenitiesServices.setSecuritySurvialance(true);
		amenitiesServices.setWifi(true);
		amenitiesServices.setDiningArea(true);
		amenitiesServices.setMeals(true);
		amenitiesServices.setPowerBackUp(true);
		amenitiesServices.setLift(true);
		amenitiesServices.setWashingMachine(true);
		amenitiesServices.setParkingArea(true);
		amenitiesServices.setWaterFilter(true);
		amenitiesServices.setPgStayId(pg);

		PGRoomFacility roomFacility=new PGRoomFacility();
		roomFacility.setAttachedWashroom(true);
		roomFacility.setBedWithMattress(true);
		roomFacility.setCeilingFan(true);
		roomFacility.setHotWatersupply(true);
		roomFacility.setAirCooler(true);
		roomFacility.setTvDth(true);
		roomFacility.setWardrobe(true);
		roomFacility.setSafetyLocker(true);
		roomFacility.setPgStayId(pg);

		List<LandMarks> landMarks=new ArrayList<>();

		LandMarks landMark=new LandMarks();
		landMark.setLandMarkName("Panchwati");
		landMark.setPgStayId(pg);
		landMarks.add(landMark);


		PayingGuest payingGuest=this.pgService.createPG(pg, amenitiesServices, roomFacility, landMarks);
		System.out.println(payingGuest);

	}
}

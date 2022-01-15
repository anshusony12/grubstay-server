package com.grubstay.server.repos;

import com.grubstay.server.entities.PayingGuest;
import com.grubstay.server.entities.StayGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PGRepository extends JpaRepository<PayingGuest, String> {

    public PayingGuest findPayingGuestByPgId(String pgId);

    @Query("from PayingGuest p where p.pgName = ?1 and p.subLocation.subLocationId = ?2")
    public PayingGuest findPGByNameAndSubLocation(String name, Long sLId);

    @Query("delete from PayingGuest pg where pg.pgId = ?1")
    @Modifying
    @Transactional
    public void deletePgById(String pgId);

    @Query("from PayingGuest p where p.subLocation.subLocationId=?1")
    public List<PayingGuest> findPayingGuestBySubLocationId(Long subLocationId);

    @Query(value="from StayGallery  s where s.stayId=?1 and s.galName like '%pgmain%'")
    public  StayGallery findFirstByStayId(String stayId);

    @Query(value = "select * from paying_guest  " +
            "inner join sub_location on sub_location.sub_location_id = paying_guest.sub_location_sub_location_id  " +
            "inner join location on location.location_id = sub_location.location_location_id  " +
            "inner join city on city.city_id=location.city_city_id " +
            "where city.city_id=?1", nativeQuery = true)
    public List<PayingGuest> loadAllPGUsingCityId(Integer cityId);

    @Query(value = "select distinct count(pg_id) from paying_guest",nativeQuery = true)
    public int getPgCounts();

    @Query(value = "select distinct count(pg_id) from paying_guest where pg_gender = 'male'",nativeQuery = true)
    public int getPgMaleCounts();

    @Query(value = "select distinct count(pg_id) from paying_guest where pg_gender = 'female'",nativeQuery = true)
    public int getPgFemaleCounts();
}

package com.grubstay.server.repos;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    public City findCityByCityId(int cityId);

    @Modifying
    @Transactional
    @Query("update City c set c.cityName = ?1, c.cityImageName = ?2, c.status=?3 where c.cityId = ?4")
    public int updateCityByCityId(String cityName, String cityIcon, boolean status, Integer cityId);

    @Query(value = "select distinct count(pg_id) from paying_guest pg " +
            "inner join grubstay.sub_location sb on sb.sub_location_id = pg.sub_location_sub_location_id " +
            "inner join grubstay.location l on l.location_id = sb.location_location_id " +
            "inner join grubstay.city c on c.city_id = l.city_city_id and c.city_name = ?1", nativeQuery = true)
    public int getPgInCity(String cityName);

    @Query(value = "select distinct count(city_id) from city",nativeQuery = true)
    public int getCitiesCount();

    @Query(value = "select c.city_id,c.city_name,location.location_id,location.location_name from city c " +
            "inner join location on location.city_city_id = c.city_id " +
            "where c.status = 1 and location.status = 1",nativeQuery = true)
    public List<Map<String,String>> citiesWithLocation();

    @Query(value="select * from city where city_name= ?1",nativeQuery = true)
    public City findCityByCityName(String cityName);

}

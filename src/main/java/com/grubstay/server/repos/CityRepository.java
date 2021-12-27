package com.grubstay.server.repos;

import com.grubstay.server.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CityRepository extends JpaRepository<City, Integer> {
    public City findCityByCityId(int cityId);

    @Modifying
    @Transactional
    @Query("update City c set c.cityName = ?1, c.cityImage = ?2 where c.cityId = ?3")
    public int updateCityByCityId(String cityName, String cityIcon, Integer cityId);
}

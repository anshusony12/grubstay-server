package com.grubstay.server.services.impl;

import com.grubstay.server.entities.City;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.repos.CityRepository;
import com.grubstay.server.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public City createCity(City city) throws HelperException {
        City existingCity=cityRepository.findCityByCityId(city.getCityId());
        if(existingCity!=null){
            throw new HelperException("City Already Exist with this Id...Try Again");
        }else {
            existingCity = cityRepository.save(city);
        }
        return existingCity;
    }

    @Override
    public City getCity(int cityId) {
        City city=cityRepository.findCityByCityId(cityId);;
        return city;
    }

    @Override
    public void deleteCity(int cityId) throws HelperException {
        City existingCity=cityRepository.findCityByCityId(cityId);
        if(existingCity==null){
            throw new HelperException("City not present!");
        }else {
            cityRepository.deleteById(cityId);
        }
    }

    @Override
    public City updateCity(City city) throws HelperException {
        City existingCity=cityRepository.findCityByCityId(city.getCityId());
        if(existingCity==null){
            throw new HelperException("City not present!");
        }else {
            int updatedRows=cityRepository.updateCityByCityId(city.getCityName(), city.getCityImageName(), city.isStatus(), city.getCityId());
            if(updatedRows>0){
                existingCity=cityRepository.findCityByCityId(city.getCityId());
            }
        }
        return existingCity;
    }

    @Override
    public List<City> getAllCity() throws HelperException {
        List<City> list=cityRepository.findAll();
        return list;
    }
}

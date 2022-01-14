package com.grubstay.server.services;

import com.grubstay.server.entities.City;
import com.grubstay.server.helper.HelperException;

import java.util.List;

public interface CityService {
    public City createCity(City city) throws HelperException;
    public City getCity(int cityId);
    public void deleteCity(int cityId) throws HelperException;
    public City updateCity(City city) throws HelperException;
    public List<City> getAllCity() throws HelperException;

}

package com.example.atta.attaProject.services;

import com.example.atta.attaProject.FullGetObjects.FullCity;
import com.example.atta.attaProject.FullGetObjects.FullSportForCity;
import com.example.atta.attaProject.RequestBody.CityPostRequestBody;
import com.example.atta.attaProject.model.City;
import com.example.atta.attaProject.model.CitySport;
import com.example.atta.attaProject.model.Region;
import com.example.atta.attaProject.model.Sport;
import com.example.atta.attaProject.repositories.CityRepository;
import com.example.atta.attaProject.repositories.CitySportRepository;
import com.example.atta.attaProject.repositories.RegionRepository;
import com.example.atta.attaProject.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    SportRepository sportRepository;
    @Autowired
    CitySportRepository citySportRepository;

    /**
     * Method to get a list of all cities from the database
     * @return list of all cities
     */
    public List<FullCity> getCities() {
        List<City> allCities =  cityRepository.findAll();
        List<FullCity> allFullCities = new ArrayList<>();
        // Getting the sports as well for every city
        for (City c : allCities) {
            FullCity fc = getFullCity(c);
            allFullCities.add(fc);
        }
        return allFullCities;
    }

    /**
     * Method to create a city with all it's sports
     * @param c The city to be updated
     * @return The updated city
     */
    public City getFullCity(City c) {
        FullCity fc = new FullCity();
        fc.setId(c.getId());
        fc.setCityName(c.getCityName());
        // Create a list of sports of the city
        Set<FullSportForCity> allSports = new HashSet<FullSportForCity>();

        List<CitySport> foundCitySports = citySportRepository.findByCity_id(c.getId());
        for (CitySport cs : foundCitySports) {
            FullSportForCity fs = new FullSportForCity();
            fs.setId(cs.getSport_id());
            fs.setSportName(sportRepository.findById(cs.getSport_id()).get().getSportName());
            fs.setStartDate(cs.getStartDate());
            fs.setEndDate(cs.getEndDate());
            fs.setPrice(cs.getPrice());
            allSports.add(fs);
        }
        c.setFullSportForCities(allSports);

        return c;
    }

    /**
     * Method to get a city from the database by name
     * @param cityName The name of the city
     * @return The city with it's sports or null if no such city
     */
    public FullCity getCity(String cityName) {
        City city =  cityRepository.findByCityName(cityName);
        if (city == null)
            return null;
        return getFullCity(city);
    }

    /**
     * Method to save a city to the database
     * @param city the city post request body with all the necessary data
     * @return The reference to the saved city or null if city couldn't be saved
     */
    public City saveCity(CityPostRequestBody city) {
        Region reg = regionRepository.findByRegionName(city.regionName);
        if (reg == null)
            return null;
        if (cityRepository.findByCityName(city.cityName) != null)
            return null;

        int region_id = reg.getId();

        City retCity = new City();
        retCity.setId(city.id);
        retCity.setCityName(city.cityName);
        retCity.setRegion_id(region_id);

        return cityRepository.save(retCity);
    }

    /**
     * Method to delete a city from the database
     * @param city_regionName The name of the city to be deleted combined with
     *                        the name of region <city_region>
     * @return true if successful deletion / false if not
     */
    public boolean deleteCity(String city_regionName) {
        String[] split = city_regionName.split("_");
        // If there is a problem with the names return false
        if (split.length != 2)
            return false;

        // Check if provided region exists
        Region reg = regionRepository.findByRegionName(split[1]);
        if (reg == null)
            return false;

        // Check if provided city exists
        City city = cityRepository.findByCityName(split[0]);
        if (city == null)
            return false;

        // Check if id's match and delete the city with all CitySport database records
        if (city.getRegion_id() == reg.getId()) {
            List<CitySport> cs = citySportRepository.findByCity_id(city.getId());
            for (CitySport c : cs) {
                citySportRepository.delete(c);
            }
            cityRepository.delete(city);
            return true;
        }
        return false;
    }

    /**
     * Method to delete a sport from a city
     * @param cityName_sportName The name of city and sport to be deleted
     * @return true if successful deletion / false if not
     */
    public boolean deleteSportFromCity(String cityName_sportName) {
        String[] names = cityName_sportName.split("_");
        if (names.length != 2)
            return false;

        City c = cityRepository.findByCityName(names[0]);
        if (c == null)
            return false;
        Sport s = sportRepository.findBySportName(names[1]);
        if (s == null)
            return false;

        List<CitySport> citySports = citySportRepository.findByCity_id(c.getId());
        for (CitySport cs : citySports) {
            if (cs.getSport_id() == s.getId()) {
                citySportRepository.delete(cs);
                return true;
            }
        }
        return false;
    }
}

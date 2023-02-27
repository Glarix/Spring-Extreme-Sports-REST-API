package com.example.atta.attaProject.services;

import com.example.atta.attaProject.RequestBody.RegionPostRequestBody;
import com.example.atta.attaProject.model.City;
import com.example.atta.attaProject.model.Country;
import com.example.atta.attaProject.model.Region;
import com.example.atta.attaProject.repositories.CityRepository;
import com.example.atta.attaProject.repositories.CountryRepository;
import com.example.atta.attaProject.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CityService cityservice;

    /**
     * Method to get a list of all regions from the database
     * @return list of found regions
     */
    public List<Region> getRegions() {
        List<Region> allRegions = regionRepository.findAll();

        // Updating the cities to have all the sports
        for (Region reg : allRegions) {
            UpdateCitiesInRegion(reg);
        }
        return allRegions;
    }

    /**
     * Method to get a region from the database by name
     * @param regionName The name of the region
     * @return The found region or null if such doesn't exist
     */
    public Region getRegion(String regionName) {
        Region reg =  regionRepository.findByRegionName(regionName);

        UpdateCitiesInRegion(reg);
        return reg;
    }

    /**
     * Method to update the cities of a region to contain all the sports
     * for display
     * @param reg The region to have it's cities updated
     */
    public void UpdateCitiesInRegion(Region reg) {
        Set<City> cities = reg.getCities();
        Set<City> updatedCities = new HashSet<>();
        for (City c : cities) {
            c = cityservice.getFullCity(c);
            updatedCities.add(c);
        }
        reg.setCities(updatedCities);
    }

    /**
     * Method to save a region into the database
     * @param region A region post request body with all the data for a region entry
     * @return The reference to the saved region or null if not saved
     */
    public Region saveRegion(RegionPostRequestBody region) {
        Country foundCountry = countryRepository.findByCountryName(region.countryName);
        if (foundCountry == null)
            return null;
        if (regionRepository.findByRegionName(region.regionName) != null)
            return null;

        long country_id = foundCountry.getId();

        Region reg = new Region();
        reg.setId(region.id);
        reg.setRegionName(region.regionName);
        reg.setCountry_id(country_id);
        return regionRepository.save(reg);
    }

    /**
     * Method to delete a region by name from database
     * @param region_countryName The name of the region and country it is from
     *                           <region_country>
     * @return true if successful deletion / false if not
     */
    public boolean deleteRegion(String region_countryName) {
        String[] split = region_countryName.split("_");
        // if there is a problem with the names return false
        if (split.length != 2)
            return false;
        // Verify if provided country exists
        Country country = countryRepository.findByCountryName(split[1]);
        if (country == null)
            return false;
        // Verify if provided region exists
        Region reg = regionRepository.findByRegionName(split[0]);
        if (reg == null)
            return false;

        // Check if id's match and delete the region with all it's cities
        if (reg.getCountry_id() == country.getId()){
            List<City> allCities = cityRepository.findByRegion_id(reg.getId());
            for (City c : allCities) {
                cityservice.deleteCity("" + c.getCityName() + "_" + reg.getRegionName());
            }
            regionRepository.delete(reg);
            return true;
        }
        return false;
    }
}

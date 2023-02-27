package com.example.atta.attaProject.services;

import com.example.atta.attaProject.model.Country;
import com.example.atta.attaProject.model.Region;
import com.example.atta.attaProject.repositories.CountryRepository;
import com.example.atta.attaProject.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    RegionService regionService;

    /**
     * Method to get a list with all countries in database
     * @return list of the countries
     */
    public List<Country> getCountries() {
        List<Country> allCountries = countryRepository.findAll();

        // Updating the regions to contain full cities with sport
        for (Country c : allCountries) {
            updateRegion(c);
        }
        return allCountries;
    }

    /**
     * Method to save a country to the database
     * @param country The country reference to be saved
     * @return The saved country reference
     */
    public Country saveCountry(Country country) {
        if (countryRepository.findByCountryName(country.getCountryName()) == null)
            return countryRepository.save(country);
        return null;
    }

    /**
     * Method to get a country from the database by the name
     * @param countryname The name of the country
     * @return The found country or null if country doesn't exist in database
     */
    public Country getCountry(String countryname) {
        Country c = countryRepository.findByCountryName(countryname);
        if(c == null)
            return null;
        updateRegion(c);
        return c;
    }

    /**
     * Method to update a countrie's regions to contain full cities with sports
     * @param c The country to have it's regions updated
     */
    private void updateRegion(Country c) {
        Set<Region> regions = c.getRegions();
        Set<Region> updatedRegions = new HashSet<>();
        for (Region r : regions) {
            regionService.UpdateCitiesInRegion(r);
            updatedRegions.add(r);
        }
        c.setRegions(updatedRegions);
    }

    /**
     * Method to delete a country by name from the database
     * @param country The name of the country to be deleted
     * @return true if country was found and deleted or false if else
     */
    public boolean deleteCountry(String country) {
        Country countryToDelete = countryRepository.findByCountryName(country);
        if (country == null)
            return false;

        // Find and delete all regions of a country then delete the country from database
        List<Region> regs = regionRepository.findByCountry_id(countryToDelete.getId());
        for (Region r : regs) {
            regionService.deleteRegion("" + r.getRegionName() + "_" + countryToDelete.getCountryName());
        }
        countryRepository.delete(countryToDelete);
        return true;
    }
}

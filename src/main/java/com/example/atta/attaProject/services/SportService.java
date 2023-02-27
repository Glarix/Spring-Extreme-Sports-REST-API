package com.example.atta.attaProject.services;

import com.example.atta.attaProject.FullGetObjects.FullCityForSport;
import com.example.atta.attaProject.FullGetObjects.FullSport;
import com.example.atta.attaProject.RequestBody.SportPostRequestBody;
import com.example.atta.attaProject.RequestBody.SportSelectionRequestBody;
import com.example.atta.attaProject.model.City;
import com.example.atta.attaProject.model.CitySport;
import com.example.atta.attaProject.model.Sport;
import com.example.atta.attaProject.repositories.CityRepository;
import com.example.atta.attaProject.repositories.CitySportRepository;
import com.example.atta.attaProject.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class SportService {

    @Autowired
    SportRepository sportRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CitySportRepository citySportRepository;

    /**
     * Method to get a list of all sports and the cities they are available in
     * @return The list with all the sports
     */
    public List<FullSport> getSports() {
        List<Sport> allSports = sportRepository.findAll();
        List<FullSport> allFullSports = new ArrayList<>();

        for (Sport s : allSports) {
            FullSport fs = getFullSport(s);
            allFullSports.add(fs);
        }
        return allFullSports;
    }

    /**
     * Method to update a sport with data about all the cities it is available in
     * @param s The sport to be updated
     * @return The updated Sport
     */
    private FullSport getFullSport(Sport s) {
        FullSport fs = new FullSport();
        fs.setId(s.getId());
        fs.setSportName(s.getSportName());

        // Create a set with every city this sport is available in
        List<FullCityForSport> allCities = new ArrayList<>();

        List<CitySport> foundCitySports = citySportRepository.findBySport_id(s.getId());
        for (CitySport cs : foundCitySports) {
            FullCityForSport fc = new FullCityForSport();
            fc.setId(cs.getCity_id());
            City c = cityRepository.findById(cs.getCity_id()).get();
            fc.setCityName(c.getCityName());
            fc.setRegionId(c.getRegion_id());
            fc.setStartDate(cs.getStartDate());
            fc.setEndDate(cs.getEndDate());
            fc.setPrice(cs.getPrice());
            allCities.add(fc);
        }
        fs.setFullCityForSport(allCities);
        return fs;
    }

    /**
     * Method to get a sport from the database by name
     * @param name The name of the sport
     * @return The sport with the given name or null if it doesn't exist
     */
    public FullSport getSport(String name) {
        Sport sport = sportRepository.findBySportName(name);
        if (sport == null)
            return null;
        return getFullSport(sport);
    }

    /**
     * Method to save a sport in the database and save in
     * the join city_sport table a record of the city
     * it is available in
     * @param sport The sport post body request with all
     *              the informations about the sport
     * @return a referece to the saved sport or null if couldn't be saved
     */
    public Sport saveSport(SportPostRequestBody sport)  {
        City foundCity = cityRepository.findByCityName(sport.cityName);
        if (foundCity == null) return null;

        // Insert in join table and if non-existent sport insert in sport table
        Sport foundSport = sportRepository.findBySportName(sport.sportName);
        if (foundSport == null) {
            Sport saveSport = new Sport();
            saveSport.setId(sport.id);
            saveSport.setSportName(sport.sportName);
            sportRepository.save(saveSport);
        }

        foundSport = sportRepository.findBySportName(sport.sportName);

        CitySport toSaveCitySport = new CitySport();
        toSaveCitySport.setId(0);
        toSaveCitySport.setCity_id(foundCity.getId());
        toSaveCitySport.setSport_id(foundSport.getId());
        toSaveCitySport.setStartDate(sport.startDate);
        toSaveCitySport.setEndDate(sport.endDate);
        toSaveCitySport.setPrice(sport.price);

        citySportRepository.save(toSaveCitySport);

        return foundSport;
    }

    /**
     * Method to delete a sport from the database by name
     * @param name The name of the sport to delete
     * @return true if successful deletion / false if not
     */
    public boolean deleteSport(String name) {

        Sport sport = sportRepository.findBySportName(name);
        if (sport == null)
            return false;

        // Delete every CitySport reference from the join table
        List<CitySport> cs = citySportRepository.findBySport_id(sport.getId());
        for (CitySport c : cs) {
            citySportRepository.delete(c);
        }
        sportRepository.delete(sport);
        return true;
    }

    /**
     * Method to update the attributes of a sport from a city
     * @param sportName_cityName The name of sport and city to be updated
     * @param sport The new sport body
     * @return true if updated successfully / false if no update
     */
    public boolean updateSport(String sportName_cityName, SportPostRequestBody sport) {
        String[] names = sportName_cityName.split("_");
        if (names.length != 2)
            return false;
        if (!sport.sportName.equals(names[0])  || !sport.cityName.equals(names[1]))
            return false;
        Sport s = sportRepository.findBySportName(names[0]);
        if (s == null)
            return false;
        City c = cityRepository.findByCityName(names[1]);
        if (c == null)
            return false;

        List<CitySport> sports = citySportRepository.findBySport_id(s.getId());
        CitySport correctCS = null;
        for (CitySport cs : sports) {
            if (cs.getCity_id() == c.getId()) {
                correctCS = cs;
                break;
            }
        }
        if (correctCS == null)
            return false;

        correctCS.setPrice(sport.price);
        correctCS.setStartDate(sport.startDate);
        correctCS.setEndDate(sport.endDate);

        citySportRepository.save(correctCS);
        return true;

    }

    /**
     * Method to return a list of sports sorted by price and within a period if specified
     * @param sports The Request body with all the needed information
     * @return The List containing all the available sports, sorted by price
     */
    public List<FullSport> selectSports(SportSelectionRequestBody sports) {
        // TODO implement all cases
        List<FullSport> finalFS = new ArrayList<>();
        // If no query information given
        if (sports.sports.size() == 0 && sports.startDate == null && sports.endDate == null)
            return finalFS;

        // If only period given
        if (sports.sports.size() == 0 && sports.startDate != null && sports.endDate != null)
            return getSportsByPeriod(getSports(), sports.startDate, sports.endDate);

        // If only sports given with no specific period
        if (sports.sports.size() > 0 && sports.startDate == null && sports.endDate == null) {
            selectGivenSports(sports.sports, finalFS);
            for (FullSport fs : finalFS) {
                Collections.sort(fs.getFullCityForSport());
            }
            return finalFS;
        }

        // If given sports and a specific period
        if (sports.sports.size() > 0 && sports.startDate != null && sports.endDate != null) {
            selectGivenSports(sports.sports, finalFS);
            return getSportsByPeriod(finalFS, sports.startDate, sports.endDate);
        }

        return finalFS;
    }

    /**
     * Method to get all the sports that match the names from a list
     * @param sports The list with all sport's names
     * @param finalFS The list with all found sports
     */
    private void selectGivenSports(List<String> sports, List<FullSport> finalFS) {
        for (String s : sports) {
            FullSport foundSport = getSport(s);
            if (foundSport != null)
                finalFS.add(foundSport);
        }
    }

    /**
     * Method to select only the sports available in a specific period
     * @param fullSports The list with all possible sports
     * @param startDate Period Start Date
     * @param endDate Period End Date
     * @return List of all available sports
     */
    private List<FullSport> getSportsByPeriod(List<FullSport> fullSports, Date startDate, Date endDate) {
        List<FullSport> available = new ArrayList<>();
        for (FullSport fs : fullSports) {
            List<FullCityForSport> fcfs = fs.getFullCityForSport();
            // Check for period
            List<FullCityForSport> acceptedSports = new ArrayList<>();
            // After this for, I should have only the sports and cities that have at least 1 day in this interval
            for (FullCityForSport fc : fcfs) {
                // It was not returning the sports starting the same day, so I made a workaround:)
                if (!fc.getEndDate().toLocalDate().plusDays(1).isBefore(startDate.toLocalDate())
                        && !fc.getStartDate().after(endDate))
                    acceptedSports.add(fc);
            }
            fs.setFullCityForSport(acceptedSports);
            if (fs.getFullCityForSport().size() > 0) {
                Collections.sort(fs.getFullCityForSport());
                available.add(fs);
            }
        }
        return available;
    }
}

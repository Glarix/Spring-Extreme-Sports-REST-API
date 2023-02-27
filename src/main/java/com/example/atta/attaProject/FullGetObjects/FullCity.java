package com.example.atta.attaProject.FullGetObjects;

import java.util.Set;

/**
 * Model to represent a city with all it's available sports
 */
public class FullCity {

    private long id;

    private String cityName;

    private Set<FullSportForCity> fullSportForCities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Set<FullSportForCity> getFullSportForCities() {
        return fullSportForCities;
    }

    public void setFullSportForCities(Set<FullSportForCity> fullSportForCities) {
        this.fullSportForCities = fullSportForCities;
    }
}

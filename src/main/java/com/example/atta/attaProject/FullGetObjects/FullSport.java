package com.example.atta.attaProject.FullGetObjects;

import java.util.List;

/**
 * Model to represent a Sport with all the cities it is available in
 */
public class FullSport {

    private long id;

    private String sportName;

    private List<FullCityForSport> fullCityForSport;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<FullCityForSport> getFullCityForSport() {
        return fullCityForSport;
    }

    public void setFullCityForSport(List<FullCityForSport> fullCityForSport) {
        this.fullCityForSport = fullCityForSport;
    }
}

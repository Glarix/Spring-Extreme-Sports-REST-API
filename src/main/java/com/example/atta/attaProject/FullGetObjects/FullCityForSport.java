package com.example.atta.attaProject.FullGetObjects;

import java.sql.Date;

public class FullCityForSport implements Comparable<FullCityForSport>{

    private long id;

    private String cityName;

    private long regionId;

    private Date startDate;

    private Date endDate;

    private Double price;

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

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int compareTo(FullCityForSport o) {
        return this.price.compareTo(o.getPrice());
    }
}

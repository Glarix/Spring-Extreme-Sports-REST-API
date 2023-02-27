package com.example.atta.attaProject.RequestBody;


import java.sql.Date;

/**
 * JSON attributes for a Sport Post request
 */
public class SportPostRequestBody {

    public long id;
    public String sportName;
    public String cityName;
    public Date startDate;
    public Date endDate;
    public Double price;
}

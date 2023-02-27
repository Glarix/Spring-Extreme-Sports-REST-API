package com.example.atta.attaProject.controllers;


import com.example.atta.attaProject.FullGetObjects.FullCity;
import com.example.atta.attaProject.RequestBody.CityPostRequestBody;
import com.example.atta.attaProject.model.City;
import com.example.atta.attaProject.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping("getcities")
    public List<FullCity> getCities() {
        return cityService.getCities();
    }

    @GetMapping("getcity/{name}")
    public FullCity getCity(@PathVariable String name) {
        return cityService.getCity(name);
    }

    @PostMapping("addcity")
    public City saveCity(@RequestBody CityPostRequestBody city) {
        return cityService.saveCity(city);
    }

    @DeleteMapping("deletecity/{city_regionName}")
    public boolean deleteCity(@PathVariable String city_regionName) {
        // the string must be cityname_regionname
        return cityService.deleteCity(city_regionName);
    }
    @DeleteMapping("deletesportfromcity/{cityName_sportName}")
    public boolean deleteSportFromCity(@PathVariable String cityName_sportName) {
        return cityService.deleteSportFromCity(cityName_sportName);
    }
}

package com.example.atta.attaProject.controllers;

import com.example.atta.attaProject.model.Country;
import com.example.atta.attaProject.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class CountryController {

    @Autowired
    CountryService countryService;


    @GetMapping("getcountries")
    public List<Country> getCountries() {
        return countryService.getCountries();
    }

    @PostMapping("addcountry")
    public Country saveCountry(@RequestBody Country country) {
        return countryService.saveCountry(country);
    }

    @GetMapping("getcountry/{name}")
    public Country getCountry(@PathVariable String name) {
        return countryService.getCountry(name);
    }

    @DeleteMapping("deletecountry/{country}")
    public boolean deleteCountry(@PathVariable String country) {
        return countryService.deleteCountry(country);
    }

}

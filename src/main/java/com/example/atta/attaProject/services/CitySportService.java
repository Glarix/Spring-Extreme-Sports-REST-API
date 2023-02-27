package com.example.atta.attaProject.services;

import com.example.atta.attaProject.repositories.CityRepository;
import com.example.atta.attaProject.repositories.CitySportRepository;
import com.example.atta.attaProject.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitySportService {

    @Autowired
    SportRepository sportRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CitySportRepository citySportRepository;



}

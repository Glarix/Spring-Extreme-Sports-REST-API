package com.example.atta.attaProject.repositories;

import com.example.atta.attaProject.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityName(String cityName);

    List<City> findByRegion_id(int id);
}

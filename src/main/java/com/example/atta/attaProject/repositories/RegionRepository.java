package com.example.atta.attaProject.repositories;

import com.example.atta.attaProject.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    Region findByRegionName(String regionName);

    List<Region> findByCountry_id(long country_id);

}

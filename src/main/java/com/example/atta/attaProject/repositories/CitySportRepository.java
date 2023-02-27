package com.example.atta.attaProject.repositories;

import com.example.atta.attaProject.model.CitySport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitySportRepository extends JpaRepository<CitySport, Long> {

    List<CitySport> findByCity_id(long city_id);

    List<CitySport> findBySport_id(long sport_id);
}

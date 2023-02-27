package com.example.atta.attaProject.repositories;

import com.example.atta.attaProject.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
    Sport findBySportName(String sportName);
}

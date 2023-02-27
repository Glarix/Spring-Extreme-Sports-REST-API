package com.example.atta.attaProject.controllers;

import com.example.atta.attaProject.FullGetObjects.FullSport;
import com.example.atta.attaProject.RequestBody.SportPostRequestBody;
import com.example.atta.attaProject.RequestBody.SportSelectionRequestBody;
import com.example.atta.attaProject.model.Sport;
import com.example.atta.attaProject.services.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class SportController {

    @Autowired
    SportService sportService;

    @GetMapping("getsports")
    public List<FullSport> getSports() {
        return sportService.getSports();
    }

    @GetMapping("getsport/{name}")
    public FullSport getSport(@PathVariable String name) {
        return sportService.getSport(name);
    }

    @PostMapping("addsport")
    public Sport saveSport(@RequestBody SportPostRequestBody sport) {
        return sportService.saveSport(sport);
    }

    @DeleteMapping("deletesport/{name}")
    public boolean deleteSport(@PathVariable String name) {
        return sportService.deleteSport(name);
    }

    @PutMapping("updatesport/{sportName_cityName}")
    public boolean updateSport(@PathVariable String sportName_cityName, @RequestBody SportPostRequestBody sport) {
        return sportService.updateSport(sportName_cityName, sport);
    }

    @GetMapping("selectsports")
    public List<FullSport> selectSports(@RequestBody SportSelectionRequestBody sports) {
        return sportService.selectSports(sports);
    }
}

package com.example.atta.attaProject.controllers;


import com.example.atta.attaProject.RequestBody.RegionPostRequestBody;
import com.example.atta.attaProject.model.Region;
import com.example.atta.attaProject.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class RegionController {

    @Autowired
    RegionService regionService;

    @GetMapping("getregions")
    public List<Region> getRegions() {
        return regionService.getRegions();
    }

    @GetMapping("getregion/{name}")
    public Region getRegion(@PathVariable String name) {
        return regionService.getRegion(name);
    }

    @PostMapping("addregion")
    public Region saveRegion(@RequestBody RegionPostRequestBody region) {
        return regionService.saveRegion(region);
    }

    @DeleteMapping("deleteregion/{region_countryName}")
    public boolean deleteRegion(@PathVariable String region_countryName) {
        return regionService.deleteRegion(region_countryName);
    }
}

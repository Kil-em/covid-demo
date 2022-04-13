package com.example.covid.demo.Controller;

import com.example.covid.demo.services.CovidService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CovidController {

    @Autowired
    CovidService service;

    @GetMapping("/all")
    public String getAllData() {
        return service.getAllCountries();
    }

    @GetMapping("/{continent}")
    public String getDataByLocation(@PathVariable("continent") String continent) throws JsonProcessingException {
        return service.getAllCountriesByContinent(continent);
    }
}

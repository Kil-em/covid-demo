package com.example.covid.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Country {

    @JsonProperty("people_vaccinated")
    private long vaccinated;
    private long population;
    private long confirmed;
    private long deaths;
}

package com.example.covid.demo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AllWrapper {
    @JsonProperty("All")
    private Country all;
}

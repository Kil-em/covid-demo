package com.example.covid.demo.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
public class Countries {

    @JsonProperty
    Map<String, AllWrapper> countries;
}

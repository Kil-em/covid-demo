package com.example.covid.demo.services;

import com.example.covid.demo.Model.AllWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CovidService {
    private static final String URL = "https://covid-api.mmediagroup.fr/v1/";

    @Autowired
    private RestTemplate restTemplate;

    public String getAllCountries() {

        ParameterizedTypeReference<TreeMap<String, AllWrapper>> responseType =
                new ParameterizedTypeReference<TreeMap<String, AllWrapper>>() {
                };

        Map<String, AllWrapper> cases = restTemplate.exchange(getEntity("cases"), responseType).getBody();
        Map<String, AllWrapper> vaccines = restTemplate.exchange(getEntity("vaccines"), responseType).getBody();

        double result = doCalculations(cases, vaccines);
        return "correlation coefficient for all countries:" + result;
    }


    public String getAllCountriesByContinent(String continent) {
        ParameterizedTypeReference<TreeMap<String, AllWrapper>> responseType =
                new ParameterizedTypeReference<TreeMap<String, AllWrapper>>() {
                };
        Map<String, AllWrapper> cases = restTemplate.exchange(getEntity("cases" + "?continent=" + continent), responseType).getBody();
        Map<String, AllWrapper> vaccines = restTemplate.exchange(getEntity("vaccines" + "?continent=" + continent), responseType).getBody();
        double result = doCalculations(cases, vaccines);
        return "correlation coefficient for all countries in " + continent + " is: " + result;

    }

    private static RequestEntity<Void> getEntity(String param) {
        return RequestEntity.get(URL + param)
                .accept(MediaType.APPLICATION_JSON).build();
    }

    private static double doCalculations(Map<String, AllWrapper> cases, Map<String, AllWrapper> vaccines) {
        List<Double> percentageOfVaccinated = new ArrayList<>();
        List<Double> percentageOfDeath = new ArrayList<>();

        vaccines.forEach((country, allAttributes) -> {
            if (allAttributes.getAll().getVaccinated() != 0)
                percentageOfVaccinated.add((double) (allAttributes.getAll().getPopulation() / allAttributes.getAll().getVaccinated()));
            else
                percentageOfVaccinated.add(0.0);


        });
        cases.forEach((country, allAttributes) -> {
            if (allAttributes.getAll().getConfirmed() != 0)
                percentageOfDeath.add((double) (allAttributes.getAll().getDeaths() / (double) allAttributes.getAll().getConfirmed()));
            else
                percentageOfDeath.add(0.0);

        });

        double result = calculateCorrelationCoefficient(percentageOfDeath.stream().mapToDouble(v -> v).toArray(), percentageOfVaccinated.stream().mapToDouble(v -> v).toArray(), percentageOfVaccinated.size());
        return result;
    }


    /**
     * formula:
     * r=n(sommation(X*Y)-sommationX*sommationY)
     * /
     * square([n*(sommation x pow 2)-pow 2(sommation X)]*[n*sommation Y pow 2- pow 2(sommation Y)]
     */
    static double calculateCorrelationCoefficient(double[] x, double[] y, int n) {
        double sum_X = 0, sum_Y = 0, sum_XY = 0;
        double squareSum_X = 0, squareSum_Y = 0;
        for (int i = 0; i < n; i++) {
            // sum of elements of array X.
            sum_X = sum_X + x[i];

            // sum of elements of array Y.
            sum_Y = sum_Y + y[i];

            // sum of X[i] * Y[i].
            sum_XY = sum_XY + x[i] * y[i];

            // sum of square of array elements.
            squareSum_X = squareSum_X + x[i] * x[i];
            squareSum_Y = squareSum_Y + y[i] * y[i];
        }

        // use formula for calculating correlation
        // coefficient.
        return (n * sum_XY - sum_X * sum_Y) /
                Math.sqrt((n * squareSum_X -
                        sum_X * sum_X) * (n * squareSum_Y -
                        sum_Y * sum_Y));
    }


}

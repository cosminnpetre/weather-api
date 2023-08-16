package com.weather.model;

import lombok.Data;

@Data
public class CityDetails implements Comparable {
    private static final String DELIMITER = ",";

    private String name;
    private String temperature;
    private String wind;

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((CityDetails) o).getName());
    }

    public String generateCSVEntry() {
        String entryString = getName() + DELIMITER;
        if (getTemperature() != null) {
            entryString += getTemperature();
        }
        entryString += DELIMITER;
        if (getWind() != null) {
            entryString += getWind();
        }
        return entryString;
    }
}

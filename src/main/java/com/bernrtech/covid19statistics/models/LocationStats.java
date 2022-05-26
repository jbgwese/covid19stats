package com.bernrtech.covid19statistics.models;


import lombok.Data;

@Data
public class LocationStats {
    private String province;
    private String country;
    private int latestTotalCases;
    private int differenceFromPreviousRecord;
}

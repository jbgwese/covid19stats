package com.bernrtech.covid19statistics.services;

import com.bernrtech.covid19statistics.models.LocationStats;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class Covid19statisticsService {


    private static String COVID19_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    //private static String COVID19_DATA_URL = "https://127.0.0.1:8080/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();


    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    public void fetchCovid19Data() throws IOException, InterruptedException {
        List<LocationStats> freshStats = new ArrayList<>();


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(COVID19_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //System.out.println(httpResponse.body());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setProvince(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latestCases=Integer.parseInt(record.get(record.size() - 1));
            int previousRecordCases=Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalCases(latestCases);
           locationStats.setDifferenceFromPreviousRecord(latestCases-previousRecordCases);
            System.out.println(locationStats);

            freshStats.add(locationStats);


        }
        this.allStats = freshStats;

    }
}

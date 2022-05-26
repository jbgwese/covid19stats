package com.bernrtech.covid19statistics.controllers;

import com.bernrtech.covid19statistics.models.LocationStats;
import com.bernrtech.covid19statistics.services.Covid19statisticsService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    Covid19statisticsService covid19statisticsService;
    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats =covid19statisticsService.getAllStats();
      int totalReportedCovidCases=  allStats.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
      int totalNewCovidCases=  allStats.stream().mapToInt(stat->stat.getDifferenceFromPreviousRecord()).sum();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCovidCases", totalReportedCovidCases);
        model.addAttribute("totalNewCovidCases", totalNewCovidCases);
        return "home";
    }

}

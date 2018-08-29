package com.kozanoglu.adtech.controller;

import com.kozanoglu.adtech.dto.result.ResultDTO;
import com.kozanoglu.adtech.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ads")
public class StatisticsController {

    private StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/statistics", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResultDTO getGroupedStatisticsBetweenDates(@RequestParam("start")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                 LocalDateTime startDate,
                                               @RequestParam("end")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                 LocalDateTime endDate,
                                               @RequestParam(value = "group_by", required = false)
                                                                 String[] groupingParams) {

        return groupingParams != null ?
                statisticsService.getGroupedStatisticsBetweenDates(startDate, endDate, groupingParams) :
                statisticsService.getStatisticsBetweenDates(startDate, endDate);
    }
}

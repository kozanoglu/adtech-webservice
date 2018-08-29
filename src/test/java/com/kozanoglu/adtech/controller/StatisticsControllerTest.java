package com.kozanoglu.adtech.controller;

import com.kozanoglu.adtech.controller.exception.GlobalExceptionHandler;
import com.kozanoglu.adtech.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StatisticsControllerTest {

    private MockMvc mockMvc;
    private StatisticsService statisticsService;

    @Before
    public void setup() {
        statisticsService = Mockito.mock(StatisticsService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new StatisticsController(statisticsService)).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    public void shouldGet200WhenStatisticsRequested() throws Exception {
        this.mockMvc.perform(get("/ads/statistics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", LocalDateTime.now().toString())
                .param("end", LocalDateTime.now().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet200WhenGroupedStatisticsRequested() throws Exception {
        this.mockMvc.perform(get("/ads/statistics")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", LocalDateTime.now().toString())
                .param("end", LocalDateTime.now().toString())
                .param("group_by", "os")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

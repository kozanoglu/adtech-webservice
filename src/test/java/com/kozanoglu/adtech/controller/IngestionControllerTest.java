package com.kozanoglu.adtech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozanoglu.adtech.controller.exception.ClickNotFoundException;
import com.kozanoglu.adtech.controller.exception.DeliveryNotFoundException;
import com.kozanoglu.adtech.controller.exception.GlobalExceptionHandler;
import com.kozanoglu.adtech.dto.request.ClickRequestDTO;
import com.kozanoglu.adtech.dto.request.DeliveryRequestDTO;
import com.kozanoglu.adtech.dto.request.InstallRequestDTO;
import com.kozanoglu.adtech.service.IngestionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class IngestionControllerTest {

    private MockMvc mockMvc;
    private IngestionService ingestionService;

    @Before
    public void setup() {
        ingestionService = Mockito.mock(IngestionService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new IngestionController(ingestionService)).setControllerAdvice(GlobalExceptionHandler.class).build();

    }

    @Test
    public void shouldGet200WhenSuccessfulDeliveryPost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/ads/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DeliveryRequestDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet200WhenSuccessfulClickPost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/ads/click")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ClickRequestDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet404WhenDeliveryNotFound() throws Exception {
        doThrow(new DeliveryNotFoundException(new JpaObjectRetrievalFailureException(new EntityNotFoundException()))).when(ingestionService).saveClick(any());
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/ads/click")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ClickRequestDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGet200WhenSuccessfulInstallPost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/ads/install")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new InstallRequestDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet404WhenClickNotFound() throws Exception {
        doThrow(new ClickNotFoundException(new JpaObjectRetrievalFailureException(new EntityNotFoundException()))).when(ingestionService).saveInstall(any());
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/ads/install")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new InstallRequestDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

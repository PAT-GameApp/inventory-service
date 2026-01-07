package com.cognizant.inventory_service.controller;

// ...existing imports...
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AllotmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAllotments_returnsOk() throws Exception {
        mockMvc.perform(get("/allotments/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllotmentById_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/allotments/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }

    @Test
    void addAllotment_returnsCreatedOrBadRequest() throws Exception {
        String requestJson = "{" +
                "\"equipmentId\":10," +
                "\"userId\":20" +
                "}";

        mockMvc.perform(post("/allotments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 201 && status != 400) {
                        throw new AssertionError("Expected 201 or 400 but got " + status);
                    }
                });
    }

    @Test
    void deleteAllotment_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(delete("/allotments/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }
}
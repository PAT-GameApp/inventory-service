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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEquipment_returnsOk() throws Exception {
        mockMvc.perform(get("/equipment/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getEquipmentById_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/equipment/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }

    @Test
    void createEquipment_returnsCreatedOrBadRequest() throws Exception {
        String requestJson = "{" +
                "\"name\":\"Test Equipment\"," +
                "\"quantity\":10" +
                "}";

        mockMvc.perform(post("/equipment/")
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
    void updateEquipment_returnsOkOrNotFoundOrBadRequest() throws Exception {
        String requestJson = "{" +
                "\"name\":\"Updated Equipment\"," +
                "\"quantity\":15" +
                "}";

        mockMvc.perform(put("/equipment/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 400 && status != 404) {
                        throw new AssertionError("Expected 200, 400 or 404 but got " + status);
                    }
                });
    }

    @Test
    void deleteEquipment_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(delete("/equipment/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    int status = result.getResponse().getStatus();
                    if (status != 200 && status != 404) {
                        throw new AssertionError("Expected 200 or 404 but got " + status);
                    }
                });
    }
}
package com.example.EverExpanding.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testOpenTheSun() throws Exception {
        mockMvc.perform(get("/planets/sun"))
                .andExpect(status().isOk())
                .andExpect(view().name("sun"));
    }

    @Test
    void testOpenMercury() throws Exception {
        mockMvc.perform(get("/planets/mercury"))
                .andExpect(status().isOk())
                .andExpect(view().name("mercury"));
    }

    @Test
    void testOpenVenus() throws Exception {
        mockMvc.perform(get("/planets/venus"))
                .andExpect(status().isOk())
                .andExpect(view().name("venus"));
    }

    @Test
    void testOpenTheEarth() throws Exception {
        mockMvc.perform(get("/planets/earth"))
                .andExpect(status().isOk())
                .andExpect(view().name("earth"));
    }

    @Test
    void testOpenTheMoon() throws Exception {
        mockMvc.perform(get("/planets/theMoon"))
                .andExpect(status().isOk())
                .andExpect(view().name("theMoon"));
    }

    @Test
    void testOpenMars() throws Exception {
        mockMvc.perform(get("/planets/mars"))
                .andExpect(status().isOk())
                .andExpect(view().name("mars"));
    }

    @Test
    void testOpenJupiter() throws Exception {
        mockMvc.perform(get("/planets/jupiter"))
                .andExpect(status().isOk())
                .andExpect(view().name("jupiter"));
    }

    @Test
    void testOpenSaturn() throws Exception {
        mockMvc.perform(get("/planets/saturn"))
                .andExpect(status().isOk())
                .andExpect(view().name("saturn"));
    }

    @Test
    void testOpenUranus() throws Exception {
        mockMvc.perform(get("/planets/uranus"))
                .andExpect(status().isOk())
                .andExpect(view().name("uranus"));
    }

    @Test
    void testOpenNeptune() throws Exception {
        mockMvc.perform(get("/planets/neptune"))
                .andExpect(status().isOk())
                .andExpect(view().name("neptune"));
    }
}

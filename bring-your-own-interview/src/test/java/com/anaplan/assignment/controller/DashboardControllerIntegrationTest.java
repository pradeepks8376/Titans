package com.anaplan.assignment.controller;

import com.anaplan.assignment.model.Dashboard;
import com.anaplan.assignment.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DashboardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    void shouldReturnDashboardList() throws Exception {
        mockMvc.perform(get("/anaplan/dashboards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Dashboard1"))
                .andExpect(jsonPath("$[0].createdAt").value("2023-09-15T09:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].updatedAt").value("2023-09-15T09:00:00.000+00:00"));
    }

    @Test
    void testCreateDashboard_Success() throws Exception {
        // Given
        Date now = new Date();
        Dashboard dashboard = new Dashboard(null, "New Dashboard", now, now);
        Dashboard savedDashboard = new Dashboard(1L, "New Dashboard", now, now);

        when(dashboardService.saveDashboard(any(Dashboard.class))).thenReturn(savedDashboard);

        // When / Then
        mockMvc.perform(post("/api/dashboards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"New Dashboard\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Dashboard"));
    }
}

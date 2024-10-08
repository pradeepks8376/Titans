package com.anaplan.assignment.service;

import com.anaplan.assignment.model.Dashboard;
import com.anaplan.assignment.repo.DashboardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnDashboardListWhenAvailable() {
        List<Dashboard> mockDashboards = List.of(new Dashboard(1L, "Test Dashboard", new Date(), new Date()));
        Mockito.when(dashboardRepository.findAll()).thenReturn(mockDashboards);

        List<Dashboard> result = dashboardService.getAllDashboards();

        assertEquals(1, result.size());
        assertEquals("Test Dashboard", result.get(0).getTitle());
    }

    //This test checks if the DashboardService correctly retrieves a Dashboard when it exists in the repository.
    @Test
    void testGetDashboardById_DashboardExists() {
        // Given
        Long dashboardId = 1L;
        Date now = new Date();
        Dashboard dashboard = new Dashboard(1L, "Sales Dashboard", now, now);
        when(dashboardRepository.findById(dashboardId)).thenReturn(Optional.of(dashboard));

        // When
        Dashboard result = dashboardService.getDashboardById(dashboardId);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sales Dashboard", result.getTitle());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
        verify(dashboardRepository, times(1)).findById(dashboardId);
    }

    // This test checks if the service handles the case where a Dashboard is not found (returns null).
    @Test
    void testGetDashboardById_DashboardDoesNotExist() {
        // Given
        Long dashboardId = 2L;
        when(dashboardRepository.findById(dashboardId)).thenReturn(Optional.empty());

        // When
        Dashboard result = dashboardService.getDashboardById(dashboardId);

        // Then
        assertNull(result);
        verify(dashboardRepository, times(1)).findById(dashboardId);
    }

    @Test
    void testSaveDashboard_Success() {
        // Given
        Date now = new Date();
        Dashboard dashboard = new Dashboard(null, "New Dashboard", now, now);
        Dashboard savedDashboard = new Dashboard(1L, "New Dashboard", now, now);

        when(dashboardRepository.save(dashboard)).thenReturn(savedDashboard);

        // When
        Dashboard result = dashboardService.saveDashboard(dashboard);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Dashboard", result.getTitle());
        verify(dashboardRepository, times(1)).save(dashboard);
    }

    @Test
    void testUpdateDashboard_Success() {
        // Given
        Long dashboardId = 1L;
        Date now = new Date();
        Dashboard existingDashboard = new Dashboard(1L, "Old Title", now, now);
        Dashboard dashboardDetails = new Dashboard(null, "New Title", now, now);

        when(dashboardRepository.findById(dashboardId)).thenReturn(Optional.of(existingDashboard));
        when(dashboardRepository.save(existingDashboard)).thenReturn(existingDashboard);

        // When
        Dashboard result = dashboardService.updateDashboard(dashboardId, dashboardDetails);

        // Then
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        verify(dashboardRepository, times(1)).findById(dashboardId);
        verify(dashboardRepository, times(1)).save(existingDashboard);
    }

    @Test
    void testUpdateDashboard_DashboardNotFound() {
        // Given
        Long dashboardId = 1L;
        Dashboard dashboardDetails = new Dashboard(null, "New Title", new Date(), new Date());

        when(dashboardRepository.findById(dashboardId)).thenReturn(Optional.empty());

        // When
        Dashboard result = dashboardService.updateDashboard(dashboardId, dashboardDetails);

        // Then
        assertNull(result);
        verify(dashboardRepository, times(1)).findById(dashboardId);
        verify(dashboardRepository, times(0)).save(any());
    }

    @Test
    void testDeleteDashboard_Success() {
        // Given
        Long dashboardId = 1L;
        when(dashboardRepository.existsById(dashboardId)).thenReturn(true);

        // When
        boolean result = dashboardService.deleteDashboard(dashboardId);

        // Then
        assertTrue(result);
        verify(dashboardRepository, times(1)).existsById(dashboardId);
        verify(dashboardRepository, times(1)).deleteById(dashboardId);
    }

    @Test
    void testDeleteDashboard_DashboardNotFound() {
        // Given
        Long dashboardId = 1L;
        when(dashboardRepository.existsById(dashboardId)).thenReturn(false);

        // When
        boolean result = dashboardService.deleteDashboard(dashboardId);

        // Then
        assertFalse(result);
        verify(dashboardRepository, times(1)).existsById(dashboardId);
        verify(dashboardRepository, times(0)).deleteById(any());
    }
}


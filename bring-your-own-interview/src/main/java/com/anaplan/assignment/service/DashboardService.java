package com.anaplan.assignment.service;

import com.anaplan.assignment.model.Dashboard;
import com.anaplan.assignment.repo.DashboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<Dashboard>  getAllDashboards() {
        log.info("In DashboardService - getAllDashboards");
        return dashboardRepository.findAll();
    }

    public Dashboard getDashboardById(Long id) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        return dashboard.orElse(null); // Return null if not found, handle appropriately
    }

    // Method to save a dashboard
    public Dashboard saveDashboard(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    // PUT API: Update dashboard
    public Dashboard updateDashboard(Long id, Dashboard updatedDashboard) {
        Optional<Dashboard> existingDashboardOpt = dashboardRepository.findById(id);
        if (existingDashboardOpt.isPresent()) {
            Dashboard existingDashboard = existingDashboardOpt.get();
            existingDashboard.setTitle(updatedDashboard.getTitle());
            existingDashboard.setUpdatedAt(new java.util.Date());
            return dashboardRepository.save(existingDashboard);
        }
        return null;
    }

    // DELETE API: Delete dashboard by ID
    public boolean deleteDashboard(Long id) {
        if (dashboardRepository.existsById(id)) {
            dashboardRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

package com.anaplan.assignment.controller;

import com.anaplan.assignment.exception.DashboardNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.anaplan.assignment.service.DashboardService;
import com.anaplan.assignment.model.Dashboard;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/anaplan")
public class DashboardController {

    @Autowired
    private final DashboardService dashboardService;

    public DashboardController (DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "Get a list of all dashboards", description = "Returns a list of dashboard objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dashboard>>  getDashboards() {
        log.info("DashboardController getDashboards:");
        List<Dashboard> dashboards = dashboardService.getAllDashboards();
        if (dashboards == null || dashboards.isEmpty()) {
            throw new DashboardNotFoundException("No dashboard found: ");
        }
        return ResponseEntity.ok(dashboards);
    }

    @GetMapping(value = "/{id}/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of  dashboard by id", description = "Returns a dashboard object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Dashboard.class)) }),
            @ApiResponse(responseCode = "404", description = "Dashboard not found", content = @Content)
    })
    public ResponseEntity<Dashboard> getById(@PathVariable(name = "id") Long id) {
        log.info("Dashboard get {}", id);
        Dashboard dashboard = dashboardService.getDashboardById(id);
        if (dashboard != null) {
            return new ResponseEntity<>(dashboard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new dashboard", description = "Creates a new dashboard with the provided details and saves it to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dashboard successfully created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Dashboard.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) {
        log.info("Dashboard createDashboard: ");
        Dashboard savedDashboard = dashboardService.saveDashboard(dashboard);
        return new ResponseEntity<>(savedDashboard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dashboard> updateDashboard(@PathVariable Long id, @RequestBody Dashboard dashboard) {
        Optional<Dashboard> existingDashboard = Optional.ofNullable(dashboardService.getDashboardById(id));

        if (existingDashboard.isPresent()) {
            dashboard.setId(id); // Ensure the ID remains unchanged
            dashboard.setUpdatedAt(new java.util.Date()); // Set the updated date
            Dashboard updatedDashboard = dashboardService.saveDashboard(dashboard);
            return new ResponseEntity<>(updatedDashboard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a dashboard by ID", description = "Deletes a dashboard based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dashboard successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Dashboard not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDashboard(@PathVariable Long id) {
        Dashboard existingDashboard = dashboardService.getDashboardById(id);
        if (existingDashboard != null) {
            dashboardService.deleteDashboard(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}

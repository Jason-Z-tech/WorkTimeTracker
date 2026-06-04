package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.dto.TimeEntryResponseDto;
import ch.jason.zeitz.worktime.tracker.mapper.DtoMapper;
import ch.jason.zeitz.worktime.tracker.service.TimeEntryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reports", description = "Auswertungen und Berichte zu Zeiteinträgen")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final TimeEntryService timeEntryService;

    public ReportController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @Operation(
            summary = "Bericht eines Benutzers abrufen",
            description = "Gibt alle Zeiteinträge eines bestimmten Benutzers als Bericht zurück. Benutzer mit ROLE_read, ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bericht erfolgreich geladen"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Benutzer oder Zeiteinträge nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_read','ROLE_update','ROLE_admin')")
    @GetMapping("/user/{userId}")
    public List<TimeEntryResponseDto> getReportByUser(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Long userId
    ) {
        return timeEntryService.getTimeEntriesByUserId(userId)
                .stream()
                .map(DtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Alle Berichte abrufen",
            description = "Gibt alle Zeiteinträge aller Benutzer zurück. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alle Berichte erfolgreich geladen"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @GetMapping("/all")
    public List<TimeEntryResponseDto> getAllReports() {
        return timeEntryService.getAllTimeEntries()
                .stream()
                .map(DtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
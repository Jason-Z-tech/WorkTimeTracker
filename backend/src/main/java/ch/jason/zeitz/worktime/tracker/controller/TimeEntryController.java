package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.dto.TimeEntryRequestDto;
import ch.jason.zeitz.worktime.tracker.dto.TimeEntryResponseDto;
import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;
import ch.jason.zeitz.worktime.tracker.mapper.DtoMapper;
import ch.jason.zeitz.worktime.tracker.service.TimeEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Time Entries", description = "Verwaltung der Zeiteinträge")
@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @Operation(
            summary = "Zeiteintrag nach ID abrufen",
            description = "Gibt einen einzelnen Zeiteintrag anhand seiner ID zurück. Benutzer mit ROLE_read, ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Zeiteintrag erfolgreich gefunden"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Zeiteintrag nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_read','ROLE_update','ROLE_admin')")
    @GetMapping("/{id}")
    public TimeEntryResponseDto getTimeEntryById(
            @Parameter(description = "ID des Zeiteintrags", example = "1")
            @PathVariable Long id
    ) {
        return DtoMapper.toResponseDto(timeEntryService.getTimeEntryById(id));
    }

    @Operation(
            summary = "Zeiteinträge eines Benutzers abrufen",
            description = "Gibt alle Zeiteinträge eines bestimmten Benutzers zurück. Benutzer mit ROLE_read, ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Zeiteinträge erfolgreich geladen"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Benutzer oder Zeiteinträge nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_read','ROLE_update','ROLE_admin')")
    @GetMapping("/user/{userId}")
    public List<TimeEntryResponseDto> getTimeEntriesByUserId(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Long userId
    ) {
        return timeEntryService.getTimeEntriesByUserId(userId)
                .stream()
                .map(DtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Neuen Zeiteintrag erstellen",
            description = "Erstellt einen neuen Zeiteintrag. Benutzer mit ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Zeiteintrag erfolgreich erstellt"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_update','ROLE_admin')")
    @PostMapping
    public TimeEntryResponseDto createTimeEntry(
            @Parameter(description = "Daten des neuen Zeiteintrags")
            @Valid @RequestBody TimeEntryRequestDto timeEntryDto
    ) {
        TimeEntry savedTimeEntry = timeEntryService.createTimeEntry(timeEntryDto);
        return DtoMapper.toResponseDto(savedTimeEntry);
    }

    @Operation(
            summary = "Zeiteintrag aktualisieren",
            description = "Aktualisiert einen bestehenden Zeiteintrag anhand seiner ID. Benutzer mit ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Zeiteintrag erfolgreich aktualisiert"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Zeiteintrag nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_update','ROLE_admin')")
    @PutMapping("/{id}")
    public TimeEntryResponseDto updateTimeEntry(
            @Parameter(description = "ID des Zeiteintrags", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Neue Daten des Zeiteintrags")
            @Valid @RequestBody TimeEntryRequestDto timeEntryDto
    ) {
        TimeEntry updatedTimeEntry = timeEntryService.updateTimeEntry(id, timeEntryDto);
        return DtoMapper.toResponseDto(updatedTimeEntry);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Zeiteintrag löschen",
            description = "Löscht einen Zeiteintrag anhand seiner ID. Benutzer mit ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Zeiteintrag erfolgreich gelöscht"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Zeiteintrag nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_update','ROLE_admin')")
    @DeleteMapping("/{id}")
    public void deleteTimeEntry(
            @Parameter(description = "ID des Zeiteintrags", example = "1")
            @PathVariable Long id
    ) {
        timeEntryService.deleteTimeEntry(id);
    }
}
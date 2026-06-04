package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.dto.ProjectRequestDto;
import ch.jason.zeitz.worktime.tracker.dto.ProjectResponseDto;
import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.mapper.DtoMapper;
import ch.jason.zeitz.worktime.tracker.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Projects", description = "Verwaltung der Projekte")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Alle Projekte abrufen",
            description = "Gibt eine Liste aller Projekte zurück. Benutzer mit ROLE_read, ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekte erfolgreich geladen"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_read','ROLE_update','ROLE_admin')")
    @GetMapping
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getAllProjects()
                .stream()
                .map(DtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Projekt nach ID abrufen",
            description = "Gibt ein einzelnes Projekt anhand seiner ID zurück. Benutzer mit ROLE_read, ROLE_update oder ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich gefunden"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_read','ROLE_update','ROLE_admin')")
    @GetMapping("/{id}")
    public ProjectResponseDto getProjectById(
            @Parameter(description = "ID des Projekts", example = "1")
            @PathVariable Long id
    ) {
        return DtoMapper.toResponseDto(projectService.getProjectById(id));
    }

    @Operation(
            summary = "Neues Projekt erstellen",
            description = "Erstellt ein neues Projekt. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich erstellt"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @PostMapping
    public ProjectResponseDto createProject(
            @Parameter(description = "Projektdaten des neuen Projekts")
            @Valid @RequestBody ProjectRequestDto projectDto
    ) {
        Project project = DtoMapper.toEntity(projectDto);
        Project savedProject = projectService.createProject(project);
        return DtoMapper.toResponseDto(savedProject);
    }

    @Operation(
            summary = "Projekt aktualisieren",
            description = "Aktualisiert ein bestehendes Projekt anhand seiner ID. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projekt erfolgreich aktualisiert"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @PutMapping("/{id}")
    public ProjectResponseDto updateProject(
            @Parameter(description = "ID des Projekts", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Neue Projektdaten")
            @Valid @RequestBody ProjectRequestDto projectDto
    ) {
        Project project = DtoMapper.toEntity(projectDto);
        Project updatedProject = projectService.updateProject(id, project);
        return DtoMapper.toResponseDto(updatedProject);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Projekt löschen",
            description = "Löscht ein Projekt anhand seiner ID. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Projekt erfolgreich gelöscht"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Projekt nicht gefunden")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @DeleteMapping("/{id}")
    public void deleteProject(
            @Parameter(description = "ID des Projekts", example = "1")
            @PathVariable Long id
    ) {
        projectService.deleteProject(id);
    }
}
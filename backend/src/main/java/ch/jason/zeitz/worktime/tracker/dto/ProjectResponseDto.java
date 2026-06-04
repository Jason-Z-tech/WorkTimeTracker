package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO fuer die Rueckgabe eines Projekts")
public class ProjectResponseDto {

    @Schema(description = "Eindeutige ID des Projekts", example = "1")
    private Long id;

    @Schema(description = "Name des Projekts", example = "Projekt A")
    private String name;

    @Schema(description = "Beschreibung des Projekts", example = "Testprojekt fuer die Zeiterfassung")
    private String description;

    @Schema(description = "Gibt an, ob das Projekt aktiv ist", example = "true")
    private boolean active;

    public ProjectResponseDto() {
    }

    public ProjectResponseDto(Long id, String name, String description, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
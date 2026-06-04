package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO zum Erstellen oder Aktualisieren eines Projekts")
public class ProjectRequestDto {

    @Schema(description = "Name des Projekts", example = "Projekt A")
    @NotBlank
    private String name;

    @Schema(description = "Beschreibung des Projekts", example = "Testprojekt fuer die Zeiterfassung")
    private String description;

    @Schema(description = "Gibt an, ob das Projekt aktiv ist", example = "true")
    private boolean active = true;

    public ProjectRequestDto() {
    }

    public ProjectRequestDto(String name, String description, boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
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
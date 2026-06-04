package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response DTO fuer die Rueckgabe eines Zeiteintrags")
public class TimeEntryResponseDto {

    @Schema(description = "Eindeutige ID des Zeiteintrags", example = "1")
    private Long id;

    @Schema(description = "Startzeit des Zeiteintrags", example = "2026-05-04T08:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Endzeit des Zeiteintrags", example = "2026-05-04T16:00:00")
    private LocalDateTime endTime;

    @Schema(description = "Automatisch berechnete Dauer in Minuten", example = "480")
    private Long durationMinutes;

    @Schema(description = "Benutzer, dem der Zeiteintrag zugeordnet ist")
    private AppUserResponseDto user;

    @Schema(description = "Projekt, dem der Zeiteintrag zugeordnet ist")
    private ProjectResponseDto project;

    public TimeEntryResponseDto() {
    }

    public TimeEntryResponseDto(
            Long id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Long durationMinutes,
            AppUserResponseDto user,
            ProjectResponseDto project
    ) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.user = user;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getDurationMinutes() {
        return durationMinutes;
    }

    public AppUserResponseDto getUser() {
        return user;
    }

    public ProjectResponseDto getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setUser(AppUserResponseDto user) {
        this.user = user;
    }

    public void setProject(ProjectResponseDto project) {
        this.project = project;
    }
}
package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request DTO zum Erstellen oder Aktualisieren eines Zeiteintrags")
public class TimeEntryRequestDto {

    @Schema(description = "Startzeit des Zeiteintrags", example = "2026-05-04T08:00:00")
    @NotNull
    private LocalDateTime startTime;

    @Schema(description = "Endzeit des Zeiteintrags", example = "2026-05-04T16:00:00")
    @NotNull
    private LocalDateTime endTime;

    @Schema(description = "ID des Benutzers", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "ID des Projekts", example = "1")
    @NotNull
    private Long projectId;

    public TimeEntryRequestDto() {
    }

    public TimeEntryRequestDto(LocalDateTime startTime, LocalDateTime endTime, Long userId, Long projectId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.projectId = projectId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
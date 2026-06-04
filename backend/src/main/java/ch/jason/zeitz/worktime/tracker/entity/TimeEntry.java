package ch.jason.zeitz.worktime.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_entries")
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    private Long durationMinutes;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public TimeEntry() {
    }

    public TimeEntry(LocalDateTime startTime, LocalDateTime endTime, AppUser user, Project project) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.project = project;
        calculateDuration();
    }

    @PrePersist
    @PreUpdate
    public void calculateDuration() {
        if (startTime != null && endTime != null) {
            this.durationMinutes = Duration.between(startTime, endTime).toMinutes();
        }
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

    public AppUser getUser() {
        return user;
    }

    public Project getProject() {
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

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
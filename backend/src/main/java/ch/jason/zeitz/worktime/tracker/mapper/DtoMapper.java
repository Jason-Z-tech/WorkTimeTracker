package ch.jason.zeitz.worktime.tracker.mapper;

import ch.jason.zeitz.worktime.tracker.dto.AppUserRequestDto;
import ch.jason.zeitz.worktime.tracker.dto.AppUserResponseDto;
import ch.jason.zeitz.worktime.tracker.dto.ProjectRequestDto;
import ch.jason.zeitz.worktime.tracker.dto.ProjectResponseDto;
import ch.jason.zeitz.worktime.tracker.dto.TimeEntryResponseDto;
import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;

public class DtoMapper {

    private DtoMapper() {
    }

    public static AppUser toEntity(AppUserRequestDto dto) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static AppUserResponseDto toResponseDto(AppUser user) {
        return new AppUserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static Project toEntity(ProjectRequestDto dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setActive(dto.isActive());
        return project;
    }

    public static ProjectResponseDto toResponseDto(Project project) {
        return new ProjectResponseDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.isActive()
        );
    }

    public static TimeEntryResponseDto toResponseDto(TimeEntry timeEntry) {
        return new TimeEntryResponseDto(
                timeEntry.getId(),
                timeEntry.getStartTime(),
                timeEntry.getEndTime(),
                timeEntry.getDurationMinutes(),
                toResponseDto(timeEntry.getUser()),
                toResponseDto(timeEntry.getProject())
        );
    }
}
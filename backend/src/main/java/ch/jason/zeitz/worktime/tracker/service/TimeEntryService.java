package ch.jason.zeitz.worktime.tracker.service;

import ch.jason.zeitz.worktime.tracker.dto.TimeEntryRequestDto;
import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;
import ch.jason.zeitz.worktime.tracker.repository.AppUserRepository;
import ch.jason.zeitz.worktime.tracker.repository.ProjectRepository;
import ch.jason.zeitz.worktime.tracker.repository.TimeEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;

    public TimeEntryService(TimeEntryRepository timeEntryRepository,
                            AppUserRepository appUserRepository,
                            ProjectRepository projectRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.appUserRepository = appUserRepository;
        this.projectRepository = projectRepository;
    }

    public List<TimeEntry> getAllTimeEntries() {
        return timeEntryRepository.findAll();
    }

    public List<TimeEntry> getTimeEntriesByUserId(Long userId) {
        return timeEntryRepository.findByUserId(userId);
    }

    public TimeEntry getTimeEntryById(Long id) {
        return timeEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zeiteintrag nicht gefunden"));
    }

    public TimeEntry createTimeEntry(TimeEntryRequestDto timeEntryDto) {
        AppUser user = appUserRepository.findById(timeEntryDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User nicht gefunden"));

        Project project = projectRepository.findById(timeEntryDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projekt nicht gefunden"));

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setStartTime(timeEntryDto.getStartTime());
        timeEntry.setEndTime(timeEntryDto.getEndTime());
        timeEntry.setUser(user);
        timeEntry.setProject(project);

        return timeEntryRepository.save(timeEntry);
    }

    public TimeEntry updateTimeEntry(Long id, TimeEntryRequestDto timeEntryDto) {
        TimeEntry timeEntry = getTimeEntryById(id);

        AppUser user = appUserRepository.findById(timeEntryDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User nicht gefunden"));

        Project project = projectRepository.findById(timeEntryDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projekt nicht gefunden"));

        timeEntry.setStartTime(timeEntryDto.getStartTime());
        timeEntry.setEndTime(timeEntryDto.getEndTime());
        timeEntry.setUser(user);
        timeEntry.setProject(project);

        return timeEntryRepository.save(timeEntry);
    }

    public void deleteTimeEntry(Long id) {
        timeEntryRepository.deleteById(id);
    }
}
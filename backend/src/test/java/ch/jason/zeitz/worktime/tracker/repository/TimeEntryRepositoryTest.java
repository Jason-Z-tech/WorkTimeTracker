package ch.jason.zeitz.worktime.tracker.repository;

import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TimeEntryRepositoryTest {

    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private AppUser createUser() {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");
        return appUserRepository.save(user);
    }

    private Project createProject() {
        Project project = new Project();
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);
        return projectRepository.save(project);
    }

    @Test
    void save_shouldCreateTimeEntry() {
        AppUser user = createUser();
        Project project = createProject();

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setUser(user);
        timeEntry.setProject(project);
        timeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 8, 0));
        timeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 16, 0));

        TimeEntry savedTimeEntry = timeEntryRepository.save(timeEntry);

        assertThat(savedTimeEntry.getId()).isNotNull();
        assertThat(savedTimeEntry.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedTimeEntry.getProject().getId()).isEqualTo(project.getId());
    }

    @Test
    void findById_shouldReturnTimeEntry() {
        AppUser user = createUser();
        Project project = createProject();

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setUser(user);
        timeEntry.setProject(project);
        timeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 8, 0));
        timeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 16, 0));

        TimeEntry savedTimeEntry = timeEntryRepository.save(timeEntry);

        Optional<TimeEntry> foundTimeEntry = timeEntryRepository.findById(savedTimeEntry.getId());

        assertThat(foundTimeEntry).isPresent();
        assertThat(foundTimeEntry.get().getId()).isEqualTo(savedTimeEntry.getId());
    }

    @Test
    void update_shouldUpdateTimeEntry() {
        AppUser user = createUser();
        Project project = createProject();

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setUser(user);
        timeEntry.setProject(project);
        timeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 8, 0));
        timeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 16, 0));

        TimeEntry savedTimeEntry = timeEntryRepository.save(timeEntry);

        savedTimeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 9, 0));
        savedTimeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 17, 0));

        TimeEntry updatedTimeEntry = timeEntryRepository.save(savedTimeEntry);

        assertThat(updatedTimeEntry.getId()).isEqualTo(savedTimeEntry.getId());
        assertThat(updatedTimeEntry.getStartTime()).isEqualTo(LocalDateTime.of(2026, 5, 4, 9, 0));
        assertThat(updatedTimeEntry.getEndTime()).isEqualTo(LocalDateTime.of(2026, 5, 4, 17, 0));
    }

    @Test
    void delete_shouldRemoveTimeEntry() {
        AppUser user = createUser();
        Project project = createProject();

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setUser(user);
        timeEntry.setProject(project);
        timeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 8, 0));
        timeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 16, 0));

        TimeEntry savedTimeEntry = timeEntryRepository.save(timeEntry);

        timeEntryRepository.deleteById(savedTimeEntry.getId());

        Optional<TimeEntry> deletedTimeEntry = timeEntryRepository.findById(savedTimeEntry.getId());

        assertThat(deletedTimeEntry).isEmpty();
    }
}
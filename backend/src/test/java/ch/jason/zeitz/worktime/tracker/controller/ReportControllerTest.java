package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;
import ch.jason.zeitz.worktime.tracker.service.TimeEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReportControllerTest {

    private MockMvc mockMvc;
    private TimeEntryService timeEntryService;

    @BeforeEach
    void setUp() {
        timeEntryService = Mockito.mock(TimeEntryService.class);
        ReportController reportController = new ReportController(timeEntryService);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    private TimeEntry createTestTimeEntry() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        Project project = new Project();
        project.setId(1L);
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setId(1L);
        timeEntry.setUser(user);
        timeEntry.setProject(project);
        timeEntry.setStartTime(LocalDateTime.of(2026, 5, 4, 8, 0));
        timeEntry.setEndTime(LocalDateTime.of(2026, 5, 4, 16, 0));
        timeEntry.setDurationMinutes(480L);

        return timeEntry;
    }

    @Test
    void getReportByUser_shouldReturnTimeEntries() throws Exception {
        Mockito.when(timeEntryService.getTimeEntriesByUserId(1L))
                .thenReturn(List.of(createTestTimeEntry()));

        mockMvc.perform(get("/api/reports/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].durationMinutes").value(480))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].project.id").value(1));
    }

    @Test
    void getAllReports_shouldReturnAllTimeEntries() throws Exception {
        Mockito.when(timeEntryService.getAllTimeEntries())
                .thenReturn(List.of(createTestTimeEntry()));

        mockMvc.perform(get("/api/reports/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].durationMinutes").value(480))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].project.id").value(1));
    }
}
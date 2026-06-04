package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.dto.TimeEntryRequestDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TimeEntryControllerTest {

    private MockMvc mockMvc;
    private TimeEntryService timeEntryService;

    @BeforeEach
    void setUp() {
        timeEntryService = Mockito.mock(TimeEntryService.class);
        TimeEntryController timeEntryController = new TimeEntryController(timeEntryService);
        mockMvc = MockMvcBuilders.standaloneSetup(timeEntryController).build();
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
    void getTimeEntryById_shouldReturnTimeEntry() throws Exception {
        Mockito.when(timeEntryService.getTimeEntryById(1L)).thenReturn(createTestTimeEntry());

        mockMvc.perform(get("/api/time-entries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.durationMinutes").value(480))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.project.id").value(1));
    }

    @Test
    void getTimeEntriesByUserId_shouldReturnTimeEntries() throws Exception {
        Mockito.when(timeEntryService.getTimeEntriesByUserId(1L)).thenReturn(List.of(createTestTimeEntry()));

        mockMvc.perform(get("/api/time-entries/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].durationMinutes").value(480))
                .andExpect(jsonPath("$[0].user.id").value(1))
                .andExpect(jsonPath("$[0].project.id").value(1));
    }

    @Test
    void createTimeEntry_shouldReturnCreatedTimeEntry() throws Exception {
        TimeEntry savedTimeEntry = createTestTimeEntry();

        Mockito.when(timeEntryService.createTimeEntry(any(TimeEntryRequestDto.class))).thenReturn(savedTimeEntry);

        String json = """
                {
                  "startTime": "2026-05-04T08:00:00",
                  "endTime": "2026-05-04T16:00:00",
                  "userId": 1,
                  "projectId": 1
                }
                """;

        mockMvc.perform(post("/api/time-entries")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.durationMinutes").value(480))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.project.id").value(1));
    }

    @Test
    void updateTimeEntry_shouldReturnUpdatedTimeEntry() throws Exception {
        TimeEntry updatedTimeEntry = createTestTimeEntry();

        Mockito.when(timeEntryService.updateTimeEntry(eq(1L), any(TimeEntryRequestDto.class))).thenReturn(updatedTimeEntry);

        String json = """
                {
                  "startTime": "2026-05-04T09:00:00",
                  "endTime": "2026-05-04T17:00:00",
                  "userId": 1,
                  "projectId": 1
                }
                """;

        mockMvc.perform(put("/api/time-entries/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.durationMinutes").value(480))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.project.id").value(1));
    }

    @Test
    void deleteTimeEntry_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/time-entries/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(timeEntryService).deleteTimeEntry(1L);
    }
}
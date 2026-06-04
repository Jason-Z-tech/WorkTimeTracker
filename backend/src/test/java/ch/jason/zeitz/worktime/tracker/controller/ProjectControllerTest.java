package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.entity.Project;
import ch.jason.zeitz.worktime.tracker.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjectControllerTest {

    private MockMvc mockMvc;
    private ProjectService projectService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        projectService = Mockito.mock(ProjectService.class);
        ProjectController projectController = new ProjectController(projectService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllProjects_shouldReturnProjects() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setName("Testprojekt");

        Mockito.when(projectService.getAllProjects()).thenReturn(List.of(project));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Testprojekt"));
    }

    @Test
    void getProjectById_shouldReturnProject() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setName("Testprojekt");

        Mockito.when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Testprojekt"));
    }

    @Test
    void createProject_shouldReturnCreatedProject() throws Exception {
        Project project = new Project();
        project.setName("Neues Projekt");

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setName("Neues Projekt");

        Mockito.when(projectService.createProject(any(Project.class))).thenReturn(savedProject);

        mockMvc.perform(post("/api/projects")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Neues Projekt"));
    }

    @Test
    void updateProject_shouldReturnUpdatedProject() throws Exception {
        Project project = new Project();
        project.setName("Update Projekt");

        Project updatedProject = new Project();
        updatedProject.setId(1L);
        updatedProject.setName("Update Projekt");

        Mockito.when(projectService.updateProject(eq(1L), any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/projects/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Update Projekt"));
    }

    @Test
    void deleteProject_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(projectService).deleteProject(1L);
    }
}
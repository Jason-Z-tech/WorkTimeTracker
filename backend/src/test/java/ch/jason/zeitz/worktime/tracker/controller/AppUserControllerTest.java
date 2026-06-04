package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.service.AppUserService;
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

class AppUserControllerTest {

    private MockMvc mockMvc;
    private AppUserService appUserService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        appUserService = Mockito.mock(AppUserService.class);
        AppUserController appUserController = new AppUserController(appUserService);
        mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUsers_shouldReturnUsers() throws Exception {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        Mockito.when(appUserService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("jason"))
                .andExpect(jsonPath("$[0].email").value("jason@test.ch"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        Mockito.when(appUserService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jason"))
                .andExpect(jsonPath("$.email").value("jason@test.ch"));
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        AppUser savedUser = new AppUser();
        savedUser.setId(1L);
        savedUser.setUsername("jason");
        savedUser.setEmail("jason@test.ch");

        Mockito.when(appUserService.createUser(any(AppUser.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jason"))
                .andExpect(jsonPath("$.email").value("jason@test.ch"));
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        AppUser user = new AppUser();
        user.setUsername("jason-update");
        user.setEmail("jason.update@test.ch");

        AppUser updatedUser = new AppUser();
        updatedUser.setId(1L);
        updatedUser.setUsername("jason-update");
        updatedUser.setEmail("jason.update@test.ch");

        Mockito.when(appUserService.updateUser(eq(1L), any(AppUser.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jason-update"))
                .andExpect(jsonPath("$.email").value("jason.update@test.ch"));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(appUserService).deleteUser(1L);
    }
}
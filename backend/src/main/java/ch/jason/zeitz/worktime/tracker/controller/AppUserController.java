package ch.jason.zeitz.worktime.tracker.controller;

import ch.jason.zeitz.worktime.tracker.dto.AppUserRequestDto;
import ch.jason.zeitz.worktime.tracker.dto.AppUserResponseDto;
import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import ch.jason.zeitz.worktime.tracker.mapper.DtoMapper;
import ch.jason.zeitz.worktime.tracker.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Verwaltung der Benutzer")
@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(
            summary = "Alle Benutzer abrufen",
            description = "Gibt eine Liste aller Benutzer zurück. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich geladen"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @GetMapping
    public List<AppUserResponseDto> getAllUsers() {
        return appUserService.getAllUsers()
                .stream()
                .map(DtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Benutzer nach ID abrufen",
            description = "Gibt einen einzelnen Benutzer anhand seiner ID zurück. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich gefunden"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @GetMapping("/{id}")
    public AppUserResponseDto getUserById(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Long id
    ) {
        return DtoMapper.toResponseDto(appUserService.getUserById(id));
    }

    @Operation(
            summary = "Neuen Benutzer erstellen",
            description = "Erstellt einen neuen Benutzer. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich erstellt"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @PostMapping
    public AppUserResponseDto createUser(
            @Parameter(description = "Benutzerdaten des neuen Benutzers")
            @Valid @RequestBody AppUserRequestDto userDto
    ) {
        AppUser user = DtoMapper.toEntity(userDto);
        AppUser savedUser = appUserService.createUser(user);
        return DtoMapper.toResponseDto(savedUser);
    }

    @Operation(
            summary = "Benutzer aktualisieren",
            description = "Aktualisiert einen bestehenden Benutzer anhand seiner ID. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich aktualisiert"),
            @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @PutMapping("/{id}")
    public AppUserResponseDto updateUser(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Neue Benutzerdaten")
            @Valid @RequestBody AppUserRequestDto userDto
    ) {
        AppUser user = DtoMapper.toEntity(userDto);
        AppUser updatedUser = appUserService.updateUser(id, user);
        return DtoMapper.toResponseDto(updatedUser);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Benutzer löschen",
            description = "Löscht einen Benutzer anhand seiner ID. Nur Benutzer mit der Rolle ROLE_admin dürfen diese Funktion verwenden."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Benutzer erfolgreich gelöscht"),
            @ApiResponse(responseCode = "401", description = "Nicht authentifiziert"),
            @ApiResponse(responseCode = "403", description = "Keine Berechtigung"),
            @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden")
    })
    @PreAuthorize("hasAuthority('ROLE_admin')")
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "ID des Benutzers", example = "1")
            @PathVariable Long id
    ) {
        appUserService.deleteUser(id);
    }
}
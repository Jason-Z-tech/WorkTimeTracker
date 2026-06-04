package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO fuer die Rueckgabe eines Benutzers")
public class AppUserResponseDto {

    @Schema(description = "Eindeutige ID des Benutzers", example = "1")
    private Long id;

    @Schema(description = "Benutzername des Benutzers", example = "jason")
    private String username;

    @Schema(description = "E-Mail-Adresse des Benutzers", example = "jason@test.ch")
    private String email;

    public AppUserResponseDto() {
    }

    public AppUserResponseDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
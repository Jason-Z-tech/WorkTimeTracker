package ch.jason.zeitz.worktime.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO zum Erstellen oder Aktualisieren eines Benutzers")
public class AppUserRequestDto {

    @Schema(description = "Benutzername des Benutzers", example = "jason")
    @NotBlank
    private String username;

    @Schema(description = "E-Mail-Adresse des Benutzers", example = "jason@test.ch")
    @Email
    @NotBlank
    private String email;

    public AppUserRequestDto() {
    }

    public AppUserRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
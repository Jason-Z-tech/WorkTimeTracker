package ch.jason.zeitz.worktime.tracker.repository;

import ch.jason.zeitz.worktime.tracker.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void save_shouldCreateUser() {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        AppUser savedUser = appUserRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("jason");
        assertThat(savedUser.getEmail()).isEqualTo("jason@test.ch");
    }

    @Test
    void findById_shouldReturnUser() {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        AppUser savedUser = appUserRepository.save(user);

        Optional<AppUser> foundUser = appUserRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("jason");
    }

    @Test
    void update_shouldUpdateUser() {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        AppUser savedUser = appUserRepository.save(user);

        savedUser.setUsername("jason-update");
        savedUser.setEmail("jason.update@test.ch");

        AppUser updatedUser = appUserRepository.save(savedUser);

        assertThat(updatedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(updatedUser.getUsername()).isEqualTo("jason-update");
        assertThat(updatedUser.getEmail()).isEqualTo("jason.update@test.ch");
    }

    @Test
    void delete_shouldRemoveUser() {
        AppUser user = new AppUser();
        user.setUsername("jason");
        user.setEmail("jason@test.ch");

        AppUser savedUser = appUserRepository.save(user);

        appUserRepository.deleteById(savedUser.getId());

        Optional<AppUser> deletedUser = appUserRepository.findById(savedUser.getId());

        assertThat(deletedUser).isEmpty();
    }
}
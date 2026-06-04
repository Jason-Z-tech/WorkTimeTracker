package ch.jason.zeitz.worktime.tracker.repository;

import ch.jason.zeitz.worktime.tracker.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void save_shouldCreateProject() {
        Project project = new Project();
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);

        Project savedProject = projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getName()).isEqualTo("Projekt A");
        assertThat(savedProject.getDescription()).isEqualTo("Beschreibung A");
        assertThat(savedProject.isActive()).isTrue();
    }

    @Test
    void findById_shouldReturnProject() {
        Project project = new Project();
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);

        Project savedProject = projectRepository.save(project);

        Optional<Project> foundProject = projectRepository.findById(savedProject.getId());

        assertThat(foundProject).isPresent();
        assertThat(foundProject.get().getName()).isEqualTo("Projekt A");
    }

    @Test
    void update_shouldUpdateProject() {
        Project project = new Project();
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);

        Project savedProject = projectRepository.save(project);

        savedProject.setName("Projekt Update");
        savedProject.setDescription("Beschreibung Update");
        savedProject.setActive(false);

        Project updatedProject = projectRepository.save(savedProject);

        assertThat(updatedProject.getId()).isEqualTo(savedProject.getId());
        assertThat(updatedProject.getName()).isEqualTo("Projekt Update");
        assertThat(updatedProject.getDescription()).isEqualTo("Beschreibung Update");
        assertThat(updatedProject.isActive()).isFalse();
    }

    @Test
    void delete_shouldRemoveProject() {
        Project project = new Project();
        project.setName("Projekt A");
        project.setDescription("Beschreibung A");
        project.setActive(true);

        Project savedProject = projectRepository.save(project);

        projectRepository.deleteById(savedProject.getId());

        Optional<Project> deletedProject = projectRepository.findById(savedProject.getId());

        assertThat(deletedProject).isEmpty();
    }
}
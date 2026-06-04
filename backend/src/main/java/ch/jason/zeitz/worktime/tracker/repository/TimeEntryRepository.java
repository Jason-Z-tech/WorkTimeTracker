package ch.jason.zeitz.worktime.tracker.repository;

import ch.jason.zeitz.worktime.tracker.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    List<TimeEntry> findByUserId(Long userId);

    List<TimeEntry> findByProjectId(Long projectId);

    List<TimeEntry> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
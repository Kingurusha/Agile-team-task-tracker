package cz.cvut.ear.dao;

import cz.cvut.ear.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);

    @Query("SELECT p FROM Project p WHERE p.startDateTime <= :dateTime AND p.endDateTime >= :dateTime")
    List<Project> findProjectsByDateTime(@Param("dateTime") LocalDateTime dateTime);
}

package cz.cvut.ear.dao;

import cz.cvut.ear.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    // TODO: replace in CP2 with an automatically generated Spring Data JPA request?
    @Query("SELECT s FROM Sprint s WHERE s.project.id = :projectId AND s.ordinalNumberInProject = :ordinalNumber")
    Optional<Sprint> findByOrdinalNumberInProject(@Param("projectId") Long projectId,
                                                  @Param("ordinalNumber") Integer sprintOrdinalNumberInProject);
}

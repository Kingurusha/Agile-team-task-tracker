package cz.cvut.ear.dao;

import cz.cvut.ear.model.Sprint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SprintRepository extends BaseRepository<Sprint, Long> {
    Optional<Sprint> findByProjectIdAndOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject);

    @Query("SELECT s FROM Sprint s WHERE LOWER(s.goal) LIKE LOWER(:keyword)")
    List<Sprint> findSprintsByGoalContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT s FROM Sprint s WHERE s.startDateTime BETWEEN :startDate AND :endDate")
    List<Sprint> findSprintsByStartDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

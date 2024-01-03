package cz.cvut.ear.repository;

import cz.cvut.ear.model.Sprint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SprintRepository extends BaseRepository<Sprint, Long> {
    Optional<Sprint> findByProjectIdAndOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject);

    @Query("SELECT s FROM Sprint s WHERE LOWER(s.goal) LIKE LOWER(:keyword)")
    List<Sprint> findSprintsByGoalKeyword(@Param("keyword") String keyword);

    @Query("SELECT s FROM Sprint s WHERE s.startDate BETWEEN :firstDate AND :secondDate")
    List<Sprint> findSprintsByStartDateBetween(@Param("firstDate") LocalDate firstDate,
                                               @Param("secondDate") LocalDate secondDate
    );
}

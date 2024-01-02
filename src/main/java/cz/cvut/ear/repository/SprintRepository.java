package cz.cvut.ear.repository;

import cz.cvut.ear.model.Sprint;

import java.util.Optional;

public interface SprintRepository extends BaseRepository<Sprint, Long> {
    Optional<Sprint> findByProjectIdAndOrdinalNumberInProject(Long projectId, Integer sprintOrdinalNumberInProject);
}

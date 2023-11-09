package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select b from Task b where b.taskName = :name")
    Task findByName(@Param("name") String name);
    List<Task> findByAssigneeUsername(String username);

    List<Task> findBySprintId(Long sprintId);

    List<Task> findByPriority(Priority priority);
}

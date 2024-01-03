package cz.cvut.ear.repository;

import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends BaseRepository<Task, Long>, TaskRepositoryCustom {
    List<Task> findByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus);

    List<Task> findBySprintId(Long sprintId);

    List<Task> findBySprintIdAndTaskPriority(Long sprintId, TaskPriority taskPriority);

    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.taskStatus != 'CLOSED'")
    List<Task> findOverdueOpenTasks();

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN CURRENT_DATE AND :endDate")
    List<Task> findTasksDueToDate(@Param("endDate") LocalDate endDate);
}

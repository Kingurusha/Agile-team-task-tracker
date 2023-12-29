package cz.cvut.ear.dao;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends BaseRepository<Task, Long> {
    Optional<Task> findByTaskName(String taskName);

    List<Task> findByAssigneeUsername(String username);
    List<Task> findByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus);

    List<Task> findBySprintId(Long sprintId);

    List<Task> findByTaskPriority(TaskPriority taskPriority);

    @Query("select t from Task t where t.assignee = :assignee and t.taskStatus != 'CLOSED'")
    List<Task> findByOpenTasksForAssignee(@Param("assignee")Employee assignee);

    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.taskStatus != 'CLOSED'")
    List<Task> findOverdueOpenTasks();

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN CURRENT_DATE AND :endDate")
    List<Task> findTasksDueInNextNDays(@Param("endDate") LocalDateTime endDate);
}

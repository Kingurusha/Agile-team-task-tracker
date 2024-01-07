package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TaskRepository extends BaseRepository<Task, Long>, TaskRepositoryCustom {
    List<Task> findByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus);

    List<Task> findBySprintId(Long sprintId);

    List<Task> findBySprintIdAndTaskPriority(Long sprintId, TaskPriority taskPriority);

    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.taskStatus != 'CLOSED'")
    List<Task> findOverdueOpenTasks();

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN CURRENT_DATE AND :endDate")
    List<Task> findTasksDueToDate(@Param("endDate") LocalDate endDate);
    Task findByLabelsId(Long labels_id);

    boolean existsByAssignee(Employee employee);
}

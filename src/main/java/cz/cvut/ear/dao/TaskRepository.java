package cz.cvut.ear.dao;

import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskName(String taskName);

    List<Task> findByAssigneeUsername(String username);
    List<Task> findByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus);

    List<Task> findBySprintId(Long sprintId);

    List<Task> findByPriority(TaskPriority taskPriority);
}

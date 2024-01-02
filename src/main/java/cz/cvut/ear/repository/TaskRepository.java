package cz.cvut.ear.repository;

import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskStatus;

import java.util.List;

public interface TaskRepository extends BaseRepository<Task, Long>, TaskRepositoryCustom {
    List<Task> findByAssigneeUsername(String username);
    List<Task> findByAssigneeUsernameAndTaskStatus(String username, TaskStatus taskStatus);
    List<Task> findBySprintId(Long sprintId);
}

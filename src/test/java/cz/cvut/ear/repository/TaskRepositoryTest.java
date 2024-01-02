package cz.cvut.ear.repository;

import cz.cvut.ear.Generator;
import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskRepositoryTest {
//    private final TaskRepository taskRepository;
//    private final SprintRepository sprintRepository;
//    private final ProjectRepository projectRepository;
//
//    @Autowired
//    public TaskRepositoryTest(TaskRepository taskRepository, SprintRepository sprintRepository, ProjectRepository projectRepository) {
//        this.taskRepository = taskRepository;
//        this.sprintRepository = sprintRepository;
//        this.projectRepository = projectRepository;
//    }
//
//
//    @Test
//    public void findByTaskNameReturnsTaskWithMatchingTaskName() {
//        final Task task = Generator.generateTask();
//        final Sprint sprint = task.getSprint();
//        final Project project = sprint.getProject();
//
//        projectRepository.saveAndFlush(project);
//        sprintRepository.saveAndFlush(sprint);
//        taskRepository.saveAndFlush(task);
//
//        String taskName = task.getTaskName();
//
//        assertEquals(task.getId(), taskRepository.findByTaskName(taskName).get().getId());
//    }
//
//    @Test
//    public void findByTaskNameReturnsOptionalEmpty() {
//        final Task task = Generator.generateTask();
//        String taskName = task.getTaskName();
//
//        assertEquals(Optional.empty(), taskRepository.findByTaskName(taskName));
//    }
}

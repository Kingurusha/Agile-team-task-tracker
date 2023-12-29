package cz.cvut.ear.service;

import cz.cvut.ear.Generator;
import cz.cvut.ear.repository.EmployeeRepository;
import cz.cvut.ear.repository.ProjectRepository;
import cz.cvut.ear.repository.SprintRepository;
import cz.cvut.ear.repository.TaskRepository;
import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.model.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Test
    void addTask() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        SprintRepository sprintRepository = Mockito.mock(SprintRepository.class);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

        TaskService taskService = new TaskService(taskRepository, sprintRepository, projectRepository, employeeRepository);

        Task task = Generator.generateTask();

        Sprint existingSprint = Mockito.mock(Sprint.class);
        Employee existingAssignee = Mockito.mock(Employee.class);

        existingSprint.setId(1L);

        existingAssignee.setId(2L);

        task.setSprint(existingSprint);
        task.setAssignee(existingAssignee);

        when(sprintRepository.findById(existingSprint.getId())).thenReturn(java.util.Optional.of(existingSprint));

        when(employeeRepository.findById(existingAssignee.getId())).thenReturn(java.util.Optional.of(existingAssignee));

        when(sprintRepository.save(existingSprint)).thenReturn(existingSprint);

        when(employeeRepository.save(existingAssignee)).thenReturn(existingAssignee);

        when(taskRepository.save(task)).thenReturn(task);

        Task resultTask = taskService.addTask(task);

        assertEquals(task, resultTask);

        ArgumentCaptor<Sprint> sprintCaptor = ArgumentCaptor.forClass(Sprint.class);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(sprintRepository, times(1)).save(sprintCaptor.capture());
        verify(employeeRepository, times(1)).save(employeeCaptor.capture());

        assertEquals(existingSprint, sprintCaptor.getValue());
        assertEquals(existingAssignee, employeeCaptor.getValue());
    }

    @Test
    void deleteTask() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        SprintRepository sprintRepository = Mockito.mock(SprintRepository.class);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

        TaskService taskService = new TaskService(taskRepository, sprintRepository, projectRepository, employeeRepository);

        Task task = Generator.generateTask();

        task.setId(1L);

        task.setTaskStatus(TaskStatus.CLOSED);

        Sprint existingSprint = Mockito.mock(Sprint.class);

        existingSprint.setId(2L);

        task.setSprint(existingSprint);

        when(taskRepository.findById(task.getId())).thenReturn(java.util.Optional.of(task));

        when(sprintRepository.findById(existingSprint.getId())).thenReturn(java.util.Optional.of(existingSprint));

        taskService.deleteTask(task.getId());

        verify(taskRepository, times(1)).deleteById(task.getId());

        verify(sprintRepository, times(1)).save(existingSprint);
    }

    @Test
    void setStoryPoints() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        SprintRepository sprintRepository = Mockito.mock(SprintRepository.class);
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

        TaskService taskService = new TaskService(taskRepository, sprintRepository, projectRepository, employeeRepository);

        Task task = Generator.generateTask();
        task.setId(1L);

        when(taskRepository.findById(task.getId())).thenReturn(java.util.Optional.of(task));

        int storyPoints = 10;
        Task resultTask = taskService.setStoryPoints(task.getId(), storyPoints);

        assertEquals(storyPoints, resultTask.getTaskPoints());
        verify(taskRepository, times(1)).save(resultTask);
    }
}

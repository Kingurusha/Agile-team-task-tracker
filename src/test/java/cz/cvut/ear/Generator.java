package cz.cvut.ear;

import cz.cvut.ear.model.*;
import cz.cvut.ear.model.enums.ProjectStatus;
import cz.cvut.ear.model.enums.SprintStatus;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Random;

public class Generator {
    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }


    public static EmpoweredEmployee generateEmpoweredEmployee() {
        final EmpoweredEmployee employee = new EmpoweredEmployee();

        employee.setName("Name" + randomInt());
        employee.setSurname("Surname" + randomInt());
        employee.setUsername("Username" + randomInt());
        employee.setEmail("Email" + randomInt());

        return employee;
    }

    public static RegularEmployee generateRegularEmployee() {
        final RegularEmployee employee = new RegularEmployee();

        employee.setName("Name" + randomInt());
        employee.setSurname("Surname" + randomInt());
        employee.setUsername("Username" + randomInt());
        employee.setEmail("Email" + randomInt());

        return employee;
    }

    public static Label generateLabel() {
        final Label label = new Label();

        label.setLabelName("Label" + randomInt());

        return label;
    }

    public static Project generateProject() {
        final Project project = new Project();

        project.setProjectName("Project_name" + randomInt());
        project.setProjectStatus(ProjectStatus.FUTURE);
        project.setDescription("Project_description" + randomInt());

        return project;
    }

    public static Sprint generateSprint() {
        final Sprint sprint = new Sprint();

        sprint.setSprintStatus(SprintStatus.FUTURE);
        sprint.setGoal("Goal" + randomInt());
        sprint.setProject(generateProject());
        sprint.setOrdinalNumberInProject(1);

        return sprint;
    }


    public static Task generateTask() {
        final Task task = new Task();

        task.setTaskName("Task_name" + randomInt());
        task.setTaskPoints(5);
        task.setTaskStatus(TaskStatus.TO_DO);
        task.setTaskPriority(TaskPriority.MEDIUM);
        task.setCreationDate(LocalDateTime.now());
        task.setLastUpdateDate(task.getCreationDate());
        task.setSprint(generateSprint());
        task.setDescription("Task_description" + randomInt());

        return task;
    }
}

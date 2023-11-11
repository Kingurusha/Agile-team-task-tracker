package cz.cvut.ear.model;

import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task extends AbstractEntity implements Serializable {
    @Column(nullable = false)
    private String taskName;

    private Integer taskPoints;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee assignee;

    @ManyToMany
    @JoinTable(
            name = "TASK_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private Set<Employee> participants;

    @ManyToOne
    @JoinColumn(name = "SPRINT_ID", nullable = false)
    private Sprint sprint;

    @ManyToMany
    @JoinTable(
            name = "TASK_LABEL",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID")
    )
    private Set<Label> labels;

    private String description;
}

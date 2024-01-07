package cz.cvut.ear.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.ear.model.enums.TaskPriority;
import cz.cvut.ear.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
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

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    @JsonIgnore
    private Employee assignee;

    @ManyToMany
    @JoinTable(
            name = "TASK_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    @JsonIgnore
    private Set<Employee> participants;

    @ManyToOne
    @JoinColumn(name = "SPRINT_ID", nullable = false)
    @JsonIgnore
    private Sprint sprint;

    @ManyToMany
    @OrderBy("labelName")
    @JoinTable(
            name = "TASK_LABEL",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "LABEL_ID")
    )
    @JsonIgnore
    private Set<Label> labels;

    private String description;
}

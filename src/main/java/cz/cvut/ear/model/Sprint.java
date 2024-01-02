package cz.cvut.ear.model;

import cz.cvut.ear.model.enums.SprintStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Sprint extends AbstractEntity implements Serializable {

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SprintStatus sprintStatus;

    private String goal;

    @ManyToOne(optional = false)
    @JoinColumn(name="PROJECT_ID", nullable = false)
    private Project project;

    @Column(nullable = false)
    private Integer ordinalNumberInProject;

    @OneToMany(mappedBy = "sprint")
    private Set<Task> tasksInSprint;
}

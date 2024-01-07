package cz.cvut.ear.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Project project;

    @Column(nullable = false)
    private Integer ordinalNumberInProject;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.PERSIST)
    @OrderBy("taskPriority DESC")
    @JsonIgnore
    private Set<Task> tasksInSprint;
    }

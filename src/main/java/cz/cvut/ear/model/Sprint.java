package cz.cvut.ear.model;

import cz.cvut.ear.model.enums.SprintStatus;
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
public class Sprint extends AbstractEntity implements Serializable {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SprintStatus sprintStatus;

    private String goal;

    @ManyToOne(optional = false)
    @JoinColumn(name="PROJECT_ID", nullable = false)
    private Project project;

    private Integer ordinalNumberInProject;

    @OneToMany(mappedBy = "sprint")
    private Set<Task> tasksInSprint;
}

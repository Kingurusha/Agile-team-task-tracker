package cz.cvut.ear.model;

import cz.cvut.ear.model.enums.ProjectStatus;
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
public class Project extends AbstractEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private String projectName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus projectStatus;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @OneToOne
    @JoinColumn(name="CURRENT_SPRINT_ID")
    private Sprint currentSprint;

    @OneToMany(mappedBy = "project")
    private Set<Sprint> sprintsInProject;

    @ManyToMany(mappedBy = "userProjects")
    private Set<Employee> usersInProject;

    private String description;
}

package cz.cvut.ear.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.ear.model.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Project extends AbstractEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private String projectName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus projectStatus;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name="CURRENT_SPRINT_ID")
    @JsonIgnore
    private Sprint currentSprint;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Sprint> sprintsInProject;

    @ManyToMany(mappedBy = "userProjects", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Employee> usersInProject;

    private String description;
}

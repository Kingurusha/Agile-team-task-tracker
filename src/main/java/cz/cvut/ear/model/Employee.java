package cz.cvut.ear.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "agile_user/employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EMPLOYEE_TYPE")
public abstract class Employee extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.PERSIST)
    @OrderBy("taskStatus DESC")
    private Set<Task> userTasks;

    @ManyToMany
    @JoinTable(
            name = "USER_PROJECT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")
    )
    private Set<Project> userProjects;
}

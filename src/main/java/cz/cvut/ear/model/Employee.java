package cz.cvut.ear.model;

import cz.cvut.ear.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "agile_user/employee")
@Data
public class Employee extends AbstractEntity implements Serializable {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "assignee")
    private Set<Task> userTasks;

    @ManyToMany
    @JoinTable(
            name = "USER_PROJECT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")
    )
    private Set<Project> userProjects;
}

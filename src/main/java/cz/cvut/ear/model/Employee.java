package cz.cvut.ear.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.cvut.ear.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NamedQuery(
        name = "Employee.findProjectsByIdAndStatus",
        query = "SELECT DISTINCT p FROM Project p " +
                "JOIN p.usersInProject u " +
                "WHERE u.id = :employeeId AND p.projectStatus = :projectStatus"
)
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

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.PERSIST)
    @OrderBy("taskStatus DESC")
    //@JsonBackReference
    //@JsonManagedReference
    @JsonIgnore
    private Set<Task> userTasks;

    @ManyToMany
    @JoinTable(
            name = "USER_PROJECT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID")
    )
    @JsonIgnore
    private Set<Project> userProjects;
}

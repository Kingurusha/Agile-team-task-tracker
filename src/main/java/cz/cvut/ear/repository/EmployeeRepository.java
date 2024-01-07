package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);


    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userProjects p WHERE p.id = :projectId")
    List<Employee> findEmployeesByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userTasks t WHERE t.dueDate < CURRENT_TIMESTAMP " +
            "AND t.taskStatus != 'CLOSED'")
    List<Employee> findEmployeesWithOverdueTasks();

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userProjects p JOIN p.sprintsInProject s " +
            "WHERE s.startDate BETWEEN :startDate AND :endDate")
    List<Employee> findEmployeesEnrolledInProjectsInDateRange(@Param("startDate") LocalDate startDate,
                                                              @Param("endDate") LocalDate endDate);

    boolean existsByName(String name);
}

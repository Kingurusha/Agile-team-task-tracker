package cz.cvut.ear.dao;

import cz.cvut.ear.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userProjects p WHERE p.id = :projectId")
    List<Employee> findEmployeesByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userTasks t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.taskStatus != 'CLOSED'")
    List<Employee> findEmployeesWithOverdueTasks();

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.userProjects p JOIN p.sprintsInProject s " +
            "WHERE s.startDateTime BETWEEN :startDate AND :endDate")
    List<Employee> findEmployeesInProjectsDuringDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}

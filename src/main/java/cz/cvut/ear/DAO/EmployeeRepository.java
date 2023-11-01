package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select b from Employee b where b.username = :name")
    Employee findByUsername(@Param("name") String name);
}

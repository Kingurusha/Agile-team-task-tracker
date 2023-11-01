package cz.cvut.ear.DAO;

import cz.cvut.ear.model.FullTimeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullTimeEmployeeRepository extends JpaRepository<FullTimeEmployee, Long> {

}

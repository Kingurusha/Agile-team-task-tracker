package cz.cvut.ear.DAO;

import cz.cvut.ear.model.PartTimeEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimeEmployeeRepository extends JpaRepository<PartTimeEmployee, Long> {

}

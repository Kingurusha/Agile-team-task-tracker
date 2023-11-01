package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select b from Task b where b.taskName = :name")
    Task findByName(@Param("name") String name);
}

package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select b from Project b where b.projectName = :name")
    Project findByName(@Param("name") String name);
}

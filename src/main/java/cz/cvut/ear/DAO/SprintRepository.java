package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

    @Query("select b from Sprint b where b.sprintName = :name")
    Sprint findByName(@Param("name") String name);
}

package cz.cvut.ear.DAO;

import cz.cvut.ear.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label, Long> {
    @Query("select b from Label b where b.labelName = :name")
    Label findByName(@Param("name") String name);
}
package cz.cvut.ear.dao;

import cz.cvut.ear.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByLabelName(String labelName);
}
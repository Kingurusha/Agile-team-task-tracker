package cz.cvut.ear.repository;

import cz.cvut.ear.model.Label;

import java.util.Optional;

public interface LabelRepository extends BaseRepository<Label, Long> {
    Optional<Label> findByLabelName(String labelName);
}
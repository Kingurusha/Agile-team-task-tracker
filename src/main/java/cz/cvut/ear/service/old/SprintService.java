package cz.cvut.ear.service;

import cz.cvut.ear.model.Sprint;

import java.util.List;

public interface SprintService {
    Sprint addSprint(Sprint sprint);
    void deleteSprint(long sprintId);
    Sprint getByName(String name);
    Sprint editSprint(Sprint sprint);
    List<Sprint> getAllSprints();
}

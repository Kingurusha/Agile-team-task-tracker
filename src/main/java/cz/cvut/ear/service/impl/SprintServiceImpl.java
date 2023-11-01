package cz.cvut.ear.service.impl;

import cz.cvut.ear.DAO.SprintRepository;
import cz.cvut.ear.model.Sprint;
import cz.cvut.ear.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public Sprint addSprint(Sprint sprint) {
        return sprintRepository.saveAndFlush(sprint);
    }

    @Override
    public void deleteSprint(long sprintId) {
        sprintRepository.deleteById(sprintId);
    }

    @Override
    public Sprint getByName(String name) {
        return sprintRepository.findByName(name);
    }

    @Override
    public Sprint editSprint(Sprint sprint) {
        return sprintRepository.saveAndFlush(sprint);
    }

    @Override
    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }
}
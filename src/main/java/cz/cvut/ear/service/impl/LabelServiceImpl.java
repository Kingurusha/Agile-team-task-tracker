package cz.cvut.ear.service.impl;

import cz.cvut.ear.DAO.LabelRepository;
import cz.cvut.ear.model.Label;
import cz.cvut.ear.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Label addLabel(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Override
    public void deleteLabel(long labelId) {
        labelRepository.deleteById(labelId);
    }

    @Override
    public Label getByName(String name) {
        return labelRepository.findByName(name);
    }

    @Override
    public Label editLabel(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }
}
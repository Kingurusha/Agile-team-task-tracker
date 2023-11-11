package cz.cvut.ear.service;

import cz.cvut.ear.dao.LabelRepository;
import cz.cvut.ear.model.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    // ----- OLD -------------

    public Label addLabel(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    public void deleteLabel(long labelId) {
        labelRepository.deleteById(labelId);
    }

    public Label getByName(String name) {
        return labelRepository.findByLabelName(name).get();
    }

    public Label editLabel(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }
}

package cz.cvut.ear.service;

import cz.cvut.ear.repository.LabelRepository;
import cz.cvut.ear.model.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// done
@Service
public class LabelService {
    private final LabelRepository labelRepository;
    private static final Logger LOG = LoggerFactory.getLogger(LabelService.class);


    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }


    @Transactional(readOnly = true)
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Label getLabelById(Long labelId) {
        return labelRepository.findById(labelId).get();
    }

    @Transactional(readOnly = true)
    public Label getLabelByName(String labelName) {
        return labelRepository.findByLabelName(labelName).get();
    }

    @Transactional
    public void createLabel(Label label) {
        labelRepository.saveAndFlush(label);
        LOG.debug("Created label {}.", label);
    }

    @Transactional
    public void updateLabel(Label label) {
        labelRepository.saveAndFlush(label);
        LOG.debug("Updated label {}.", label);
    }

    @Transactional
    public void deleteLabel(Long labelId) {
        Label label = labelRepository.findById(labelId).get();
        labelRepository.deleteById(labelId);
        LOG.debug("Deleted label {}.", label);
    }
}

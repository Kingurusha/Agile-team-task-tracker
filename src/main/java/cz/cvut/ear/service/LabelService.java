package cz.cvut.ear.service;

import cz.cvut.ear.repository.LabelRepository;
import cz.cvut.ear.helper.validator.LabelValidator;
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
    private final LabelValidator labelValidator;
    private static final Logger LOG = LoggerFactory.getLogger(LabelService.class);


    @Autowired
    public LabelService(LabelRepository labelRepository, LabelValidator labelValidator) {
        this.labelRepository = labelRepository;
        this.labelValidator = labelValidator;
    }


    @Transactional(readOnly = true)
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Label getLabelById(Long labelId) {
        labelValidator.validateLabel(labelId);
        return labelRepository.findById(labelId).get();
    }

    @Transactional(readOnly = true)
    public Label getLabelByName(String labelName) {
        labelValidator.validateLabelName(labelName);
        return labelRepository.findByLabelName(labelName).get();
    }

    @Transactional
    public void createLabel(Label label) {
        labelValidator.validateLabelCreation(label);
        labelRepository.saveAndFlush(label);
        LOG.debug("Created label {}.", label);
    }

    @Transactional
    public void updateLabel(Label label) {
        labelValidator.validateLabel(label.getId());
        labelRepository.saveAndFlush(label);
        LOG.debug("Updated label {}.", label);
    }

    @Transactional
    public void deleteLabel(Long labelId) {
        //labelValidator.validateLabelDeletion(labelId);
        //Label label = labelRepository.findById(labelId).get();
        labelRepository.deleteById(labelId);
        //LOG.debug("Deleted label {}.", label);
    }
}

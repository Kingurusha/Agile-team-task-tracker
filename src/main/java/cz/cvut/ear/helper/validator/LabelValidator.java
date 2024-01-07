package cz.cvut.ear.helper.validator;

import cz.cvut.ear.model.Label;
import cz.cvut.ear.model.Task;
import cz.cvut.ear.repository.LabelRepository;
import cz.cvut.ear.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LabelValidator {
    private final LabelRepository labelRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public LabelValidator(LabelRepository labelRepository, TaskRepository taskRepository) {
        this.labelRepository = labelRepository;
        this.taskRepository = taskRepository;
    }

    public void validateLabelName(String labelName) {
        if (labelName == null || labelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Label name cannot be null or empty.");
        }
    }
    public void validateLabel(Long labelId) {
        if (labelId == null) {
            throw new IllegalArgumentException("Label id cannot be null.");
        }

        if (!labelRepository.existsById(labelId)) {
            throw new IllegalArgumentException("Label with id " + labelId + " does not exist.");
        }
    }

    public void validateLabelCreation(Label label) {
        if (labelRepository.existsByLabelName(label.getLabelName())) {
            throw new IllegalArgumentException("Label with name " + label.getLabelName() + " already exists.");
        }
    }

    public void validateLabelDeletion(Long labelId) {
        Label label = labelRepository.findById(labelId).orElse(null);
        if (label == null) {
            throw new IllegalArgumentException("Label with ID " + labelId + " does not exist.");
        }

        Task taskWithLabel = taskRepository.findByLabelsId(labelId);
        if (taskWithLabel != null) {
            throw new IllegalStateException("Cannot delete label with ID " + labelId + " because it is associated with tasks.");
        }
    }
}

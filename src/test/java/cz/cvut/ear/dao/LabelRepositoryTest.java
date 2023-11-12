package cz.cvut.ear.dao;

import cz.cvut.ear.Generator;
import cz.cvut.ear.model.Label;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class LabelRepositoryTest {
    private final LabelRepository labelRepository;

    @Autowired
    public LabelRepositoryTest(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }


    @Test
    public void findByLabelNameReturnsLabelWithMatchingLabelName() {
        final Label label = Generator.generateLabel();
        String labelName = label.getLabelName();
        labelRepository.saveAndFlush(label);

        assertEquals(label.getId(), labelRepository.findByLabelName(labelName).get().getId());
    }

    @Test
    public void findByLabelNameReturnsOptionalEmptyForUnknownLabelName() {
        final Label label = Generator.generateLabel();
        String labelName = label.getLabelName();

        assertEquals(Optional.empty(), labelRepository.findByLabelName(labelName));
    }
}

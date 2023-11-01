package cz.cvut.ear.service;

import cz.cvut.ear.model.Label;

import java.util.List;

public interface LabelService {
    Label addLabel(Label label);
    void deleteLabel(long labelId);
    Label getByName(String name);
    Label editLabel(Label label);
    List<Label> getAllLabels();
}
package cz.cvut.ear.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
public class Label extends AbstractEntity implements Serializable {
    @Column(unique = true, nullable = false)
    private String labelName;
}

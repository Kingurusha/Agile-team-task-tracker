package cz.cvut.ear.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("Regular")
public class RegularEmployee extends Employee implements Serializable {
}

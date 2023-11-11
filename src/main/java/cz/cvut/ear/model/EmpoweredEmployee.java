package cz.cvut.ear.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("Empowered")
public class EmpoweredEmployee extends Employee implements Serializable {
}

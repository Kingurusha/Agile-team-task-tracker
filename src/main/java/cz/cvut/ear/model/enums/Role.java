package cz.cvut.ear.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    EMPOWERED("EMPOWERED_EMPLOYEE"), REGULAR("REGULAR_EMPLOYEE");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}

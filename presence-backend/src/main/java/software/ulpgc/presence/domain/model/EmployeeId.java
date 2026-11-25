package software.ulpgc.presence.domain.model;

import static software.ulpgc.presence.domain.model.RequiredValues.requireText;

public record EmployeeId(String value) {
    public EmployeeId {
        value = requireText(value, "employee id");
    }

    @Override
    public String toString() {
        return value;
    }
}

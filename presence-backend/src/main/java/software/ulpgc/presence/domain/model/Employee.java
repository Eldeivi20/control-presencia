package software.ulpgc.presence.domain.model;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;
import static software.ulpgc.presence.domain.model.RequiredValues.requireText;

public record Employee(EmployeeId id, String displayName, String workCenter) {
    public Employee {
        id = requirePresent(id, "employee id");
        displayName = requireText(displayName, "employee display name");
        workCenter = requireText(workCenter, "employee work center");
    }
}

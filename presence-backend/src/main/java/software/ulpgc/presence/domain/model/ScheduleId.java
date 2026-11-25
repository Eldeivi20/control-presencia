package software.ulpgc.presence.domain.model;

import static software.ulpgc.presence.domain.model.RequiredValues.requireText;

public record ScheduleId(String value) {
    public ScheduleId {
        value = requireText(value, "schedule id");
    }

    @Override
    public String toString() {
        return value;
    }
}

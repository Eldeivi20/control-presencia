package software.ulpgc.presence.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record ClockEvent(UUID id, EmployeeId employeeId, LocalDateTime occurredAt, ClockEventType type, ClockEventOrigin origin) {
    public ClockEvent {
        id = requirePresent(id, "clock event id");
        employeeId = requirePresent(employeeId, "clock event employee id");
        occurredAt = requirePresent(occurredAt, "clock event date and time");
        type = requirePresent(type, "clock event type");
        origin = requirePresent(origin, "clock event origin");
    }

    public static ClockEvent clockIn(EmployeeId employeeId, LocalDateTime occurredAt, ClockEventOrigin origin) {
        return new ClockEvent(UUID.randomUUID(), employeeId, occurredAt, ClockEventType.CLOCK_IN, origin);
    }

    public static ClockEvent clockOut(EmployeeId employeeId, LocalDateTime occurredAt, ClockEventOrigin origin) {
        return new ClockEvent(UUID.randomUUID(), employeeId, occurredAt, ClockEventType.CLOCK_OUT, origin);
    }

    public boolean isClockIn() {
        return type == ClockEventType.CLOCK_IN;
    }

    public boolean isClockOut() {
        return type == ClockEventType.CLOCK_OUT;
    }
}

package software.ulpgc.presence.domain.model;

import java.time.LocalDate;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record ScheduleAssignment(EmployeeId employeeId, ScheduleId scheduleId, DateRange period) {
    public ScheduleAssignment {
        employeeId = requirePresent(employeeId, "assigned employee id");
        scheduleId = requirePresent(scheduleId, "assigned schedule id");
        period = requirePresent(period, "assignment period");
    }

    public boolean isActiveOn(LocalDate date) {
        return period.contains(date);
    }

    public boolean overlaps(ScheduleAssignment other) {
        return employeeId.equals(other.employeeId) && period.overlaps(other.period);
    }
}

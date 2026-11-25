package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.MissingRequiredValueException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScheduleAssignmentTest {
    private static final EmployeeId OFFICE_WORKER = new EmployeeId("EMP-001");
    private static final EmployeeId WAITER = new EmployeeId("EMP-002");
    private static final ScheduleId OFFICE_SCHEDULE = new ScheduleId("office-continuous");

    private static ScheduleAssignment assignment(EmployeeId employeeId, int firstDay, int lastDay) {
        DateRange period = new DateRange(LocalDate.of(2026, 11, firstDay), LocalDate.of(2026, 11, lastDay));
        return new ScheduleAssignment(employeeId, OFFICE_SCHEDULE, period);
    }

    @Test
    void assignmentIsActiveOnlyInsideItsPeriod() {
        ScheduleAssignment assignment = assignment(OFFICE_WORKER, 1, 15);
        assertTrue(assignment.isActiveOn(LocalDate.of(2026, 11, 15)));
        assertFalse(assignment.isActiveOn(LocalDate.of(2026, 11, 16)));
    }

    @Test
    void assignmentsOfTheSameEmployeeWithSharedDaysOverlap() {
        assertTrue(assignment(OFFICE_WORKER, 1, 15).overlaps(assignment(OFFICE_WORKER, 15, 30)));
    }

    @Test
    void consecutiveAssignmentsDoNotOverlap() {
        assertFalse(assignment(OFFICE_WORKER, 1, 15).overlaps(assignment(OFFICE_WORKER, 16, 30)));
    }

    @Test
    void assignmentsOfDifferentEmployeesNeverOverlap() {
        assertFalse(assignment(OFFICE_WORKER, 1, 30).overlaps(assignment(WAITER, 1, 30)));
    }

    @Test
    void assignmentWithoutPeriodIsRejected() {
        assertThrows(MissingRequiredValueException.class,
                () -> new ScheduleAssignment(OFFICE_WORKER, OFFICE_SCHEDULE, null));
    }
}

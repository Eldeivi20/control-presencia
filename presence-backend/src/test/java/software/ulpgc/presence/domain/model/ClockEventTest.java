package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.MissingRequiredValueException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClockEventTest {
    private static final EmployeeId NURSE = new EmployeeId("EMP-003");
    private static final LocalDateTime SHIFT_START = LocalDateTime.of(2026, 11, 23, 7, 58);

    @Test
    void clockInKeepsEmployeeDateTimeTypeAndOrigin() {
        ClockEvent event = ClockEvent.clockIn(NURSE, SHIFT_START, ClockEventOrigin.ATTENDANCE_APP);
        assertEquals(NURSE, event.employeeId());
        assertEquals(SHIFT_START, event.occurredAt());
        assertEquals(ClockEventOrigin.ATTENDANCE_APP, event.origin());
        assertTrue(event.isClockIn());
    }

    @Test
    void clockOutIsRecognisedAsSuch() {
        assertTrue(ClockEvent.clockOut(NURSE, SHIFT_START.plusHours(8), ClockEventOrigin.HTTP_API).isClockOut());
    }

    @Test
    void everyEventReceivesItsOwnIdentifier() {
        ClockEvent first = ClockEvent.clockIn(NURSE, SHIFT_START, ClockEventOrigin.ATTENDANCE_APP);
        ClockEvent second = ClockEvent.clockIn(NURSE, SHIFT_START, ClockEventOrigin.ATTENDANCE_APP);
        assertNotEquals(first.id(), second.id());
    }

    @Test
    void eventWithoutOriginIsRejected() {
        assertThrows(MissingRequiredValueException.class, () -> ClockEvent.clockIn(NURSE, SHIFT_START, null));
    }
}

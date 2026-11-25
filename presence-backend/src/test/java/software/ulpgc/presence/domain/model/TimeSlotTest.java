package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.InvalidTimeSlotException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeSlotTest {
    private static final LocalDate MONDAY = LocalDate.of(2026, 11, 23);

    @Test
    void officeSlotLastsSevenHoursWithinTheSameDay() {
        TimeSlot office = TimeSlot.between(LocalTime.of(8, 0), LocalTime.of(15, 0));
        assertEquals(Duration.ofHours(7), office.length());
        assertFalse(office.crossesMidnight());
    }

    @Test
    void slotEndingExactlyAtMidnightDoesNotCrossIt() {
        assertFalse(TimeSlot.between(LocalTime.of(16, 0), LocalTime.MIDNIGHT).crossesMidnight());
    }

    @Test
    void nightShiftEndsOnTheFollowingDay() {
        TimeSlot night = TimeSlot.between(LocalTime.of(22, 0), LocalTime.of(6, 0));
        assertEquals(Duration.ofHours(8), night.length());
        assertTrue(night.crossesMidnight());
        assertEquals(LocalDateTime.of(2026, 11, 24, 6, 0), night.on(MONDAY).end());
    }

    @Test
    void identicalStartAndEndMeansAFullDayGuard() {
        TimeSlot guard = TimeSlot.between(LocalTime.of(8, 0), LocalTime.of(8, 0));
        assertEquals(Duration.ofHours(24), guard.length());
        assertTrue(guard.crossesMidnight());
    }

    @Test
    void slotLongerThanADayIsRejected() {
        assertThrows(InvalidTimeSlotException.class,
                () -> TimeSlot.startingAt(LocalTime.of(8, 0), Duration.ofHours(25)));
    }

    @Test
    void zeroLengthSlotIsRejected() {
        assertThrows(InvalidTimeSlotException.class,
                () -> TimeSlot.startingAt(LocalTime.of(8, 0), Duration.ZERO));
    }
}

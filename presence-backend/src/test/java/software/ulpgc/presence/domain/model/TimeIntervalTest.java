package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.InvalidTimeIntervalException;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeIntervalTest {
    private static TimeInterval interval(int startHour, int endHour) {
        return new TimeInterval(LocalDateTime.of(2026, 11, 23, startHour, 0), LocalDateTime.of(2026, 11, 23, endHour, 0));
    }

    @Test
    void overlapIsTheSharedPartOfBothIntervals() {
        assertEquals(Duration.ofHours(3), interval(8, 15).overlapWith(interval(12, 18)));
    }

    @Test
    void disjointIntervalsDoNotOverlap() {
        assertEquals(Duration.ZERO, interval(10, 14).overlapWith(interval(17, 21)));
    }

    @Test
    void startInstantIsContainedButEndInstantIsNot() {
        assertTrue(interval(8, 15).contains(LocalDateTime.of(2026, 11, 23, 8, 0)));
        assertFalse(interval(8, 15).contains(LocalDateTime.of(2026, 11, 23, 15, 0)));
    }

    @Test
    void intervalEndingBeforeItStartsIsRejected() {
        assertThrows(InvalidTimeIntervalException.class, () -> interval(15, 8));
    }
}

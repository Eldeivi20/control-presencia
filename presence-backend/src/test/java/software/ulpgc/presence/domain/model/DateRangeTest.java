package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.InvalidDateRangeException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateRangeTest {
    private final DateRange november = new DateRange(LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30));

    @Test
    void bothLimitsAreIncluded() {
        assertTrue(november.contains(LocalDate.of(2026, 11, 1)));
        assertTrue(november.contains(LocalDate.of(2026, 11, 30)));
        assertFalse(november.contains(LocalDate.of(2026, 12, 1)));
    }

    @Test
    void daysListsEveryDateOfTheRange() {
        assertEquals(30, november.days().count());
    }

    @Test
    void singleDayRangeContainsOnlyThatDay() {
        assertEquals(1, DateRange.singleDay(LocalDate.of(2026, 11, 25)).days().count());
    }

    @Test
    void rangeEndingBeforeItStartsIsRejected() {
        assertThrows(InvalidDateRangeException.class,
                () -> new DateRange(LocalDate.of(2026, 11, 30), LocalDate.of(2026, 11, 1)));
    }
}

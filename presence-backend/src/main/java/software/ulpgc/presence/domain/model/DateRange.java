package software.ulpgc.presence.domain.model;

import software.ulpgc.presence.domain.exceptions.InvalidDateRangeException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record DateRange(LocalDate start, LocalDate end) {
    public DateRange {
        start = requirePresent(start, "date range start");
        end = requirePresent(end, "date range end");
        if (end.isBefore(start)) throw new InvalidDateRangeException(start, end);
    }

    public static DateRange singleDay(LocalDate date) {
        return new DateRange(date, date);
    }

    public boolean contains(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    public Stream<LocalDate> days() {
        return start.datesUntil(end.plusDays(1));
    }
}

package software.ulpgc.presence.domain.model;

import software.ulpgc.presence.domain.exceptions.InvalidTimeSlotException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record TimeSlot(LocalTime start, Duration length) {
    private static final Duration FULL_DAY = Duration.ofHours(24);

    public TimeSlot {
        start = requirePresent(start, "time slot start");
        length = requirePresent(length, "time slot length");
        if (!length.isPositive() || length.compareTo(FULL_DAY) > 0)
            throw new InvalidTimeSlotException(length);
    }

    public static TimeSlot between(LocalTime start, LocalTime end) {
        requirePresent(start, "time slot start");
        requirePresent(end, "time slot end");
        return new TimeSlot(start, lengthBetween(start, end));
    }

    public static TimeSlot startingAt(LocalTime start, Duration length) {
        return new TimeSlot(start, length);
    }

    private static Duration lengthBetween(LocalTime start, LocalTime end) {
        Duration sameDayLength = Duration.between(start, end);
        return sameDayLength.isPositive() ? sameDayLength : sameDayLength.plus(FULL_DAY);
    }

    public LocalTime end() {
        return start.plus(length);
    }

    public boolean crossesMidnight() {
        return length.compareTo(timeUntilMidnight()) > 0;
    }

    private Duration timeUntilMidnight() {
        return Duration.between(start, LocalTime.MAX).plusNanos(1);
    }

    public TimeInterval on(LocalDate date) {
        return new TimeInterval(date.atTime(start), date.atTime(start).plus(length));
    }
}

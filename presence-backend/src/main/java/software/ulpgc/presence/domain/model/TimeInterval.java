package software.ulpgc.presence.domain.model;

import software.ulpgc.presence.domain.exceptions.InvalidTimeIntervalException;

import java.time.Duration;
import java.time.LocalDateTime;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record TimeInterval(LocalDateTime start, LocalDateTime end) {
    public TimeInterval {
        start = requirePresent(start, "time interval start");
        end = requirePresent(end, "time interval end");
        if (!start.isBefore(end)) throw new InvalidTimeIntervalException(start, end);
    }

    public Duration duration() {
        return Duration.between(start, end);
    }

    public boolean contains(LocalDateTime instant) {
        return !instant.isBefore(start) && instant.isBefore(end);
    }

    public Duration overlapWith(TimeInterval other) {
        LocalDateTime latestStart = start.isAfter(other.start) ? start : other.start;
        LocalDateTime earliestEnd = end.isBefore(other.end) ? end : other.end;
        return latestStart.isBefore(earliestEnd) ? Duration.between(latestStart, earliestEnd) : Duration.ZERO;
    }
}

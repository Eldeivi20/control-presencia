package software.ulpgc.presence.domain.exceptions;

import java.time.LocalDateTime;

public class InvalidTimeIntervalException extends DomainException {
    public InvalidTimeIntervalException(LocalDateTime start, LocalDateTime end) {
        super("Time interval must start before it ends: " + start + " - " + end);
    }
}

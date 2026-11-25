package software.ulpgc.presence.domain.exceptions;

import java.time.Duration;

public class InvalidTimeSlotException extends DomainException {
    public InvalidTimeSlotException(Duration length) {
        super("Time slot length must be greater than zero and at most 24 hours, but was " + length);
    }
}

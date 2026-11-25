package software.ulpgc.presence.domain.exceptions;

import java.time.Duration;

public class NegativeDeviationException extends DomainException {
    public NegativeDeviationException(Duration deviation) {
        super("Incident deviation cannot be negative, but was " + deviation);
    }
}

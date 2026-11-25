package software.ulpgc.presence.domain.exceptions;

import java.time.LocalDate;

public class InvalidDateRangeException extends DomainException {
    public InvalidDateRangeException(LocalDate start, LocalDate end) {
        super("Date range must not end before it starts: " + start + " - " + end);
    }
}

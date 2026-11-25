package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.NegativeDeviationException;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncidentTest {
    private static final EmployeeId WAITER = new EmployeeId("EMP-002");
    private static final LocalDate MONDAY = LocalDate.of(2026, 11, 23);

    @Test
    void lateArrivalRecordsHowLateTheEmployeeWas() {
        Incident incident = new Incident(WAITER, MONDAY, IncidentType.LATE_ARRIVAL, Duration.ofMinutes(12));
        assertEquals(Duration.ofMinutes(12), incident.deviation());
    }

    @Test
    void absenceHasNoDeviation() {
        assertEquals(Duration.ZERO, Incident.withoutDeviation(WAITER, MONDAY, IncidentType.ABSENCE).deviation());
    }

    @Test
    void negativeDeviationIsRejected() {
        assertThrows(NegativeDeviationException.class,
                () -> new Incident(WAITER, MONDAY, IncidentType.EARLY_DEPARTURE, Duration.ofMinutes(-5)));
    }
}

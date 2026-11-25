package software.ulpgc.presence.domain.model;

import software.ulpgc.presence.domain.exceptions.NegativeDeviationException;

import java.time.Duration;
import java.time.LocalDate;

import static software.ulpgc.presence.domain.model.RequiredValues.requirePresent;

public record Incident(EmployeeId employeeId, LocalDate date, IncidentType type, Duration deviation) {
    public Incident {
        employeeId = requirePresent(employeeId, "incident employee id");
        date = requirePresent(date, "incident date");
        type = requirePresent(type, "incident type");
        deviation = requirePresent(deviation, "incident deviation");
        if (deviation.isNegative()) throw new NegativeDeviationException(deviation);
    }

    public static Incident withoutDeviation(EmployeeId employeeId, LocalDate date, IncidentType type) {
        return new Incident(employeeId, date, type, Duration.ZERO);
    }
}

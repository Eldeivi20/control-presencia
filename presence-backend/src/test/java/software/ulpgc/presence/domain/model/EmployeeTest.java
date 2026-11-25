package software.ulpgc.presence.domain.model;

import org.junit.jupiter.api.Test;
import software.ulpgc.presence.domain.exceptions.MissingRequiredValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeTest {
    @Test
    void employeeIdIsStrippedOfSurroundingBlanks() {
        assertEquals("EMP-001", new EmployeeId("  EMP-001 ").value());
    }

    @Test
    void blankEmployeeIdIsRejected() {
        assertThrows(MissingRequiredValueException.class, () -> new EmployeeId("   "));
    }

    @Test
    void employeeWithoutWorkCenterIsRejected() {
        assertThrows(MissingRequiredValueException.class,
                () -> new Employee(new EmployeeId("EMP-001"), "Office Worker A", null));
    }

    @Test
    void employeeKeepsItsIdentityAndWorkCenter() {
        Employee employee = new Employee(new EmployeeId("EMP-001"), "Office Worker A", "Head Office");
        assertEquals(new EmployeeId("EMP-001"), employee.id());
        assertEquals("Head Office", employee.workCenter());
    }
}

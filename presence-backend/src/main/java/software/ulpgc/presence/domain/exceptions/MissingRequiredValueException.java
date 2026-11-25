package software.ulpgc.presence.domain.exceptions;

public class MissingRequiredValueException extends DomainException {
    public MissingRequiredValueException(String valueName) {
        super("Missing required value: " + valueName);
    }
}

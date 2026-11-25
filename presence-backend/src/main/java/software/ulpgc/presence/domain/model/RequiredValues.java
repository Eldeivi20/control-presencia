package software.ulpgc.presence.domain.model;

import software.ulpgc.presence.domain.exceptions.MissingRequiredValueException;

public final class RequiredValues {
    private RequiredValues() {
    }

    public static <T> T requirePresent(T value, String valueName) {
        if (value == null) throw new MissingRequiredValueException(valueName);
        return value;
    }

    public static String requireText(String value, String valueName) {
        if (value == null || value.isBlank()) throw new MissingRequiredValueException(valueName);
        return value.strip();
    }
}

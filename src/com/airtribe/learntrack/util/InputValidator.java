package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() {
    }

    public static String validateNonBlank(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public static String validateEmail(String email) throws InvalidInputException {
        String sanitizedEmail = validateNonBlank(email, "Email");
        if (!sanitizedEmail.contains("@") || sanitizedEmail.startsWith("@") || sanitizedEmail.endsWith("@")) {
            throw new InvalidInputException("Please provide a valid email address.");
        }
        return sanitizedEmail;
    }

    public static int validateDurationInWeeks(int durationInWeeks) throws InvalidInputException {
        if (durationInWeeks <= 0) {
            throw new InvalidInputException("Course duration must be greater than 0.");
        }
        return durationInWeeks;
    }

    public static String generateDefaultEmail(String firstName, String lastName) {
        String safeFirst = firstName == null ? "" : firstName.trim().toLowerCase();
        String safeLast = lastName == null ? "" : lastName.trim().toLowerCase();
        String compactFirst = safeFirst.replaceAll("\\s+", "");
        String compactLast = safeLast.replaceAll("\\s+", "");
        if (compactFirst.isEmpty() && compactLast.isEmpty()) {
            return "student@learntrack.local";
        }
        return compactFirst + "." + compactLast + "@learntrack.local";
    }
}

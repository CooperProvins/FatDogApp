package com.example.testapplication;

/**
 * Enum that defines any dog's sex
 */
public enum Sex {
    MALE,
    FEMALE;

    /**
     * Parses a string into a Sex enum value
     * Accepts: "male", "boy", "man", "female", "girl", "woman"
     * @param input the string to parse
     * @return the corresponding Sex enum value
     */
    public static Sex parse(String input) {
        assert input != null : "Sex parse input can not be null";

        String normalized = input.toUpperCase().trim();

        switch (normalized) {
            case "MALE":
            case "BOY":
            case "MAN":
                return MALE;
            case "FEMALE":
            case "GIRL":
            case "WOMAN":
                return FEMALE;
            default:
                return null;
        }
    }
}

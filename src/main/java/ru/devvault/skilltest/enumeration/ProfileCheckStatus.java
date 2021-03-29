package ru.devvault.skilltest.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProfileCheckStatus {

    VALID("valid", "Profile passed verification"),
    INVALID("invalid", "Profile failed verification");

    private final String key;
    private final String statusName;
}

package ru.devvault.skilltest.dto;

import java.util.Collections;
import java.util.List;

public class EmailAddress {
    private List<String> addresses;

    public EmailAddress() {
        this.addresses = Collections.emptyList();
    }

    public EmailAddress(List<String> addresses) {
        this.addresses = addresses;
    }

    public EmailAddress(String address) {
        this();
        add(address);
    }

    List<String> get() {
        return addresses;
    }

    void set(List<String> addresses) {
        this.addresses = addresses;
    }

    void add(String address) {
        this.addresses.add(address);
    }
}

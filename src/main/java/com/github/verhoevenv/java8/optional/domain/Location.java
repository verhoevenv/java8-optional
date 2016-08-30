package com.github.verhoevenv.java8.optional.domain;

public class Location {
    private ZipCode zipCode;

    public Location(ZipCode zipCode) {
        this.zipCode = zipCode;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }
}

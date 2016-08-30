package com.github.verhoevenv.java8.optional;

import com.github.verhoevenv.java8.optional.domain.Location;
import com.github.verhoevenv.java8.optional.domain.Office;
import com.github.verhoevenv.java8.optional.domain.OfficeRepository;

import java.util.Optional;

public class OfficeByLocationRetrievalJavaMap implements OfficeByLocationRetrieval {
    private OfficeRepository officeRepository;

    public OfficeByLocationRetrievalJavaMap(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office retrieveOfficeForLocation(Location location) {
        return implementationWithMap(location);
    }

    public Office implementationWithMap(Location location) {
        return Optional.ofNullable(location)
                .map(loc -> officeRepository.findFor(loc.getZipCode()))
                .orElseGet(() -> retrieveDefaultOffice());
    }

    private Office retrieveDefaultOffice() {
        return officeRepository.findWithLowestId();
    }

}

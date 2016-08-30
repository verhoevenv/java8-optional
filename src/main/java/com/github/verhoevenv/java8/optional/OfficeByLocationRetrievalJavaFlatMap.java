package com.github.verhoevenv.java8.optional;

import com.github.verhoevenv.java8.optional.domain.Location;
import com.github.verhoevenv.java8.optional.domain.Office;
import com.github.verhoevenv.java8.optional.domain.OfficeRepository;

import java.util.Optional;

public class OfficeByLocationRetrievalJavaFlatMap implements OfficeByLocationRetrieval {
    private OfficeRepository officeRepository;

    public OfficeByLocationRetrievalJavaFlatMap(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office retrieveOfficeForLocation(Location location) {
        return implementationWithFlatMap(location);
    }

    public Office implementationWithFlatMap(Location location) {
        return Optional.ofNullable(location)
                .flatMap(loc -> Optional.ofNullable(officeRepository.findFor(loc.getZipCode())))
                .orElseGet(() -> retrieveDefaultOffice());
    }

    private Office retrieveDefaultOffice() {
        return officeRepository.findWithLowestId();
    }

}

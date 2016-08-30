package com.github.verhoevenv.java8.optional;

import com.github.verhoevenv.java8.optional.domain.Location;
import com.github.verhoevenv.java8.optional.domain.Office;
import com.github.verhoevenv.java8.optional.domain.OfficeRepository;

import java.util.Optional;

public class OfficeByLocationRetrievalJavaOriginal implements OfficeByLocationRetrieval {
    private OfficeRepository officeRepository;

    public OfficeByLocationRetrievalJavaOriginal(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public Office retrieveOfficeForLocation(Location location) {
        return existingImplementation(location);
    }

    private Office existingImplementation(Location location) {
        if (location != null) {
            Office office = officeRepository.findFor(location.getZipCode());
            if(office != null) {
                return office;
            } else {
                return retrieveDefaultOffice();
            }
        } else {
            return retrieveDefaultOffice();
        }
    }

    private Office retrieveDefaultOffice() {
        return officeRepository.findWithLowestId();
    }

}

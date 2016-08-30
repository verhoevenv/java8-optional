package com.github.verhoevenv.java8.optional;

import com.github.verhoevenv.java8.optional.domain.Location;
import com.github.verhoevenv.java8.optional.domain.Office;

public interface OfficeByLocationRetrieval {
    Office retrieveOfficeForLocation(Location location);
}

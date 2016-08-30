package com.github.verhoevenv.java8.optional;

import com.github.verhoevenv.java8.optional.domain.Location;
import com.github.verhoevenv.java8.optional.domain.Office;
import com.github.verhoevenv.java8.optional.domain.OfficeRepository;
import com.github.verhoevenv.java8.optional.domain.ZipCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class OfficeByLocationRetrievalTest {

    private OfficeRepository officeRepositoryMock = mock(OfficeRepository.class);
    private Office defaultOffice = new Office();
    private Office anOffice = new Office();

    private ZipCode aZipCode = new ZipCode();
    private Location aLocation = new Location(aZipCode);

    private OfficeByLocationRetrieval sut;

    @Parameterized.Parameter
    public Impl implementationUnderTest;

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Impl> implementations() {
        return asList(Impl.values());
    }

    private enum Impl {
        JAVA_ORIG(OfficeByLocationRetrievalJavaOriginal::new, "Java original code"),
        JAVA_FLATMAP(OfficeByLocationRetrievalJavaFlatMap::new, "Java Optional with flatMap"),
        JAVA_MAP(OfficeByLocationRetrievalJavaMap::new, "Java Optional with map"),
        SCALA_FLATMAP(OfficeByLocationRetrievalScalaFlatMap::new, "Scala Option with flatMap"),
        SCALA_MAP(OfficeByLocationRetrievalScalaMap::new, "Scala Option with Map"),
        SCALA_MONADIC(OfficeByLocationRetrievalScalaMonadic::new, "Scala Option with monad comprehension");

        private final Function<OfficeRepository, OfficeByLocationRetrieval> factory;
        private final String description;

        Impl(Function<OfficeRepository, OfficeByLocationRetrieval> factory, String description) {
            this.factory = factory;
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }

        public Function<OfficeRepository, OfficeByLocationRetrieval> getFactory() {
            return factory;
        }
    }


    @Before
    public void setupSUT() {
        sut = implementationUnderTest.getFactory().apply(officeRepositoryMock);
    }

    @Before
    public void setupMocking() {
        when(officeRepositoryMock.findWithLowestId()).thenReturn(defaultOffice);
    }


    @Test
    public void givenLocationWithOffice_returnsThatOffice() throws Exception {
        when(officeRepositoryMock.findFor(aZipCode)).thenReturn(anOffice);

        Office result = sut.retrieveOfficeForLocation(aLocation);

        assertThat(result).isEqualTo(anOffice).isNotEqualTo(defaultOffice);
    }

    @Test
    public void givenNoLocation_returnsDefaultOffice() throws Exception {
        Office result = sut.retrieveOfficeForLocation(null);

        assertThat(result).isEqualTo(defaultOffice);
    }

    @Test
    public void givenLocationWithoutOffice_returnsDefaultOffice() throws Exception {
        when(officeRepositoryMock.findFor(aZipCode)).thenReturn(null);

        Office result = sut.retrieveOfficeForLocation(aLocation);

        assertThat(result).isEqualTo(defaultOffice);
    }

}
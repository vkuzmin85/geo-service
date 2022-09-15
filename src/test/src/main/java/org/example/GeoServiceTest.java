package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

class GeoServiceTest {

    @ParameterizedTest
    @MethodSource("ipSource")
    void byIpTest(String ip, Country expected) {
        GeoService geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(ip).getCountry();
        Assertions.assertEquals(expected, actual);
    }
    private static Stream<Arguments> ipSource() {
        return Stream.of(
                Arguments.of("96.4.25.63", Country.USA),
                Arguments.of("172.34.25.61", Country.RUSSIA),
                Arguments.of("96.44.183.149", Country.USA));
    }
}
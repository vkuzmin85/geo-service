package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("argSource")
    void localeTest(Country country, String expected) {
        LocalizationService locService = new LocalizationServiceImpl();
        String actual = locService.locale(country);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"));
    }
}

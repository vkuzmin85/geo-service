package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    GeoService geoService;

    @Mock
    LocalizationService locService;

    @Mock
    Location location;

    @ParameterizedTest
    @MethodSource("ipSource")
    void sendTest(String ip) {
        String expected;
        if (ip.startsWith("172.")) {
            expected = "Добро пожаловать";
        } else {
            expected = "Welcome";
        }
        when(geoService.byIp(ip)).thenReturn(location);
        when(locService.locale(any())).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, locService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String actual = messageSender.send(headers);
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> ipSource() {
        return Stream.of(
                Arguments.of("96.345.43.12"),
                Arguments.of("96.1.8.0"),
                Arguments.of("172.2.56.7"),
                Arguments.of("172.125.225.123"));
    }
}
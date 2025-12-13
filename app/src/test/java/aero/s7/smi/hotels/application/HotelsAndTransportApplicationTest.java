package aero.s7.smi.hotels.application;

import aero.s7.smi.hotels.api.dto.application.ApplicationDto;
import aero.s7.smi.hotels.api.dto.application.HotelAndTransportApplicationDto;
import aero.s7.smi.hotels.api.dto.application.passenger.ApplicationPassengerHotelDto;
import aero.s7.smi.hotels.api.dto.catalog.MealTypeDto;
import aero.s7.smi.hotels.api.request.application.ApplicationRequest;
import aero.s7.smi.hotels.api.request.application.HotelAndTransportApplicationRequest;
import aero.s7.smi.hotels.api.request.application.passenger.ApplicationPassengerHotelRequest;
import aero.s7.smi.psn.api.dto.FQT;
import aero.s7.smi.psn.api.dto.PassengerDto;
import aero.s7.smi.psn.api.starter.client.SbaPsnApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class HotelsAndTransportApplicationTest extends AbstractApplicationTest {
    @Autowired
    private SbaPsnApiClient sbaPsnApiClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected Stream<Arguments> getSaveRequestData() {
        return Stream.of(
                Arguments.of("/json/application/save-hotel-and-transport-without-parent.json")
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends ApplicationRequest> Class<T> getRequestClass() {
        return (Class<T>) HotelAndTransportApplicationRequest.class;
    }

    @BeforeEach
    @Sql({"/sql/application/clean-up.sql"})
    @SneakyThrows
    void setup() {
        final String contactInfo = "[{\"contact\":[\"79645789543\"]},{\"email\":[\"ramil.mukhtasarov@softline.com\"]}]";

        when(sbaPsnApiClient.findByUid(any()))
                .thenReturn(List.of(
                        PassengerDto.builder()
                                .uid(UUID.randomUUID().toString())
                                .shortleg("FLT=5228.DATE=20230519.DEP=IKT.CARRIER=S7")
                                .rowhash("3018.20221205.IKT.S7.-.LBCI11.4.ADT")
                                .flt("3018")
                                .fqt(List.of(FQT.builder()
                                        .airline("S7")
                                        .code("FQTV")
                                        .number("123471070")
                                        .status("GOLD")
                                        .build()))
                                .stdUtc(new Timestamp(1641346768000L))
                                .stdUtcDate(new Timestamp(1641346768000L))
                                .contactInfo(objectMapper.readTree(contactInfo))
                                .build()
                ));
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void saveApplicationTest(final String jsonPath) {
        super.saveApplicationTest(jsonPath);
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void getByIdApplicationAfterSavingTest(final String jsonPath) {
        super.getByIdApplicationAfterSavingTest(jsonPath);
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void getByIdApplicationAfterSentTest(final String jsonPath) {
        super.getByIdApplicationAfterSentTest(jsonPath);
    }

    @Override
    protected void performAdditionalAfterSavingAssertions(final ApplicationRequest request, final ApplicationDto response) {
        final HotelAndTransportApplicationRequest hotelRequest = (HotelAndTransportApplicationRequest) request;
        final HotelAndTransportApplicationDto hotelResponse = (HotelAndTransportApplicationDto) response;

        assertThat(hotelResponse.getCheckInDate(), is(hotelRequest.getCheckInDate()));
        assertThat(hotelResponse.getCheckOutDate(), is(hotelRequest.getCheckOutDate()));

        performMealTypeAssertions(hotelRequest.getMealTypes(), hotelResponse.getMealTypes());
        performPassengerAssertions(hotelRequest.getPassengers(), hotelResponse.getPassengers());
    }

    private void performMealTypeAssertions(final List<String> request, final List<MealTypeDto> response) {
        assertThat(request, hasSize(response.size()));

        final List<String> requestMealTypeCodes = request
                .stream()
                .filter(Objects::nonNull)
                .toList();
        final List<String> responseMealTypeCodes = response
                .stream()
                .filter(Objects::nonNull)
                .map(MealTypeDto::getCode)
                .filter(Objects::nonNull)
                .toList();

        assertThat(responseMealTypeCodes, containsInAnyOrder(requestMealTypeCodes.toArray()));
    }

    private void performPassengerAssertions(final List<ApplicationPassengerHotelRequest> request, final List<ApplicationPassengerHotelDto> response) {
        assertThat(request, hasSize(response.size()));

        response.forEach(passengerResponse -> {
            final Optional<ApplicationPassengerHotelRequest> passengerRequest = request
                    .stream()
                    .filter(passenger -> passenger.getUid().equals(passengerResponse.getUid()))
                    .findAny();

            passengerRequest.ifPresentOrElse(requestValue -> {
                if (requestValue.getPhoneNumber() != null) {
                    assertThat(passengerResponse.getPhoneNumber(), is(requestValue.getPhoneNumber()));
                }
                if (requestValue.getGroupId() != null) {
                    assertThat(passengerResponse.getGroupId(), notNullValue());
                }
                if (requestValue.getRoom() != null) {
                    if (requestValue.getRoom().getRoomCategoryId() != null) {
                        assertThat(passengerResponse.getRoom().getRoomCategory().getId(), is(requestValue.getRoom().getRoomCategoryId()));
                    }
                    assertThat(passengerResponse.getRoom().getCanShare(), is(requestValue.getRoom().getCanShare()));
                }
            }, () -> {
                throw new NullPointerException();
            });

        });
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void cancelApplicationTest(final String jsonPath) {
        super.cancelApplicationTest(jsonPath);
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void deleteApplicationTest(final String jsonPath) {
        super.deleteApplicationTest(jsonPath);
    }
}

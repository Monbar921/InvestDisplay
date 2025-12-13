package aero.s7.smi.hotels.application;

import aero.s7.smi.hotels.AbstractDatabaseHotelsTest;
import aero.s7.smi.hotels.api.dto.application.ApplicationDto;
import aero.s7.smi.hotels.api.dto.application.enums.Status;
import aero.s7.smi.hotels.api.dto.application.failed.situation.FailedSituationDto;
import aero.s7.smi.hotels.api.dto.catalog.ProviderDto;
import aero.s7.smi.hotels.api.request.application.ApplicationRequest;
import aero.s7.smi.hotels.api.request.application.CancelApplicationRequest;
import aero.s7.smi.hotels.starter.client.SbaHotelsClient;
import aero.s7.smi.psn.api.dto.FQT;
import aero.s7.smi.psn.api.dto.PassengerDto;
import aero.s7.smi.psn.api.starter.client.SbaPsnApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = {"/sql/init.sql", "/sql/application/init.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractApplicationTest extends AbstractDatabaseHotelsTest {
    @Autowired
    private SbaHotelsClient sbaHotelsClient;
    @MockitoBean
    private SbaPsnApiClient sbaPsnApiClient;
    @Autowired
    private ObjectMapper objectMapper;

    protected abstract Stream<Arguments> getSaveRequestData();

    protected abstract <T extends ApplicationRequest> Class<T> getRequestClass();

    protected abstract void performAdditionalAfterSavingAssertions(ApplicationRequest request, ApplicationDto response);

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
        final Long applicationId = saveApplication(jsonPath);
        assertThat(applicationId, notNullValue());
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void getByIdApplicationAfterSavingTest(final String jsonPath) {
        final ApplicationRequest request = getRequest(jsonPath);

        final Long applicationId = sbaHotelsClient.saveApplication(request);
        assertThat(applicationId, notNullValue());

        final ApplicationDto foundApplication = sbaHotelsClient.getApplicationById(applicationId);
        assertThat(foundApplication.getStatus(), is(Status.CREATED));
        assertSavedApplication(request, foundApplication);
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void getByIdApplicationAfterSentTest(final String jsonPath) {
        final ApplicationRequest request = getRequest(jsonPath);

        final Long applicationId = sbaHotelsClient.sendApplication(request);
        assertThat(applicationId, notNullValue());

        final ApplicationDto foundApplication = sbaHotelsClient.getApplicationById(applicationId);
        assertThat(foundApplication.getStatus(), is(Status.SENT));
        assertSavedApplication(request, foundApplication);
    }

    void assertSavedApplication(final ApplicationRequest request, final ApplicationDto foundApplication) {
        assertThat(foundApplication, notNullValue());
        assertThat(foundApplication.getId(), notNullValue());
        assertThat(foundApplication.getParent(), nullValue());
        assertThat(foundApplication.getLegId(), is(request.getLegId()));
        assertThat(foundApplication.getServiceType().getCode(), is(request.getServiceTypeCode().name()));
        assertThat(foundApplication.getComment(), is(request.getComment()));
        assertThat(foundApplication.getUpdated(), nullValue());
        assertThat(foundApplication.getSent(), nullValue());
        assertThat(foundApplication.getCreated().getCommittedAt(), notNullValue());
        assertThat(foundApplication.getCreated().getCommittedBy(), is("user2@s7.ru"));

        final FailedSituationDto failedRequest = request.getFailedSituation();
        final FailedSituationDto response = foundApplication.getFailedSituation();

        assertThat(response.getReasonId(), is(failedRequest.getReasonId()));
        assertThat(response.getFailedLegId(), is(failedRequest.getFailedLegId()));

        final ProviderDto providerResponse = foundApplication.getProvider();
        assertThat(providerResponse.getId(), is(request.getProviderId()));

        performAdditionalAfterSavingAssertions(request, foundApplication);
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void getByLegIdApplicationTest(final String jsonPath) {
        final ApplicationDto applicationDto = getApplication(jsonPath);

        final List<ApplicationDto> applicationDtoList = sbaHotelsClient.getApplicationByLegId(applicationDto.getLegId());
        assertThat(applicationDtoList, hasSize(1));
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void cancelApplicationTest(final String jsonPath) {
        final ApplicationDto applicationDto = getApplication(jsonPath);
        assertThat(applicationDto.getStatus(), is(Status.CREATED));

        final CancelApplicationRequest cancelRequest = getCancelRequest(applicationDto.getId(), applicationDto.getLegId());

        sbaHotelsClient.cancelApplication(cancelRequest);

        final ApplicationDto foundApplication = sbaHotelsClient.getApplicationById(applicationDto.getId());
        assertThat(foundApplication.getStatus(), is(Status.CANCELED));
        assertThat(foundApplication.getComment(), is(cancelRequest.getComment()));
    }

    @ParameterizedTest
    @MethodSource("getSaveRequestData")
    void deleteApplicationTest(final String jsonPath) {
        final ApplicationDto applicationDto = getApplication(jsonPath);
        assertThat(applicationDto.getStatus(), is(Status.CREATED));

        sbaHotelsClient.deleteApplication(applicationDto.getId());

        final ApplicationDto foundApplication = sbaHotelsClient.getApplicationById(applicationDto.getId());
        assertThat(foundApplication, nullValue());
    }

    private <T extends ApplicationRequest> T getRequest(final String dto) {
        return readObjectFromFile(dto, getRequestClass());
    }

    private Long saveApplication(final String dto) {
        final ApplicationRequest request = getRequest(dto);

        return sbaHotelsClient.saveApplication(request);
    }

    private ApplicationDto getApplication(final String dto) {
        final Long id = saveApplication(dto);

        return sbaHotelsClient.getApplicationById(id);
    }

    private CancelApplicationRequest getCancelRequest(final Long id, final String legId) {
        return new CancelApplicationRequest()
                .setId(id)
                .setLegId(legId)
                .setComment("canceled");
    }
}

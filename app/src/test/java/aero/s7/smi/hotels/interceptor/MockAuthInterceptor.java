package aero.s7.smi.hotels.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

public class MockAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.FROM, "user2@s7.ru");
    }

}

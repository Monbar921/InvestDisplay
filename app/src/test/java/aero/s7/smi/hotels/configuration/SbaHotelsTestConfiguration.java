package aero.s7.smi.hotels.configuration;

import aero.s7.smi.hotels.interceptor.MockAuthInterceptor;
import aero.s7.smi.hotels.starter.annotation.EnableSbaHotelsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSbaHotelsClient
public class SbaHotelsTestConfiguration {
    @Bean
    public MockAuthInterceptor mockAuthInterceptor() {
        return new MockAuthInterceptor();
    }
}

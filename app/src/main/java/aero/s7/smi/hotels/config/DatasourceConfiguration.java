package aero.s7.smi.hotels.config;

import aero.s7.smi.booking.client.starter.annotation.EnableBookingClient;
import aero.s7.smi.psn.api.starter.annotation.EnablePsnApiClient;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@EnableSchedulerLock(defaultLockAtMostFor = "PT60S", defaultLockAtLeastFor = "PT30S")
@EnableScheduling
@EnableBookingClient
@EnablePsnApiClient
@Configuration
public class DatasourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(final HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }
}

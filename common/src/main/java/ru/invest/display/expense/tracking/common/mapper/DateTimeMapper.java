package ru.invest.display.expense.tracking.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Mapper
@SuppressWarnings("checkstyle:AbstractClassName")
public abstract class DateTimeMapper {
    @Named("toLocalDateTime")
    public LocalDateTime toLocalDateTime(final ZonedDateTime zonedDateTime) {
        return Optional.ofNullable(zonedDateTime)
                .map(ZonedDateTime::toLocalDateTime)
                .orElse(null);
    }

    @Named("toZonedDateTime")
    public ZonedDateTime toZonedDateTime(final LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(dateTime -> dateTime.atZone(ZoneOffset.UTC))
                .orElse(null);
    }

    @Named("getCurrentDateTimeUtc")
    public LocalDateTime getCurrentDateTimeUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    @Named("getCurrentDateTimeUtc")
    public LocalDateTime getCurrentDateTimeUtc(final Object object) {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    @Named("getLocalDateTime")
    public LocalDateTime getLocalDateTime(final Timestamp timestamp) {
        return Optional
                .ofNullable(timestamp)
                .map(Timestamp::toInstant)
                .map(instant -> LocalDateTime.ofInstant(instant, ZoneOffset.UTC))
                .orElse(null);
    }
}

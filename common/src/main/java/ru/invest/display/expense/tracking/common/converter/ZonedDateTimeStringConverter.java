package ru.invest.display.expense.tracking.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Optional;

@Converter
public class ZonedDateTimeStringConverter implements AttributeConverter<ZonedDateTime, String> {

    @Override
    public String convertToDatabaseColumn(final ZonedDateTime attribute) {
        return Optional.ofNullable(attribute)
                .map(ZonedDateTime::toString)
                .orElse(null);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(final String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }

        return ZonedDateTime.parse(dbData);
    }
}

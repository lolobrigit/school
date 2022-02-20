package ru.edu.project.backend.da.jpa.converter;

import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.model.RequestStatus;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter
public class RequestStatusConverter implements AttributeConverter<Status, Long> {

    /**
     * Конвертация Status -> Long.
     *
     * @param status
     * @return long
     */
    @Override
    public Long convertToDatabaseColumn(final Status status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    /**
     * Конвертация Long -> RequestStatus enum.
     *
     * @param longValue
     * @return status enum
     */
    @Override
    public Status convertToEntityAttribute(final Long longValue) {
        return RequestStatus.byCode(longValue);
    }
}

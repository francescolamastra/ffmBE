package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SessioneMercatoOpAcquistoConverter implements AttributeConverter<Constants.SessioneMercatoOpAcquisto, String> {

    @Override
    public String convertToDatabaseColumn(Constants.SessioneMercatoOpAcquisto attribute) {
        return attribute != null ? attribute.getSigla() : null;
    }

    @Override
    public Constants.SessioneMercatoOpAcquisto convertToEntityAttribute(String dbData) {
        return dbData != null ? Constants.SessioneMercatoOpAcquisto.fromValue(dbData) : null;
    }
}

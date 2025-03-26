package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SessioneMercatoOpAcquistoConverter implements AttributeConverter<Constants.SessioneMercatoOpAcquistoEnum, String> {

    @Override
    public String convertToDatabaseColumn(Constants.SessioneMercatoOpAcquistoEnum attribute) {
        return attribute != null ? attribute.getSigla() : null;
    }

    @Override
    public Constants.SessioneMercatoOpAcquistoEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? Constants.SessioneMercatoOpAcquistoEnum.fromValue(dbData) : null;
    }
}

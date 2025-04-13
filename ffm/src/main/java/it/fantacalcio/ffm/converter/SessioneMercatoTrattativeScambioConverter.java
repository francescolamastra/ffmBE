package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SessioneMercatoTrattativeScambioConverter implements AttributeConverter<Constants.SessioneMercatoTrattiveScambioEnum, String> {

    @Override
    public String convertToDatabaseColumn(Constants.SessioneMercatoTrattiveScambioEnum attribute) {
        return attribute != null ? attribute.getSigla() : null;
    }

    @Override
    public Constants.SessioneMercatoTrattiveScambioEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? Constants.SessioneMercatoTrattiveScambioEnum.fromSigla(dbData) : null;
    }
}

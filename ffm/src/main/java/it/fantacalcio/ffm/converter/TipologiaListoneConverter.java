package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipologiaListoneConverter implements AttributeConverter<Constants.TipologiaListoneEnum, String> {

    @Override
    public String convertToDatabaseColumn(Constants.TipologiaListoneEnum attribute) {
        return attribute != null ? attribute.getSigla() : null;
    }

    @Override
    public Constants.TipologiaListoneEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? Constants.TipologiaListoneEnum.fromSigla(dbData) : null;
    }
}

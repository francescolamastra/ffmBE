package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipologiaRosaConverter implements AttributeConverter<Constants.TipologiaRosaEnum, String> {

    @Override
    public String convertToDatabaseColumn(Constants.TipologiaRosaEnum attribute) {
        return attribute != null ? attribute.getSigla() : null;
    }

    @Override
    public Constants.TipologiaRosaEnum convertToEntityAttribute(String dbData) {
        return dbData != null ? Constants.TipologiaRosaEnum.fromSigla(dbData) : null;
    }
}

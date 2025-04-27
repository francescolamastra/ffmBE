package it.fantacalcio.ffm.utility.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import it.fantacalcio.ffm.utility.Constants;

import java.io.IOException;

public class CustomSessioneMercatoOpAcquistoDeserializer extends JsonDeserializer<Constants.SessioneMercatoOpAcquistoEnum> {

    @Override
    public Constants.SessioneMercatoOpAcquistoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return Constants.SessioneMercatoOpAcquistoEnum.fromSigla(value);
    }
}

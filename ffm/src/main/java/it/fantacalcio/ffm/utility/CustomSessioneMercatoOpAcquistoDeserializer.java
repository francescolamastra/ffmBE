package it.fantacalcio.ffm.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomSessioneMercatoOpAcquistoDeserializer extends JsonDeserializer<Constants.SessioneMercatoOpAcquisto> {

    @Override
    public Constants.SessioneMercatoOpAcquisto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return Constants.SessioneMercatoOpAcquisto.fromValue(value);
    }
}

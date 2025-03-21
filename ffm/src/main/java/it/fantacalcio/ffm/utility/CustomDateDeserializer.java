package it.fantacalcio.ffm.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        if (dateString.contains(":")) {
            return LocalDateTime.parse(dateString, formatterDateTime);
        } else {
            LocalDate localDate = LocalDate.parse(dateString, formatterDate);
            return localDate.atStartOfDay();
        }
    }
}

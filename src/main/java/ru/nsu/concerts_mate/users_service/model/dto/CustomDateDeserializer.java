package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Objects.isNull;
@JsonComponent
public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat formatter
            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        if (isNull(date) || date.isEmpty()) {
            return null;
        }
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = date.substring(0, date.length() - 6);
        date = date.replace("T", " ");

        return formatter.parse(date, new ParsePosition(0));
    }
}
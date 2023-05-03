package at.spengergasse.spengermed;

import com.google.gson.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Component
public class LocalDateTimeJSONAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private static final DateTimeFormatter javascriptDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMAN);


    @Override
    public JsonElement serialize(LocalDateTime datetime, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(datetime.format(javascriptDateFormat));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), javascriptDateFormat);
    }

}

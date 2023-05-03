package at.spengergasse.spengermed;

import com.google.gson.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Component
public class LocalDateJSONAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private static final DateTimeFormatter javascriptDateFormat = 
//        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMAN);
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);


    @Override
    public JsonElement serialize(LocalDate datetime, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(datetime.format(javascriptDateFormat));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), javascriptDateFormat);
    }

}

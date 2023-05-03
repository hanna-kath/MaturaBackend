package at.spengergasse.spengermed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpengerspitalApplication {

  @Bean
  public Gson dateAwareGSON(){
    return new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJSONAdapter())
            .create();
  }

  public static void main(String[] args) {
    SpringApplication.run(SpengerspitalApplication.class, args);
  }
}

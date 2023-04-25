package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;

@AllArgsConstructor
@Component
@Log
public class MedicationDataGenerator implements CommandLineRunner {

    MedicationRepository medicationRepository;

    public static Medication returnOneMedication() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("23000000-0bb0-0000-a000-ed000000ac10")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        CodeableConcept codes = CodeableConcept.builder()
                .text("Medication Code 1")
                .build();

        Reference mahs = Reference.builder()
                .reference("Organization/1")
                .build();

        CodeableConcept doseforms = CodeableConcept.builder()
                .text("powder")
                .build();

        Quantity totalvolumes = Quantity.builder()
                .comparator(ComparatorEnum.GREATER)
                .unit("Unit 1")
                .decimal(250)
                .build();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder()
                .isActive(true)
                .build());

        Batch batchs = Batch.builder()
                .lotNumber("ID 111")
                .expirationDate(LocalDateTime.of(2022,01,01,12,00,00,00))
                .build();

        Reference definitions = Reference.builder()
                .reference("Patient/1")
                .build();

        Medication medication = Medication.builder()
                .identifier(identifiers)
                .code(codes)
                .status(Medication.StatusCode.inactive)
                .marketingAuthorizationHolder(mahs)
                .doseForm(doseforms)
                .totalVolume(totalvolumes)
                .ingredient(ingredients)
                .batch(batchs)
                .definition(definitions)
                .build();

        return medication;

    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Medication Commandline Runner started");

        medicationRepository.save(returnOneMedication());
    }
}

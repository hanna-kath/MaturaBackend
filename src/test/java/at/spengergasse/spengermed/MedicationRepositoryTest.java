package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.MedicationRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MedicationRepositoryTest {
    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    @Transactional
    public void testSaveLoadOneMedication() {
        Medication medication = returnOneMedication();
        Medication savedMed = medicationRepository.save(medication);
        Optional<Medication> loadedMedOptional = medicationRepository.findById(UUID.fromString(String.valueOf(savedMed.getId())));
        Medication loadedMed = loadedMedOptional.get();

        assertEquals(medication.getText(), loadedMed.getText());
        assertEquals(medication.getCode(), loadedMed.getCode());
        assertEquals(medication.getStatus(), loadedMed.getStatus());
        assertEquals(medication.getMarketingAuthorizationHolder(), loadedMed.getMarketingAuthorizationHolder());
        assertEquals(medication.getDoseForm(), loadedMed.getDoseForm());
        assertEquals(medication.getTotalVolume(), loadedMed.getTotalVolume());
        assertEquals(medication.getBatch(), loadedMed.getBatch());
        assertEquals(medication.getDefinition(), loadedMed.getDefinition());

        assertTrue(CollectionUtils.isEqualCollection(medication.getIdentifier(), loadedMed.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(medication.getIngredient(), loadedMed.getIngredient()));
    }

    @Test
    @Transactional
    public void testDeleteOneMedication() {
        Medication medication = returnOneMedication();
        Medication savedMed = medicationRepository.save(medication);
        medicationRepository.deleteById(UUID.fromString(String.valueOf(savedMed.getId())));
        assertTrue(medicationRepository.findById(UUID.fromString(String.valueOf(savedMed.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneMedication() {
        Medication med = returnOneMedication();
        Medication savedEmptyMed = medicationRepository.save(new Medication());
        savedEmptyMed.setCode(med.getCode());
        savedEmptyMed.setStatus(med.getStatus());
        savedEmptyMed.setMarketingAuthorizationHolder(med.getMarketingAuthorizationHolder());
        savedEmptyMed.setDoseForm(med.getDoseForm());
        savedEmptyMed.setTotalVolume(med.getTotalVolume());
        savedEmptyMed.setIngredient(med.getIngredient());
        savedEmptyMed.setBatch(med.getBatch());
        savedEmptyMed.setDefinition(med.getDefinition());
    }

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

        List<IngredientBB> ingredients = new ArrayList<>();
        ingredients.add(IngredientBB.builder()
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
}

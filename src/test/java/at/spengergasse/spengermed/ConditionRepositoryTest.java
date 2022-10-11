package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ConditionRepositoryTest {
    @Autowired
    private ConditionRepository conditionRepository;

    @Test
    @Transactional
    public void testSaveLoadOneCondition() {
        Condition c = returnOneCondition();
        Condition savedC = conditionRepository.save(c);
        Optional<Condition> loadedCOptional = conditionRepository.findById(UUID.fromString(String.valueOf(savedC.getId())));
        Condition loadedC = loadedCOptional.get();

        assertEquals(c.getText(), loadedC.getText());
        assertEquals(c.getClinicalStatus(), loadedC.getClinicalStatus());
        assertEquals(c.getVerificationStatus(), loadedC.getVerificationStatus());
        assertEquals(c.getSeverity(), loadedC.getSeverity());
        assertEquals(c.getCode(), loadedC.getCode());
        assertEquals(c.getSubject(), loadedC.getSubject());
        assertEquals(c.getEncounter(), loadedC.getEncounter());

        assertTrue(CollectionUtils.isEqualCollection(c.getIdentifier(), loadedC.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(c.getCategory(), loadedC.getCategory()));
        assertTrue(CollectionUtils.isEqualCollection(c.getBodySite(), loadedC.getBodySite()));
    }

    @Test
    @Transactional
    public void testDeleteOneCondition() {
        Condition c = returnOneCondition();
        Condition savedC = conditionRepository.save(c);
        conditionRepository.deleteById(UUID.fromString(String.valueOf(savedC.getId())));
        assertTrue(conditionRepository.findById(UUID.fromString(String.valueOf(savedC.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneCondition() {
        Condition c = returnOneCondition();
        Condition savedEmptyC = conditionRepository.save(new Condition());
        savedEmptyC.setClinicalStatus(c.getClinicalStatus());
        savedEmptyC.setVerificationStatus(c.getVerificationStatus());
        savedEmptyC.setCategory(c.getCategory());
        savedEmptyC.setSeverity(c.getSeverity());
        savedEmptyC.setCode(c.getCode());
        savedEmptyC.setBodySite(c.getBodySite());
        savedEmptyC.setSubject(c.getSubject());
        savedEmptyC.setEncounter(c.getEncounter());
    }

    //FALSCH NOCH: MUSS geändert werden
    public static Condition returnOneCondition() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-0000-000000000c12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<Coding> codings = new ArrayList<>();
        codings.add(Coding.builder()
                .code("active")
                .build());

        CodeableConcept clinicalStatuses = CodeableConcept.builder()
                .coding(codings)
                .text("de")
                .build();

        CodeableConcept verificationStatuses = CodeableConcept.builder()
                .coding(codings)
                .text("de")
                .build();

        List<CodeableConcept> categories = new ArrayList<>();
        categories.add(CodeableConcept.builder()
                .text("en")
                .coding(codings)
                .build());

        CodeableConcept severities = CodeableConcept.builder()
                .coding(codings)
                .text("de")
                .build();

        CodeableConcept codes = CodeableConcept.builder()
                .coding(codings)
                .text("de")
                .build();

        List<CodeableConcept> bodySites = new ArrayList<>();
        bodySites.add(CodeableConcept.builder()
                .text("en")
                .coding(codings)
                .build());

        Reference subjects = Reference.builder()
                .reference("Patient/1")
                .display("Consultation")
                .build();

        Reference encounters = Reference.builder()
                .reference("Encounter/1")
                .build();

        Condition c = Condition.builder()
                .identifier(identifiers)
                .clinicalStatus(clinicalStatuses)
                .verificationStatus(verificationStatuses)
                .category(categories)
                .severity(severities)
                .code(codes)
                .bodySite(bodySites)
                .subject(subjects)
                .encounter(encounters)
                .build();

        return c;

    }
}

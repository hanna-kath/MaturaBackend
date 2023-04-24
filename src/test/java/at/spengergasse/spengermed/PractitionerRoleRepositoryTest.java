package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.PractitionerRoleRepository;
import at.spengergasse.spengermed.repository.StructureMapRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PractitionerRoleRepositoryTest {

    @Autowired
    private PractitionerRoleRepository practitionerRoleRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOnePractitionerRole(){
        PractitionerRole pr = returnOnePractitionerRole();
        PractitionerRole savedPr = practitionerRoleRepository.save(pr);
        Optional<PractitionerRole> loadedPrOptional = practitionerRoleRepository.findById(UUID.fromString(String.valueOf(savedPr.getId())));
        PractitionerRole loadedPr = loadedPrOptional.get();

        assertEquals(pr.getText(), loadedPr.getText());
        assertEquals(pr.getActive(), loadedPr.getActive());
        assertEquals(pr.getPeriod(), loadedPr.getPeriod());
        assertEquals(pr.getPractitioner(), loadedPr.getPractitioner());

        assertTrue(CollectionUtils.isEqualCollection(pr.getIdentifier(), loadedPr.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getSpecialty(), loadedPr.getSpecialty()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getAvailableTime(), loadedPr.getAvailableTime()));

    }

    @Test
    @Transactional
    public void testDeletePractitionerRole(){
        PractitionerRole pr = returnOnePractitionerRole();
        PractitionerRole savedPr = practitionerRoleRepository.save(pr);
        practitionerRoleRepository.deleteById(UUID.fromString(String.valueOf(savedPr.getId())));
        assertTrue(practitionerRoleRepository.findById(UUID.fromString(String.valueOf(savedPr.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdatePractitionerRole(){
        PractitionerRole pr = returnOnePractitionerRole();
        PractitionerRole savedEmptyPr = practitionerRoleRepository.save(new PractitionerRole());
        savedEmptyPr.setText(pr.getText());
        savedEmptyPr.setIdentifier(pr.getIdentifier());
        savedEmptyPr.setActive(pr.getActive());
        savedEmptyPr.setPractitioner(pr.getPractitioner());
        savedEmptyPr.setPeriod(pr.getPeriod());
        savedEmptyPr.setSpecialty(pr.getSpecialty());
        savedEmptyPr.setAvailableTime(pr.getAvailableTime());
    }

    public static PractitionerRole returnOnePractitionerRole() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("4ea88404-e27c-11ed-b5ea-0242ac120002")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        Period periods = Period.builder()
                .start(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                .end(LocalDateTime.of(2030, 1, 1, 0, 0, 0))
                .build();

        Reference practitioners = Reference.builder()
                .reference("Practitioner/21")
                .display("Dr. Walter")
                .build();

        List<CodeableConcept> specialties = new ArrayList<>();
        specialties.add(CodeableConcept.builder()
                .text("en")
                .build());


        List<AvailableTime> availableTimes = new ArrayList<>();
        availableTimes.add(AvailableTime.builder()
                .availableEndTime(LocalTime.of(12,00,00))
                .availableStartTime(LocalTime.of(10,00,00))
                .allDay(true)
                .build());

        PractitionerRole pr = PractitionerRole.builder()
                .identifier(identifiers)
                .active(true)
                .period(periods)
                .practitioner(practitioners)
                .specialty(specialties)
                .availableTime(availableTimes)
                .build();

        return pr;
    }
}

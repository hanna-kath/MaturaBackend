package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.EncounterRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// Test, ob man das in die Datenbank speichern, abrufen, etc. kann
@SpringBootTest
public class EncounterRepositoryTest {

    @Autowired
    private EncounterRepository encounterRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneEncounter(){
        Encounter e = returnOneEncounter();
        Encounter savedE = encounterRepository.save(e);
        Optional<Encounter> loadedEOptional = encounterRepository.findById(UUID.fromString(String.valueOf(savedE.getId())));
        Encounter loadedE = loadedEOptional.get();

        assertEquals(e.getText(), loadedE.getText());
        assertEquals(e.getStatus(), loadedE.getStatus());
        assertEquals(e.getSubject(), loadedE.getSubject());
        assertEquals(e.getPeriod(), loadedE.getPeriod());
        assertEquals(e.getPartOf(), loadedE.getPartOf());

        assertTrue(CollectionUtils.isEqualCollection(e.getStatusHistory(), loadedE.getStatusHistory()));
        assertTrue(CollectionUtils.isEqualCollection(e.getType(), loadedE.getType()));
        assertTrue(CollectionUtils.isEqualCollection(e.getEpisodeOfCare(), loadedE.getEpisodeOfCare()));
        assertTrue(CollectionUtils.isEqualCollection(e.getParticipant(), loadedE.getParticipant()));
        assertTrue(CollectionUtils.isEqualCollection(e.getAppointment(), loadedE.getAppointment()));
        assertTrue(CollectionUtils.isEqualCollection(e.getReasonReference(), loadedE.getReasonReference()));
        assertTrue(CollectionUtils.isEqualCollection(e.getDiagnosis(), loadedE.getDiagnosis()));
    }

    @Test
    @Transactional
    public void testDeleteEncounter(){
        Encounter e = returnOneEncounter();
        Encounter savedE = encounterRepository.save(e);
        encounterRepository.deleteById(UUID.fromString(String.valueOf(savedE.getId())));
        assertTrue(encounterRepository.findById(UUID.fromString(String.valueOf(savedE.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateEncounter(){
        Encounter e = returnOneEncounter();
        Encounter savedEmptyE = encounterRepository.save(new Encounter());
        savedEmptyE.setText(e.getText());
        savedEmptyE.setStatus(e.getStatus());
        savedEmptyE.setStatusHistory(e.getStatusHistory());
        savedEmptyE.setType(e.getType());
        savedEmptyE.setSubject(e.getSubject());
        savedEmptyE.setEpisodeOfCare(e.getEpisodeOfCare());
        savedEmptyE.setParticipant(e.getParticipant());
        savedEmptyE.setAppointment(e.getAppointment());
        savedEmptyE.setPeriod(e.getPeriod());
        savedEmptyE.setReasonReference(e.getReasonReference());
        savedEmptyE.setDiagnosis(e.getDiagnosis());
        savedEmptyE.setPartOf(e.getPartOf());

    }

    public static Encounter returnOneEncounter() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("E_1")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        List<StatusHistory> statusHistories = new ArrayList<>();
        statusHistories.add(StatusHistory.builder()
                .status(StatusHistory.StatusCode.arrived)
                .period(new Period(LocalDateTime.of(2022, 04,12,8,0,0),
                        LocalDateTime.of(2022,04,14,9,0,0)))
                .build());

        List<Coding> codings = new ArrayList<>();
        codings.add(new Coding("System", "8.0.1", "code", "<div>...</div>", true));
        List<CodeableConcept> types = new ArrayList<>();
        types.add(CodeableConcept.builder()
                .coding(codings)
                .text("<div></div>")
                .build());

        Reference subjects = Reference.builder()
                .reference("Patient/1")
                .display("Consultation")
                .build();

        List<Reference> episodesOfCare = new ArrayList<>();
        episodesOfCare.add(Reference.builder()
                .reference("Encounter/1")
                .build());

        Reference i = Reference.builder()
                .reference("Practitioner/1")
                .display("Attending")
                .build();
        List<Participant> participants = new ArrayList<>();
        participants.add(Participant.builder()
                .type(types)
                .period(new Period(LocalDateTime.of(2022, 04,12,8,0,0),
                        LocalDateTime.of(2022,04,14,9,0,0)))
                .individual(i)
                .build());

        List<Reference> appointments = new ArrayList<>();
        appointments.add(Reference.builder()
                .reference("Appointment/example")
                .build());

        Period p = Period.builder()
                .start(LocalDateTime.of(2021, 06, 23, 3, 45, 0))
                .end(LocalDateTime.of(2022, 01, 29, 4, 12, 0))
                .build();

        List<Reference> reasonReferences = new ArrayList<>();
        reasonReferences.add(Reference.builder()
                .reference("Reference/braintumor")
                .build());

        Reference d = Reference.builder()
                .reference("Condition/tumor")
                .build();
        List<Diagnosis> diagnoses = new ArrayList<>();
        diagnoses.add(Diagnosis.builder()
                .condition(d)
                .rank(1)
                .build());

        Reference partsOf = Reference.builder()
                .reference("Encounter/1")
                .build();

        Encounter e = Encounter.builder()
                .identifier(identifiers)
                .status(Encounter.StatusCode.arrived)
                .statusHistory(statusHistories)
                .type(types)
                .subject(subjects)
                .episodeOfCare(episodesOfCare)
                .participant(participants)
                .appointment(appointments)
                .period(p)
                .reasonReference(reasonReferences)
                .diagnosis(diagnoses)
                .partOf(partsOf)
                .build();

        return e;
    }

}

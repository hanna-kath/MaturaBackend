package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ConsentRepository;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ImmunizationEvaluationRepositoryTest {

    @Autowired
    private ImmunizationEvaluationRepository immunizationEvaluationRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneImmunizationEvaluation(){
        ImmunizationEvaluation ie = returnOneImmunizationEvaluation();
        ImmunizationEvaluation savedIe = immunizationEvaluationRepository.save(ie);
        Optional<ImmunizationEvaluation> loadedIeOptional = immunizationEvaluationRepository.findById(savedIe.getId());
        ImmunizationEvaluation loadedIe = loadedIeOptional.get();

        assertEquals(ie.getText(), loadedIe.getText());
        assertEquals(ie.getStatus(), loadedIe.getStatus());
        assertEquals(ie.getPatient(), loadedIe.getPatient());
        assertEquals(ie.getDate(), loadedIe.getDate());
        assertEquals(ie.getAuthority(), loadedIe.getAuthority());
        assertEquals(ie.getTargetDisease(), loadedIe.getTargetDisease());
        assertEquals(ie.getImmunizationEvent(), loadedIe.getImmunizationEvent());
        assertEquals(ie.getDoseStatus(), loadedIe.getDoseStatus());
        assertEquals(ie.getDescription(), loadedIe.getDescription());
        assertEquals(ie.getSeries(), loadedIe.getSeries());
        assertEquals(ie.getDoseNumber(), loadedIe.getDoseNumber());
        assertEquals(ie.getSeriesDoses(), loadedIe.getSeriesDoses());

        assertTrue(CollectionUtils.isEqualCollection(ie.getIdentifier(), loadedIe.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ie.getDoseStatusReason(), loadedIe.getDoseStatusReason()));

    }

    @Test
    @Transactional
    public void testDeleteImmunizationEvaluation(){
        ImmunizationEvaluation ie = returnOneImmunizationEvaluation();
        ImmunizationEvaluation savedIe = immunizationEvaluationRepository.save(ie);
        immunizationEvaluationRepository.deleteById(savedIe.getId());
        assertTrue(immunizationEvaluationRepository.findById(savedIe.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateImmunizationEvaluation(){
        ImmunizationEvaluation ie = returnOneImmunizationEvaluation();
        ImmunizationEvaluation savedEmptyIe = immunizationEvaluationRepository.save(new ImmunizationEvaluation());
        savedEmptyIe.setText(ie.getText());
        savedEmptyIe.setStatus(ie.getStatus());
        savedEmptyIe.setPatient(ie.getPatient());
        savedEmptyIe.setDate(ie.getDate());
        savedEmptyIe.setAuthority(ie.getAuthority());
        savedEmptyIe.setTargetDisease(ie.getTargetDisease());
        savedEmptyIe.setImmunizationEvent(ie.getImmunizationEvent());
        savedEmptyIe.setDoseStatus(ie.getDoseStatus());
        savedEmptyIe.setDoseStatusReason(ie.getDoseStatusReason());
        savedEmptyIe.setDescription(ie.getDescription());
        savedEmptyIe.setSeries(ie.getSeries());
        savedEmptyIe.setDoseNumber(ie.getDoseNumber());
        savedEmptyIe.setSeriesDoses(ie.getSeriesDoses());
    }

    public static ImmunizationEvaluation returnOneImmunizationEvaluation() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("20000000-cabd-1654-0000-00000000ac12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        Reference patients = Reference.builder()
                .reference("Patient/1")
                .display("Patients")
                .build();

        Reference authorities = Reference.builder()
                .reference("Organization/1")
                .display("Organizations")
                .build();

        CodeableConcept targetdiseases = CodeableConcept.builder()
                .text("TargetDisease")
                .build();

        Reference immunizationevents = Reference.builder()
                .reference("ImmunizationEvents/1")
                .display("ImmunizationEvents")
                .build();

        CodeableConcept dosestatuses = CodeableConcept.builder()
                .text("DoseStatuses")
                .build();

        List<CodeableConcept> dosestatusreasons = new ArrayList<>();
        dosestatusreasons.add(CodeableConcept.builder()
                .text("Dose Status Reason")
                .build());

        ImmunizationEvaluation ie = ImmunizationEvaluation.builder()
                .identifier(identifiers)
                .status(ImmunizationEvaluation.StatusCode.completed)
                .patient(patients)
                .date(LocalDateTime.of(2023,05,01,12,00,00,00))
                .authority(authorities)
                .targetDisease(targetdiseases)
                .immunizationEvent(immunizationevents)
                .doseStatus(dosestatuses)
                .doseStatusReason(dosestatusreasons)
                .description("Description ImmunizationEvaluation")
                .series("Series 1")
                .doseNumber("DoseNumber: 1")
                .seriesDoses("SeriesDose: 1")
                .build();

        return ie;
    }


}

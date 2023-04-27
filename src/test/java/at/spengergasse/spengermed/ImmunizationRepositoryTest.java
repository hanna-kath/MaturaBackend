package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImmunizationRepository;
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

// Test, ob man das in die Datenbank speichern, abrufen, etc. kann
@SpringBootTest
public class ImmunizationRepositoryTest {

    @Autowired
    private ImmunizationRepository immunizationRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneImmunization(){
        Immunization i = returnOneImmunization();
        Immunization savedE = immunizationRepository.save(i);
        Optional<Immunization> loadedEOptional = immunizationRepository.findById(savedE.getId());
        Immunization loadedE = loadedEOptional.get();

        assertEquals(i.getText(), loadedE.getText());
        assertEquals(i.getStatus(), loadedE.getStatus());
        assertEquals(i.getStatusReason(), loadedE.getStatusReason());
        assertEquals(i.getVaccineCode(), loadedE.getVaccineCode());
        assertEquals(i.getAdministeredProduct(), loadedE.getAdministeredProduct());
        assertEquals(i.getManufacturer(), loadedE.getManufacturer());
        assertEquals(i.getLotNumber(), loadedE.getLotNumber());
        assertEquals(i.getExpirationDate(), loadedE.getExpirationDate());
        assertEquals(i.getPatient(), loadedE.getPatient());
        assertEquals(i.getEncounter(), loadedE.getEncounter());
        assertEquals(i.getOccurrenceDateTime(), loadedE.getOccurrenceDateTime());
        assertEquals(i.getOccurrenceString(), loadedE.getOccurrenceString());
        assertEquals(i.getPrimarySource(), loadedE.getPrimarySource());
        assertEquals(i.getInformationSource(), loadedE.getInformationSource());
        assertEquals(i.getLocation(), loadedE.getLocation());
        assertEquals(i.getSite(), loadedE.getSite());
        assertEquals(i.getRoute(), loadedE.getRoute());
        assertEquals(i.getDoseQuantity(), loadedE.getDoseQuantity());
        assertEquals(i.getIsSubpotent(), loadedE.getIsSubpotent());
        assertEquals(i.getFundingSource(), loadedE.getFundingSource());

        assertTrue(CollectionUtils.isEqualCollection(i.getIdentifier(), loadedE.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(i.getBasedOn(), loadedE.getBasedOn()));
        assertTrue(CollectionUtils.isEqualCollection(i.getSupportinginformation(), loadedE.getSupportinginformation()));
        assertTrue(CollectionUtils.isEqualCollection(i.getPerformer(), loadedE.getPerformer()));
        assertTrue(CollectionUtils.isEqualCollection(i.getNote(), loadedE.getNote()));
        assertTrue(CollectionUtils.isEqualCollection(i.getReason(), loadedE.getReason()));
        assertTrue(CollectionUtils.isEqualCollection(i.getSubpotentReason(), loadedE.getSubpotentReason()));
        assertTrue(CollectionUtils.isEqualCollection(i.getProgramEligibility(), loadedE.getProgramEligibility()));
        assertTrue(CollectionUtils.isEqualCollection(i.getReaction(), loadedE.getReaction()));
        assertTrue(CollectionUtils.isEqualCollection(i.getProtocolApplied(), loadedE.getProtocolApplied()));
    }

    @Test
    @Transactional
    public void testDeleteImmunization(){
        Immunization i = returnOneImmunization();
        Immunization savedI = immunizationRepository.save(i);
        immunizationRepository.deleteById(savedI.getId());
        assertTrue(immunizationRepository.findById(UUID.fromString(String.valueOf(savedI.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateImmunization(){
        Immunization e = returnOneImmunization();
        Immunization savedEmptyE = immunizationRepository.save(new Immunization());
        savedEmptyE.setText(e.getText());
        savedEmptyE.setBasedOn(e.getBasedOn());
        savedEmptyE.setStatus(e.getStatus());
        savedEmptyE.setStatusReason(e.getStatusReason());
        savedEmptyE.setVaccineCode(e.getVaccineCode());
        savedEmptyE.setAdministeredProduct(e.getAdministeredProduct());
        savedEmptyE.setManufacturer(e.getManufacturer());
        savedEmptyE.setLotNumber(e.getLotNumber());
        savedEmptyE.setExpirationDate(e.getExpirationDate());
        savedEmptyE.setPatient(e.getPatient());
        savedEmptyE.setEncounter(e.getEncounter());
        savedEmptyE.setSupportinginformation(e.getSupportinginformation());
        savedEmptyE.setOccurrenceDateTime(e.getOccurrenceDateTime());
        savedEmptyE.setOccurrenceString(e.getOccurrenceString());
        savedEmptyE.setPrimarySource(e.getPrimarySource());
        savedEmptyE.setInformationSource(e.getInformationSource());
        savedEmptyE.setLocation(e.getLocation());
        savedEmptyE.setSite(e.getSite());
        savedEmptyE.setRoute(e.getRoute());
        savedEmptyE.setDoseQuantity(e.getDoseQuantity());
        savedEmptyE.setPerformer(e.getPerformer());
        savedEmptyE.setNote(e.getNote());
        savedEmptyE.setReason(e.getReason());
        savedEmptyE.setIsSubpotent(e.getIsSubpotent());
        savedEmptyE.setSubpotentReason(e.getSubpotentReason());
        savedEmptyE.setProgramEligibility(e.getProgramEligibility());
        savedEmptyE.setFundingSource(e.getFundingSource());
        savedEmptyE.setReaction(e.getReaction());
        savedEmptyE.setProtocolApplied(e.getProtocolApplied());
    }

    public static Immunization returnOneImmunization() {
        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-afb2-1371-abcd-000000001612")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<Reference> basedons = new ArrayList<>();
        basedons.add(Reference.builder()
                .display("Display 1")
                .type("Type X")
                .build());

        CodeableConcept streasons = CodeableConcept.builder()
                .text("en")
                .build();

        CodeableConcept vaccinecodes = CodeableConcept.builder()
                .text("de")
                .build();

        Reference ref = Reference.builder()
                .display("Display 1")
                .type("Type X")
                .build();

        CodeableReference aproducts = CodeableReference.builder()
                .reference(ref)
                .build();

        CodeableReference mas = CodeableReference.builder()
                .reference(ref)
                .build();

        Reference patients = Reference.builder()
                .display("Patient 1")
                .type("Patient/1")
                .build();

        Reference encounters = Reference.builder()
                .display("encounters 1")
                .type("encounter/1")
                .build();

        List<Reference> sinformations = new ArrayList<>();
        basedons.add(Reference.builder()
                .display("Information 1")
                .type("any")
                .build());

        CodeableReference isources = CodeableReference.builder()
                .reference(ref)
                .build();

        Reference locations = Reference.builder()
                .display("locations 1")
                .type("Location/1")
                .build();

        CodeableConcept sites = CodeableConcept.builder()
                .text("de")
                .build();

        CodeableConcept routes = CodeableConcept.builder()
                .text("en")
                .build();

        Money dosequantities = Money.builder()
                .currency(Money.CurrencyCode.AED)
                .value(120000)
                .build();

        List<Performer> performers = new ArrayList<>();
        performers.add(Performer.builder()
                .actor(ref)
                .build());

        List<Annotation> notes = new ArrayList<>();
        notes.add(Annotation.builder()
                .text("Note 1")
                .authorString("By Dr. X")
                .build());

        List<CodeableReference> reasons = new ArrayList<>();
        reasons.add(CodeableReference.builder()
                .reference(ref)
                .build());

        List<CodeableConcept> sreasons = new ArrayList<>();
        sreasons.add(CodeableConcept.builder()
                .text("Subpotent Reason")
                .build());

        List<ProgramEligibility> pe = new ArrayList<>();
        pe.add(ProgramEligibility.builder()
                .program(routes)
                .programStatus(sites)
                .build());

        CodeableConcept fsources = CodeableConcept.builder()
                .text("en")
                .build();

        List<Reaction> reactions = new ArrayList<>();
        reactions.add(Reaction.builder()
                .reported(true)
                .dateTime(LocalDateTime.of(2023,12,12,12,0,0,0))
                .build());

        List<ProtocolApplied> protocolsapplied = new ArrayList<>();
        protocolsapplied.add(ProtocolApplied.builder()
                .series("Series 1")
                .doseNumber("11241")
                .seriesDoses("1 Dose")
                .build());

        Immunization i = Immunization.builder()
                .identifier(identifiers)
                .basedOn(basedons)
                .status(Immunization.code.completed)
                .statusReason(streasons)
                .vaccineCode(vaccinecodes)
                .administeredProduct(aproducts)
                .manufacturer(mas)
                .lotNumber("n14195")
                .expirationDate(LocalDate.of(2023,10,10))
                .patient(patients)
                .encounter(encounters)
                .supportinginformation(sinformations)
                .occurrenceDateTime(LocalDateTime.of(2023,10,10,10,10,0,0))
                .occurrenceString("occurence")
                .primarySource(true)
                .informationSource(isources)
                .location(locations)
                .site(sites)
                .route(routes)
                .doseQuantity(dosequantities)
                .performer(performers)
                .note(notes)
                .reason(reasons)
                .isSubpotent(true)
                .subpotentReason(sreasons)
                .programEligibility(pe)
                .fundingSource(fsources)
                .reaction(reactions)
                .protocolApplied(protocolsapplied)
                .build();

        return i;
    }




}


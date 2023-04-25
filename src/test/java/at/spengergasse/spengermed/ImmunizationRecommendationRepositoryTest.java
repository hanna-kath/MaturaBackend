package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.ImmunizationRecommendationRepository;
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
public class ImmunizationRecommendationRepositoryTest {
    @Autowired
    private ImmunizationRecommendationRepository immunizationRecommendationRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneImmunizationRecommendation(){
        ImmunizationRecommendation ir = returnOneImmunizationRecommendation();
        ImmunizationRecommendation savedIr = immunizationRecommendationRepository.save(ir);
        Optional<ImmunizationRecommendation> loadedIrOptional = immunizationRecommendationRepository.findById(savedIr.getId());
        ImmunizationRecommendation loadedIr = loadedIrOptional.get();

        assertEquals(ir.getText(), loadedIr.getText());
        assertEquals(ir.getPatient(), loadedIr.getPatient());
        assertEquals(ir.getDate(), loadedIr.getDate());
        assertEquals(ir.getAuthority(), loadedIr.getAuthority());

        assertTrue(CollectionUtils.isEqualCollection(ir.getIdentifier(), loadedIr.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ir.getRecommendation(), loadedIr.getRecommendation()));
    }

    @Test
    @Transactional
    public void testDeleteImmunizationRecommendation(){
        ImmunizationRecommendation ie = returnOneImmunizationRecommendation();
        ImmunizationRecommendation savedIe = immunizationRecommendationRepository.save(ie);
        immunizationRecommendationRepository.deleteById(savedIe.getId());
        assertTrue(immunizationRecommendationRepository.findById(savedIe.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateImmunizationRecommendation(){
        ImmunizationRecommendation ie = returnOneImmunizationRecommendation();
        ImmunizationRecommendation savedEmptyIe = immunizationRecommendationRepository.save(new ImmunizationRecommendation());
        savedEmptyIe.setText(ie.getText());
        savedEmptyIe.setPatient(ie.getPatient());
        savedEmptyIe.setDate(ie.getDate());
        savedEmptyIe.setAuthority(ie.getAuthority());
        savedEmptyIe.setRecommendation(ie.getRecommendation());
    }

    public static ImmunizationRecommendation returnOneImmunizationRecommendation() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("2000bb00-cabd-1654-1261-00000000ac12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        Reference patients = Reference.builder()
                .reference("Patient/2")
                .display("Patients")
                .build();

        Reference authorities = Reference.builder()
                .reference("Organization/2")
                .display("Organizations")
                .build();

        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(Recommendation.builder()
                .description("Description")
                .series("Series 2")
                .doseNumber("Number 2")
                .seriesDoses("Series Doses 2")
                .build());

        ImmunizationRecommendation ir = ImmunizationRecommendation.builder()
                .identifier(identifiers)
                .patient(patients)
                .date(LocalDateTime.of(2023,05,01,12,00,00,00))
                .authority(authorities)
                .recommendation(recommendations)
                .build();

        return ir;
    }

}

package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
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
public class RiskAssessmentRepositoryTest {

    @Autowired  //Automatisch wird eine Instanz erstellt
    private RiskAssessmentRepository riskAssessmentRepository;    //für Datenbankzugriff

    @Test           //Methode --> Testmethode
    @Transactional  //Datenbankzugriffe als Transaktion
    public void testSaveLoadOneRiskAssessment() {
        RiskAssessment ra = returnOneRiskAssessment(); //hat keinen PK, wird erst beim Speichern festgelegt
        RiskAssessment savedRa = riskAssessmentRepository.save(ra);
        Optional<RiskAssessment> loadedRaOptional = riskAssessmentRepository.findById(UUID.fromString(String.valueOf(savedRa.getId())));
        RiskAssessment loadedRa = loadedRaOptional.get();

        //compares the expected result with that of the actual result
        assertEquals(ra.getText(), loadedRa.getText());
        assertEquals(ra.getParent(), loadedRa.getParent());
        assertEquals(ra.getStatus(), loadedRa.getStatus());

        // asserts that a specified condition is true
        assertTrue(CollectionUtils.isEqualCollection(ra.getIdentifier(), loadedRa.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ra.getPrediction(), loadedRa.getPrediction()));

    }

    @Test
    @Transactional
    public void testDeleteOneRiskAssessment() {
        RiskAssessment ra = returnOneRiskAssessment();
        RiskAssessment savedRa = riskAssessmentRepository.save(ra);
        riskAssessmentRepository.deleteById(UUID.fromString(String.valueOf(savedRa.getId())));
        assertTrue(riskAssessmentRepository.findById(UUID.fromString(String.valueOf(savedRa.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneRiskAssessment() {
        RiskAssessment ra = returnOneRiskAssessment();
        RiskAssessment savedEmptyRa = riskAssessmentRepository.save(new RiskAssessment());
        savedEmptyRa.setText(ra.getText());
        savedEmptyRa.setStatus(ra.getStatus());
        savedEmptyRa.setParent(ra.getParent());
        savedEmptyRa.setPrediction(ra.getPrediction());
    }

    public static RiskAssessment returnOneRiskAssessment() {
        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-abcd-000432198765")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        Reference ref = Reference.builder()
                .reference("https://localhost:8080/api/patient/00000000-0000-0000-0000-000000000002")
                .type("Patient")
                .build();

        List<Coding> codings = new ArrayList<>();
        codings.add(new Coding("System", "8.0.1", "code", "<div>...</div>", true));

        List<Prediction> predictions = new ArrayList<>();
        CodeableConcept c1 = CodeableConcept.builder()
                .coding(codings).text("<div></div>")
                .build();
        Range ra1 = Range.builder()
                .high(90)
                .low(70)
                .build();
        Prediction p = Prediction.builder()
                .outcome(c1)
                .probabilityRange(ra1)
                .build();
        predictions.add(p);

        //Instanz erstellt und der bekommt die davor festegelegten Werte zugewiesen
        RiskAssessment ra = RiskAssessment.builder()
                .identifier(identifiers)
                .status(RiskAssessment.StatusCode.amended)
                .parent(ref)
                .prediction(predictions)
                .build();

        return ra;

    }
}

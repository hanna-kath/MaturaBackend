package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.IVisionPrescriptionRespository;
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
public class IVisionPrescriptionRepositoryTest {
    @Autowired
    private IVisionPrescriptionRespository iVisionPrescriptionRespository;

    @Test
    @Transactional
    public void testSaveLoadOneVisionPrescription() {
        VisionPrescription vp = returnOneVisionPrescription();
        VisionPrescription savedVp = iVisionPrescriptionRespository.save(vp);
        Optional<VisionPrescription> loadedVpOptional = iVisionPrescriptionRespository.findById(UUID.fromString(String.valueOf(savedVp.getId())));
        VisionPrescription loadedVp = loadedVpOptional.get();

        assertEquals(vp.getText(), loadedVp.getText());
        assertEquals(vp.getStatus(), loadedVp.getStatus());
        assertEquals(vp.getPatient(), loadedVp.getPatient());
        assertEquals(vp.getDateWritten(), loadedVp.getDateWritten());

        assertTrue(CollectionUtils.isEqualCollection(vp.getIdentifier(), loadedVp.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(vp.getLenseSpecification(), loadedVp.getLenseSpecification()));
    }

    @Test
    @Transactional
    public void testDeleteOneCondition() {
        VisionPrescription vp = returnOneVisionPrescription();
        VisionPrescription savedVp = iVisionPrescriptionRespository.save(vp);
        iVisionPrescriptionRespository.deleteById(UUID.fromString(String.valueOf(savedVp.getId())));
        assertTrue(iVisionPrescriptionRespository.findById(UUID.fromString(String.valueOf(savedVp.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneCondition() {
        VisionPrescription c = returnOneVisionPrescription();
        VisionPrescription savedEmptyC = iVisionPrescriptionRespository.save(new VisionPrescription());
        savedEmptyC.setPatient(c.getPatient());
        savedEmptyC.setDateWritten(c.getDateWritten());
        savedEmptyC.setLenseSpecification(c.getLenseSpecification());
    }

    public static VisionPrescription returnOneVisionPrescription() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-ab62-0000-000000000c12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<LenseSpecification> lenseSpecifications = new ArrayList<>();
        lenseSpecifications.add(LenseSpecification.builder()
                .eye(LenseSpecification.EyeCode.left)
                .sphere(2f)
                .add(3f)
                .cylinder(-0.5f)
                .axis(90)
                .build());

        Reference patients = Reference.builder()
                .reference("Patient/1")
                .build();

        VisionPrescription vp = VisionPrescription.builder()
                .identifier(identifiers)
                .status(VisionPrescription.StatusCode.acitve)
                .patient(patients)
                .dateWritten(LocalDateTime.of(2023, 04, 18, 12, 30,00,00))
                .lenseSpecification(lenseSpecifications)
                .build();

        return vp;

    }
}


package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImagingSelectionRepository;
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
public class ImagingSelectionRepositoryTest {

    @Autowired
    private ImagingSelectionRepository imagingselectionRepository;

    @Test
    @Transactional
    public void testSaveLoadOneImagingselection() {
        ImagingSelection i = returnOneImagingselection();
        ImagingSelection savedI = imagingselectionRepository.save(i);
        Optional<ImagingSelection> loadedIOptional = imagingselectionRepository.findById(UUID.fromString(String.valueOf(savedI.getId())));
        ImagingSelection loadedI = loadedIOptional.get();

//        assertEquals(g.getText(), loadedG.getText());

        assertEquals(i.getStatus(), loadedI.getStatus());
        assertEquals(i.getSubject(), loadedI.getSubject());
        assertEquals(i.getIssued(), loadedI.getIssued());
        assertEquals(i.getCode(), loadedI.getCode());
        assertEquals(i.getStudyuid(), loadedI.getStudyuid());
        assertEquals(i.getSeriesuid(), loadedI.getSeriesuid());
        assertEquals(i.getSeriesnumber(), loadedI.getSeriesnumber());
        assertEquals(i.getFrameofreferenceuid(), loadedI.getFrameofreferenceuid());
        assertEquals(i.getBodysite(), loadedI.getBodysite());

        assertTrue(CollectionUtils.isEqualCollection(i.getIdentifier(), loadedI.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(i.getPerformer(), loadedI.getPerformer()));
        assertTrue(CollectionUtils.isEqualCollection(i.getBasedon(), loadedI.getBasedon()));
        assertTrue(CollectionUtils.isEqualCollection(i.getCategory(), loadedI.getCategory()));
        assertTrue(CollectionUtils.isEqualCollection(i.getDerivedfrom(), loadedI.getDerivedfrom()));
        assertTrue(CollectionUtils.isEqualCollection(i.getEndpoint(), loadedI.getEndpoint()));
        assertTrue(CollectionUtils.isEqualCollection(i.getFocus(), loadedI.getFocus()));
        assertTrue(CollectionUtils.isEqualCollection(i.getInstance(), loadedI.getInstance()));

    }

    @Test
    @Transactional
    public void testDeleteOneImagingselection() {
        ImagingSelection i = returnOneImagingselection();
        ImagingSelection savedI = imagingselectionRepository.save(i);
        imagingselectionRepository.deleteById(UUID.fromString(String.valueOf(savedI.getId())));
        assertTrue(imagingselectionRepository.findById(UUID.fromString(String.valueOf(savedI.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneImagingselection() {
        ImagingSelection i = returnOneImagingselection();
        ImagingSelection savedEmptyI = imagingselectionRepository.save(new ImagingSelection());

        savedEmptyI.setText(i.getText());
        savedEmptyI.setStatus(i.getStatus());
        savedEmptyI.setSubject(i.getSubject());
        savedEmptyI.setIssued(i.getIssued());
        savedEmptyI.setPerformer(i.getPerformer());
        savedEmptyI.setBasedon(i.getBasedon());
        savedEmptyI.setCategory(i.getCategory());
        savedEmptyI.setCode(i.getCode());
        savedEmptyI.setStudyuid(i.getStudyuid());
        savedEmptyI.setDerivedfrom(i.getDerivedfrom());
        savedEmptyI.setEndpoint(i.getEndpoint());
        savedEmptyI.setSeriesuid(i.getSeriesuid());
        savedEmptyI.setSeriesnumber(i.getSeriesnumber());
        savedEmptyI.setFrameofreferenceuid(i.getFrameofreferenceuid());
        savedEmptyI.setBodysite(i.getBodysite());
        savedEmptyI.setFocus(i.getFocus());
        savedEmptyI.setInstance(i.getInstance());
    }

    public static ImagingSelection returnOneImagingselection() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-1278-a618-abcd-000000001612")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        Reference subjects = Reference.builder()
                .display("Group/1")
                .type("Group")
                .build();

        Reference p = Reference.builder()
                .display("Performer one")
                .type("Group")
                .build();

        List<Performer> performers = new ArrayList<>();
        performers.add(Performer.builder()
                .actor(p)
                .build());

        List<Reference> basedons = new ArrayList<>();
        basedons.add(Reference.builder()
                .display("Display 1")
                .type("Type X")
                .build());

        List<CodeableConcept> categories = new ArrayList<>();
        categories.add(CodeableConcept.builder()
                .text("Category 1")
                .build());

        CodeableConcept codes = CodeableConcept.builder()
                .text("Code 1")
                .build();

        List<Reference> derivedfroms = new ArrayList<>();
        derivedfroms.add(Reference.builder()
                .display("Display 1")
                .type("Derivedfrom")
                .build());

        List<Reference> endpoints = new ArrayList<>();
        derivedfroms.add(Reference.builder()
                .display("Endpoint 1")
                .type("URL")
                .build());

        Reference b = Reference.builder()
                .display("Bodysite one")
                .type("BodyStructure")
                .build();

        CodeableReference bodysites = CodeableReference.builder()
                .reference(b)
                .build();

        List<Reference> f = new ArrayList<>();
        f.add(Reference.builder()
                .type("ImagingSelection")
                .build());

        Coding c = new Coding();

        List<Instance> instances = new ArrayList<>();
        instances.add(Instance.builder()
                .title("Instance")
                .uid("00000000-2623-0000-abcd-0000000ano12")
                .sopClass(c)
                .build());

        ImagingSelection i = ImagingSelection.builder()
                .identifier(identifiers)
                .status(ImagingSelection.code.unknown)
                .subject(subjects)
                .issued(LocalDateTime.of(2023,05,05,10,00,00,00))
                .performer(performers)
                .basedon(basedons)
                .category(categories)
                .code(codes)
                .studyuid("ID 12345678")
                .derivedfrom(derivedfroms)
                .endpoint(endpoints)
                .seriesuid("12345678")
                .seriesnumber(1)
                .frameofreferenceuid("12345621aa1")
                .bodysite(bodysites)
                .focus(f)
                .instance(instances)
                .build();

        return i;
    }

}

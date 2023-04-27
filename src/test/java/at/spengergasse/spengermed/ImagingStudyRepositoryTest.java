package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImagingStudyRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ImagingStudyRepositoryTest {
    @Autowired
    private ImagingStudyRepository imagingStudyRepository;

    @Test
    @Transactional
    public void testSaveLoadOneImagingStudy() {
        ImagingStudy is = returnOneImagingStudy();
        ImagingStudy savedIs = imagingStudyRepository.save(is);
        Optional<ImagingStudy> loadedIsOptional = imagingStudyRepository.findById(UUID.fromString(String.valueOf(savedIs.getId())));
        ImagingStudy loadedIs = loadedIsOptional.get();

        assertEquals(is.getStatus(), loadedIs.getStatus());
        assertEquals(is.getSubject(), loadedIs.getSubject());
        assertEquals(is.getEncounter(), loadedIs.getEncounter());
        assertEquals(is.getStarted(), loadedIs.getStarted());
        assertEquals(is.getReferrer(), loadedIs.getReferrer());
        assertEquals(is.getNumberofSeries(), loadedIs.getNumberofSeries());
        assertEquals(is.getNumberofinstances(), loadedIs.getNumberofinstances());
        assertEquals(is.getLocation(), loadedIs.getLocation());
        assertEquals(is.getDescription(), loadedIs.getDescription());

        assertTrue(CollectionUtils.isEqualCollection(is.getIdentifier(), loadedIs.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(is.getModality(), loadedIs.getModality()));
        assertTrue(CollectionUtils.isEqualCollection(is.getBasedOn(), loadedIs.getBasedOn()));
        assertTrue(CollectionUtils.isEqualCollection(is.getPartOf(), loadedIs.getPartOf()));
        assertTrue(CollectionUtils.isEqualCollection(is.getEndpoint(), loadedIs.getEndpoint()));
        assertTrue(CollectionUtils.isEqualCollection(is.getProcedure(), loadedIs.getProcedure()));
        assertTrue(CollectionUtils.isEqualCollection(is.getReason(), loadedIs.getReason()));
        assertTrue(CollectionUtils.isEqualCollection(is.getNote(), loadedIs.getNote()));
        assertTrue(CollectionUtils.isEqualCollection(is.getSeries(), loadedIs.getSeries()));

    }

    @Test
    @Transactional
    public void testDeleteOneImagingStudy() {
        ImagingStudy is = returnOneImagingStudy();
        ImagingStudy savedIs = imagingStudyRepository.save(is);
        imagingStudyRepository.deleteById(UUID.fromString(String.valueOf(savedIs.getId())));
        assertTrue(imagingStudyRepository.findById(UUID.fromString(String.valueOf(savedIs.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneImagingStudy() {
        ImagingStudy is = returnOneImagingStudy();
        ImagingStudy savedIs = imagingStudyRepository.save(new ImagingStudy());

        savedIs.setIdentifier(is.getIdentifier());
        savedIs.setEncounter(is.getEncounter());
        savedIs.setEndpoint(is.getEndpoint());
        savedIs.setSubject(is.getSubject());
        savedIs.setStatus(is.getStatus());
        savedIs.setModality(is.getModality());
        savedIs.setStarted(is.getStarted());
        savedIs.setBasedOn(is.getBasedOn());
        savedIs.setPartOf(is.getPartOf());
        savedIs.setReferrer(is.getReferrer());
        savedIs.setNumberofSeries(is.getNumberofSeries());
        savedIs.setNumberofinstances(is.getNumberofinstances());
        savedIs.setProcedure(is.getProcedure());
        savedIs.setLocation(is.getLocation());
        savedIs.setReason(is.getReason());
        savedIs.setNote(is.getNote());
        savedIs.setDescription(is.getDescription());
        savedIs.setSeries(is.getSeries());
    }

    public static ImagingStudy returnOneImagingStudy() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-abcd-0000000ano12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<CodeableConcept> codes = new ArrayList<>();
        codes.add(CodeableConcept.builder()
                .text("en")
                .build());

        Reference s = Reference.builder()
                .type("subject")
                .build();

        Reference e = Reference.builder()
                .type("encounter")
                .build();

        List<Reference> lr = new ArrayList<>();
        lr.add(Reference.builder()
                .type("basedOn")
                .build());

        List<Reference> lr1 = new ArrayList<>();
        lr1.add(Reference.builder()
                .type("partOf")
                .build());

        Reference r1 = Reference.builder()
                .type("referrer")
                .build();

        List<Reference> le = new ArrayList<>();
        le.add(Reference.builder()
                .type("endpoint")
                .build());

        List<CodeableReference> procedures = new ArrayList<>();
        procedures.add(CodeableReference.builder()
                .reference(r1)
                .build());

        Reference r3 = Reference.builder()
                .type("location")
                .build();

        List<CodeableReference> lreason = new ArrayList<>();
        lreason.add(CodeableReference.builder()
                .reference(r1)
                .build());

        List<Annotation> a = new ArrayList<>();
        a.add(Annotation.builder()
                .text("annotation")
                .build());

        CodeableConcept cc = new CodeableConcept();

        List<Series> series = new ArrayList<>();
        series.add(Series.builder()
                .description("series")
                .uid("ID1234567")
                .modality(cc)
                .build());


        ImagingStudy is = ImagingStudy.builder()
               .identifier(identifiers)
               .status(ImagingStudy.code.registered)
               .modality(codes)
               .subject(s)
               .encounter(e)
               .started(LocalDateTime.of(1960, 1, 2,12,0,0,0))
               .basedOn(lr)
               .partOf(lr1)
               .referrer(r1)
               .endpoint(le)
               .numberofSeries(10)
               .numberofinstances(10)
               .procedure(procedures)
               .location(r3)
               .reason(lreason)
               .note(a)
               .description("description")
               .series(series)
               .build();

        return is;
    }
}

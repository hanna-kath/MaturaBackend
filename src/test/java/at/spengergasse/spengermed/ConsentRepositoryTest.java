package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ConsentRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ConsentRepositoryTest {

    @Autowired
    private ConsentRepository consentRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneConsent(){
        Consent c = returnOneConsent();
        Consent savedC = consentRepository.save(c);
        Optional<Consent> loadedCOptional = consentRepository.findById(savedC.getId());
        Consent loadedC = loadedCOptional.get();

        assertEquals(c.getText(), loadedC.getText());
        assertEquals(c.getStatus(), loadedC.getStatus());
        assertEquals(c.getSourceAttachment(), loadedC.getSourceAttachment());
        assertEquals(c.getSourceReference(), loadedC.getSourceReference());

        assertTrue(CollectionUtils.isEqualCollection(c.getPolicy(), loadedC.getPolicy()));
        assertTrue(CollectionUtils.isEqualCollection(c.getIdentifier(), loadedC.getIdentifier()));

    }

    @Test
    @Transactional
    public void testDeleteConsent(){
        Consent c = returnOneConsent();
        Consent savedC = consentRepository.save(c);
        consentRepository.deleteById(savedC.getId());
        assertTrue(consentRepository.findById(savedC.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateConsent(){
        Consent c = returnOneConsent();
        Consent savedEmptyC = consentRepository.save(new Consent());
        savedEmptyC.setText(c.getText());
        savedEmptyC.setStatus(c.getStatus());
        savedEmptyC.setSourceReference(c.getSourceReference());
        savedEmptyC.setSourceAttachment(c.getSourceAttachment());
        savedEmptyC.setPolicy(c.getPolicy());
    }

    public static Consent returnOneConsent() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("20000000-cabd-0000-0000-00000000ac12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        Reference sourceReferences = Reference.builder()
                .reference("Consent/1")
                .display("c")
                .build();

        Attachment sourceAttachments = Attachment.builder()
                .title("Consentformular")
                .build();

        List<Policy> policies = new ArrayList<>();
        policies.add(Policy.builder().authority("https://AuthorityRessource/ar8976").build());

        Consent c = Consent.builder()
                .identifier(identifiers)
                .status(Consent.StatusCode.active)
                .policy(policies)
                .sourceAttachment(sourceAttachments)
                .sourceReference(sourceReferences)
                .build();

        return c;
    }

}

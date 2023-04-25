package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.InsurancePlanRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InsurancePlanRepositoryTest {
    @Autowired
    private InsurancePlanRepository insurancePlanRepository;

    @Test
    @Transactional
    public void testSaveLoadOneInsurancePlan() {
        InsurancePlan ip = returnOneInsurancePlan();
        InsurancePlan savedIp = insurancePlanRepository.save(ip);
        Optional<InsurancePlan> loadedIPOptional = insurancePlanRepository.findById(UUID.fromString(String.valueOf(savedIp.getId())));
        InsurancePlan loadedIp = loadedIPOptional.get();

        assertTrue(CollectionUtils.isEqualCollection(ip.getIdentifier(), loadedIp.getIdentifier()));
        assertThat(ip.getStatus()).isEqualTo(savedIp.getStatus());
        assertTrue(CollectionUtils.isEqualCollection(ip.getType(), loadedIp.getType()));
        assertThat(ip.getName()).isEqualTo(savedIp.getName());
        assertThat(ip.getAlias()).isEqualTo(savedIp.getAlias());
        assertThat(ip.getPeriod()).isEqualTo(savedIp.getPeriod());
        assertThat(ip.getOwnedBy()).isEqualTo(savedIp.getOwnedBy());
        assertThat(ip.getAdministeredBy()).isEqualTo(savedIp.getAdministeredBy());
        assertTrue(CollectionUtils.isEqualCollection(ip.getCoverageArea(), loadedIp.getCoverageArea()));
        assertTrue(CollectionUtils.isEqualCollection(ip.getContact(), loadedIp.getContact()));
        assertTrue(CollectionUtils.isEqualCollection(ip.getEndpoint(), loadedIp.getEndpoint()));
        assertTrue(CollectionUtils.isEqualCollection(ip.getNetwork(), loadedIp.getNetwork()));
        assertTrue(CollectionUtils.isEqualCollection(ip.getCoverage(), loadedIp.getCoverage()));
        assertTrue(CollectionUtils.isEqualCollection(ip.getPlan(), loadedIp.getPlan()));
    }

    @Test
    @Transactional
    public void testDeleteOneInsurancePlan() {
        InsurancePlan ip = returnOneInsurancePlan();
        InsurancePlan savedIp = insurancePlanRepository.save(ip);
        insurancePlanRepository.deleteById(UUID.fromString(String.valueOf(savedIp.getId())));
        assertTrue(insurancePlanRepository.findById(UUID.fromString(String.valueOf(savedIp.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneInsurancePlan() {
        InsurancePlan ip = returnOneInsurancePlan();
        InsurancePlan savedEmptyIp = insurancePlanRepository.save(new InsurancePlan());

        savedEmptyIp.setStatus(ip.getStatus());
        savedEmptyIp.setType(ip.getType());
        savedEmptyIp.setName(ip.getName());
        savedEmptyIp.setAlias(ip.getAlias());
        savedEmptyIp.setPeriod(ip.getPeriod());
        savedEmptyIp.setOwnedBy(ip.getOwnedBy());
        savedEmptyIp.setOwnedBy(ip.getOwnedBy());
        savedEmptyIp.setAdministeredBy(ip.getAdministeredBy());
        savedEmptyIp.setCoverageArea(ip.getCoverageArea());
        savedEmptyIp.setContact(ip.getContact());
        savedEmptyIp.setEndpoint(ip.getEndpoint());
        savedEmptyIp.setNetwork(ip.getNetwork());
        savedEmptyIp.setCoverage(ip.getCoverage());
        savedEmptyIp.setPlan(ip.getPlan());

    }

    public static InsurancePlan returnOneInsurancePlan() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-abcd-00000000no12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<CodeableConcept> types = new ArrayList<>();
        types.add(CodeableConcept.builder()
                .text("abcd")
                .build());

        List<String> alias = new ArrayList<>();
        alias.add("ExPlan");
        alias.add("Number1Plan");

        Period periods = new Period();
        periods.setStart(LocalDateTime.now());

        Reference references = new Reference();
        references.setDisplay("example organization");

        List<Reference> references1 = new ArrayList<>();
        references1.add(Reference.builder()
                .display("ex")
                .build());

        List<Benefit> benefits = new ArrayList<>();
        benefits.add(Benefit.builder()
                .requirement("example")
                .type(CodeableConcept.builder().text("Plan").build())
                .build());

       List<Coverage> coverages = new ArrayList<>();
       coverages.add(Coverage.builder()
               .type(CodeableConcept.builder().text("new Plan").build())
               .benefit(benefits)
               .build());

        InsurancePlan ip = InsurancePlan.builder()
                .identifier(identifiers)
                .status(InsurancePlan.StatusCode.active)
                .type(types)
                .name("Example Plan")
                .alias(alias)
                .period(periods)
                .ownedBy(references)
                .administeredBy(references)
                .coverageArea(references1)
                .coverage(coverages)
                .build();

        return ip;
    }
}

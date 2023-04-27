package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImplementationGuideRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ImplementationGuideRepositoryTest {

    @Autowired
    private ImplementationGuideRepository igRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneImplementationGuide(){
        ImplementationGuide ig = returnOneImplementationGuide();
        ImplementationGuide savedIg = igRepository.save(ig);
        Optional<ImplementationGuide> loadedEOptional = igRepository.findById(savedIg.getId());
        ImplementationGuide loadedIg = loadedEOptional.get();

        assertEquals(ig.getText(), loadedIg.getText());
        assertEquals(ig.getUrl(), loadedIg.getUrl());
        assertEquals(ig.getVersion(), loadedIg.getVersion());
        assertEquals(ig.getVersionAlgorithmCoding(), loadedIg.getVersionAlgorithmCoding());
        assertEquals(ig.getVersionAlgorithmString(), loadedIg.getVersionAlgorithmString());
        assertEquals(ig.getName(), loadedIg.getName());
        assertEquals(ig.getTitle(), loadedIg.getTitle());
        assertEquals(ig.getStatus(), loadedIg.getStatus());
        assertEquals(ig.getExperimental(), loadedIg.getExperimental());
        assertEquals(ig.getDate(), loadedIg.getDate());
        assertEquals(ig.getPublisher(), loadedIg.getPublisher());
        assertEquals(ig.getDescription(), loadedIg.getDescription());
        assertEquals(ig.getPurpose(), loadedIg.getPurpose());
        assertEquals(ig.getCopyright(), loadedIg.getCopyright());
        assertEquals(ig.getCopyrightLabel(), loadedIg.getCopyrightLabel());
        assertEquals(ig.getPackageId(), loadedIg.getPackageId());
        assertEquals(ig.getLicense(), loadedIg.getLicense());
        assertEquals(ig.getFhirVersion(), loadedIg.getFhirVersion());
        assertEquals(ig.getVersionAlgorithmString(), loadedIg.getVersionAlgorithmString());
        assertEquals(ig.getDefinition(), loadedIg.getDefinition());
        assertEquals(ig.getManifest(), loadedIg.getManifest());

        assertTrue(CollectionUtils.isEqualCollection(ig.getIdentifier(), loadedIg.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ig.getContact(), loadedIg.getContact()));
        assertTrue(CollectionUtils.isEqualCollection(ig.getUseContext(), loadedIg.getUseContext()));
        assertTrue(CollectionUtils.isEqualCollection(ig.getJurisdication(), loadedIg.getJurisdication()));
        assertTrue(CollectionUtils.isEqualCollection(ig.getDependsOn(), loadedIg.getDependsOn()));
        assertTrue(CollectionUtils.isEqualCollection(ig.getGlobal(), loadedIg.getGlobal()));
    }

    @Test
    @Transactional
    public void testDeleteImplementationGuide(){
        ImplementationGuide ig = returnOneImplementationGuide();
        ImplementationGuide savedIg = igRepository.save(ig);
        igRepository.deleteById(savedIg.getId());
        assertTrue(igRepository.findById(UUID.fromString(String.valueOf(savedIg.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateImplementationGuide(){
        ImplementationGuide ig = returnOneImplementationGuide();
        ImplementationGuide savedEmptyIg = igRepository.save(new ImplementationGuide());
        savedEmptyIg.setText(ig.getText());
        savedEmptyIg.setUrl(ig.getUrl());
        savedEmptyIg.setIdentifier(ig.getIdentifier());
        savedEmptyIg.setVersion(ig.getVersion());
        savedEmptyIg.setVersionAlgorithmString(ig.getVersionAlgorithmString());
        savedEmptyIg.setVersionAlgorithmCoding(ig.getVersionAlgorithmCoding());
        savedEmptyIg.setName(ig.getName());
        savedEmptyIg.setTitle(ig.getTitle());
        savedEmptyIg.setStatus(ig.getStatus());
        savedEmptyIg.setExperimental(ig.getExperimental());
        savedEmptyIg.setDate(ig.getDate());
        savedEmptyIg.setPublisher(ig.getPublisher());
        savedEmptyIg.setContact(ig.getContact());
        savedEmptyIg.setDescription(ig.getDescription());
        savedEmptyIg.setUseContext(ig.getUseContext());
        savedEmptyIg.setJurisdication(ig.getJurisdication());
        savedEmptyIg.setPurpose(ig.getPurpose());
        savedEmptyIg.setCopyright(ig.getCopyright());
        savedEmptyIg.setCopyrightLabel(ig.getCopyrightLabel());
        savedEmptyIg.setPackageId(ig.getPackageId());
        savedEmptyIg.setLicense(ig.getLicense());
        savedEmptyIg.setFhirVersion(ig.getFhirVersion());
        savedEmptyIg.setDependsOn(ig.getDependsOn());
        savedEmptyIg.setGlobal(ig.getGlobal());
        savedEmptyIg.setDefinition(ig.getDefinition());
        savedEmptyIg.setManifest(ig.getManifest());

    }

    public static ImplementationGuide returnOneImplementationGuide() {

        //--------------------- IDENTIFIER ----------------------------------------

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("E_1")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());
        identifiers.add(Identifier.builder()
                .value("E_2")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.old)
                .build());

        //--------------------- CONTACT ----------------------------------------

        List<ContactDetail> contactDetails = new ArrayList<>();
        contactDetails.add(ContactDetail.builder()
                .name("Contact 1")
                .tele(ContactPoint.builder()
                        .system(ContactPoint.SystemCode.email)
                        .rank(1)
                        .use(ContactPoint.UseCode.work)
                        .value("contact1@gmail.com")
                        .period(Period.builder()
                                .start(LocalDateTime.of(2023,01,01,10,10,10))
                                .end(LocalDateTime.now())
                                .build())
                        .build())
                .build());
        contactDetails.add(ContactDetail.builder()
                .name("Contact 2")
                .tele(ContactPoint.builder()
                        .system(ContactPoint.SystemCode.phone)
                        .rank(1)
                        .use(ContactPoint.UseCode.work)
                        .value("+69 868 1246")
                        .period(Period.builder()
                                .start(LocalDateTime.of(2022,01,01,10,10,10))
                                .end(LocalDateTime.now())
                                .build())
                        .build())
                .build());

        //--------------------- JURISIDCATION ----------------------------------------

        List<Coding> codings = new ArrayList<>();
        codings.add(new Coding("System", "8.0.1", "code", "<div>...</div>", true));
        codings.add(new Coding("System", "8.0.2", "code", "<div>...</div>", true));

        List<Coding> codings2 = new ArrayList<>();
        codings2.add(new Coding("System", "9.3.2", "code", "<div>...</div>", true));
        codings2.add(new Coding("System", "9.4.0", "code", "<div>...</div>", true));

        List<CodeableConcept> jurisdications = new ArrayList<>();
        jurisdications.add(CodeableConcept.builder()
                        .text("Jurisdication 1")
                        .coding(codings)
                .build());
        jurisdications.add(CodeableConcept.builder()
                .text("Jurisdication 2")
                .coding(codings2)
                .build());

        //--------------------- DEPENDS ON ----------------------------------------

        List<DependsOn> depensOn = new ArrayList<>();
        depensOn.add(DependsOn.builder()
                        .uri("uri/fhir/5t432345")
                        .packageId("5t432345")
                        .version("5.3.2")
                        .reason("Implementation of new Resource")
                .build());
        depensOn.add(DependsOn.builder()
                .uri("uri/fhir/234t5z6")
                .packageId("234t5z6")
                .version("5.3.6")
                .reason("Changed relationship between resource 1 and resource 2")
                .build());

        //--------------------- GLOBAL ----------------------------------------

        List<Global> globals = new ArrayList<>();
        globals.add(Global.builder()
                .type(Global.Type.account)
                        .profile("Profile 1")
                .build());
        globals.add(Global.builder()
                .type(Global.Type.appointment)
                .profile("Profile 2")
                .build());

        //--------------------- Definition ----------------------------------------
        List<Grouping> groupings = new ArrayList<>();
        groupings.add(Grouping.builder()
                        .name("Grouping name 1")
                        .description("Description grouping 1")
                .build());
        groupings.add(Grouping.builder()
                .name("Grouping name 2")
                .description("Description grouping 2")
                .build());

        List<ResourceBB> resources = new ArrayList<>();
        Reference ref1 = Reference.builder()
                .reference("Ref 1")
                .display("Reference 1")
                .type("RefType2")
                .build();

        List<ResourceBB.FhirVersion> fhirVersions3= new ArrayList<>();
        fhirVersions3.add(ResourceBB.FhirVersion.V2);
        fhirVersions3.add(ResourceBB.FhirVersion.V4);
        List<String> profiles1 = Arrays.asList("Profile 1", "Profile 2", "Profile 3");
        resources.add(ResourceBB.builder()
                        .reference(ref1)
                        .fhirVersion(fhirVersions3)
                        .name("Res  1")
                        .description("Res 1")
                        .isExample(true)
                        .profile(profiles1)
                        .groupingId("Grouping id 1")
                .build());
        Reference ref2 = Reference.builder()
                .reference("Ref 2")
                .display("Reference 2")
                .type("RefType2")
                .build();
        List<String> profiles2 = Arrays.asList("Profile 4", "Profile 5", "Profile 6");

        resources.add(ResourceBB.builder()
                .reference(ref2)
                .fhirVersion(fhirVersions3)
                .name("Name Res 2")
                .description("Refsdesc 2")
                .isExample(true)
                .profile(profiles2)
                .groupingId("Grouping id 2")
                .build());

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(Parameter.builder()
                        .code(Coding.builder()
                                .code("Code 1")
                                .display("Display 1")
                                .version("V1")
                                .build())
                        .value("Value 1")
                .build());
        parameters.add(Parameter.builder()
                .code(Coding.builder()
                        .code("Code 2")
                        .display("Display 2")
                        .version("V2")
                        .build())
                .value("Value 2")
                .build());

        List<Template> templates = new ArrayList<>();
        templates.add(Template.builder()
                        .scope("Scope 1")
                        .code(Template.Code.template1)
                        .source("Source 1")
                .build());
        templates.add(Template.builder()
                .scope("Scope 2")
                .code(Template.Code.template2)
                .source("Source 2")
                .build());

        Definition definition = Definition.builder()
                .grouping(groupings)
                .resource(resources)
                .page(Page.builder()
                        .sourceString("Source")
                        .name("Page name")
                        .title("Page title")
                        .generation(Page.Generation.html)
                        .page(Arrays.asList("page 1", "page 2", "page 3"))
                        .build())
                .parameter(parameters)
                .template(templates)
                .build();

        //--------------------- Manifest ----------------------------------------

        List<ResourceBB.FhirVersion> fhirVersions2= new ArrayList<>();
        fhirVersions2.add(ResourceBB.FhirVersion.V2);
        fhirVersions2.add(ResourceBB.FhirVersion.V4);

        List<ResourceBB> resources1 = new ArrayList<>();
        List<String> profiles3 = Arrays.asList("Profile 7", "Profile 8", "Profile 9");
        Reference ref3 = Reference.builder()
                .reference("Ref 3")
                .display("Reference 3")
                .type("RefType3")
                .build();
        resources1.add(ResourceBB.builder()
                .reference(ref3)
                .fhirVersion(fhirVersions2)
                .isExample(true)
                .profile(profiles3)
                        .relativePath("Relative Path 1")
                .build());

        List<String> profiles4 = Arrays.asList("Profile 10", "Profile 11", "Profile 12");
        Reference ref4 = Reference.builder()
                .reference("Ref 4")
                .display("Reference 4")
                .type("RefType4")
                .build();
        resources1.add(ResourceBB.builder()
                .reference(ref4)
                .fhirVersion(fhirVersions2)
                .isExample(true)
                .profile(profiles4)
                .relativePath("Relative Path 1")
                .build());

        List<Page> pages = new ArrayList<>();
        pages.add(Page.builder()
                        .name("Name page 1")
                        .title("title page 1")
                        .anchor(Arrays.asList("Anchor 1", "Anchor 2", "Anchor 3"))
                .build());
        pages.add(Page.builder()
                .name("Name page 2")
                .title("title page 3")
                .anchor(Arrays.asList("Anchor 4", "Anchor 5", "Anchor 6"))
                .build());

        Manifest manifest = Manifest.builder()
                .rendering("Rendering")
                .resource(resources1)
                .page(pages)
                .image(Arrays.asList("Image 1", "Image 2", "Image 3"))
                .other(Arrays.asList("Other 1", "Other 2", "Other 3"))
                .build();


        //--------------------------------------------------------------------------

        List<ImplementationGuide.FhirVersion> fhirVersions= new ArrayList<>();
        fhirVersions.add(ImplementationGuide.FhirVersion.V2);
        fhirVersions.add(ImplementationGuide.FhirVersion.V4);

        Coding codingUsage = Coding.builder()
                .code("Code Usage")
                .build();
        Reference referenceUsage = Reference.builder()
                .reference("Reference Usage")
                .build();

        List<UsageContext> usageContexts = new ArrayList<>();
        usageContexts.add(UsageContext.builder()
                        .code(codingUsage)
                        .valueReference(referenceUsage)
                .build());
        usageContexts.add(UsageContext.builder()
                .code(codingUsage)
                .valueReference(referenceUsage)
                .build());


        ImplementationGuide implementationGuide = ImplementationGuide.builder()
                .url("ImplementationGuide/2.3.2")
                .identifier(identifiers)
                .version("2.3.2")
                .versionAlgorithmString("BogoSort")
                .name("Implementation Guide for FHIR Version 2.3.2")
                .title("ImplementationGuide FHIR 2.3.2")
                .status(ImplementationGuide.Status.active)
                .experimental(true)
                .date(LocalDateTime.now())
                .useContext(usageContexts)
                .publisher("HL7")
                .contact(contactDetails)
                .description("Description")
                .jurisdication(jurisdications)
                .purpose("Purpose")
                .copyright("Copyright HL7")
                .copyrightLabel("Copyright Label HL7")
                .packageId("1234567u5t43refwrtz653rf")
                .license(ImplementationGuide.License.OBSD)
                .fhirVersion(fhirVersions)
                .dependsOn(depensOn)
                .global(globals)
                .definition(definition)
                .manifest(manifest)
                .build();

        return implementationGuide;
    }
}

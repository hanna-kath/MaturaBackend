package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.PractitionerRepository;
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

import static org.junit.jupiter.api.Assertions.*;

//Funktionalität vom Repository getestet. (CRUD Funktionen auf die Datenbank)

@SpringBootTest //Durch diese Annotation wird aus der Klasse eine Testklasse
public class PractitionerRepositoryTest {

    //Datenbank-Interaktion wird getestet
    @Autowired  //Automatisch wird eine Instanz erstellt
    private PractitionerRepository practitionerRepository;    //für Datenbankzugriff

    @Test           //Methode --> Testmethode
    @Transactional  //Datenbankzugriffe als Transaktion
    public void testSaveLoadOnePractitioner() {
        Practitioner pr = returnOnePractitioner(); //hat keinen PK, wird erst beim Speichern festgelegt
        Practitioner savedPr = practitionerRepository.save(pr);
        Optional<Practitioner> loadedPrOptional = practitionerRepository.findById(UUID.fromString(String.valueOf(savedPr.getId())));
        Practitioner loadedPr = loadedPrOptional.get();

        //compares the expected result with that of the actual result
        assertEquals(pr.getText(), loadedPr.getText());
        assertEquals(pr.getGender(), loadedPr.getGender());
        assertEquals(pr.getBirthDate(), loadedPr.getBirthDate());
        assertEquals(pr.isActive(), loadedPr.isActive());

        // asserts that a specified condition is true
        assertTrue(CollectionUtils.isEqualCollection(pr.getTelecom(), loadedPr.getTelecom()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getName(), loadedPr.getName()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getIdentifier(), loadedPr.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getAddress(), loadedPr.getAddress()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getPhoto(), loadedPr.getPhoto()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getQualification(), loadedPr.getQualification()));
        assertTrue(CollectionUtils.isEqualCollection(pr.getCommunication(), loadedPr.getCommunication()));
    }

    @Test
    @Transactional
    public void testDeleteOnePractitioner() {
        Practitioner pr = returnOnePractitioner();
        Practitioner savedPr = practitionerRepository.save(pr);
        practitionerRepository.deleteById(UUID.fromString(String.valueOf(savedPr.getId())));
        assertTrue(practitionerRepository.findById(UUID.fromString(String.valueOf(savedPr.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOnePractitioner() {
        Practitioner pr = returnOnePractitioner();
        Practitioner savedEmptyPr = practitionerRepository.save(new Practitioner());
        savedEmptyPr.setActive(pr.isActive());
        savedEmptyPr.setText(pr.getText());
        savedEmptyPr.setGender(pr.getGender());
        savedEmptyPr.setBirthDate(pr.getBirthDate());
        savedEmptyPr.setTelecom(pr.getTelecom());
        savedEmptyPr.setName(pr.getName());
        //savedEmptyPr.setIdentifier(pr.getIdentifier());   //??braucht man das??
        savedEmptyPr.setAddress(pr.getAddress());
        savedEmptyPr.setPhoto(pr.getPhoto());
        savedEmptyPr.setQualification(pr.getQualification());
        savedEmptyPr.setCommunication(pr.getCommunication());
    }

    public static Practitioner returnOnePractitioner() {
        List<HumanName> names = new ArrayList<>();
        List<String> suffixes = new ArrayList<>();
        suffixes.add("MD");
        List<String> givenNames = new ArrayList<>();
        givenNames.add("John");
        names.add(HumanName.builder()
                .family("Boockvar")
                .given(givenNames)
                .period(new Period(LocalDateTime.of(1975, 05, 05, 0, 0, 0),
                        LocalDateTime.of(2065, 01,01,0, 0, 0)))
                .text("<div> John Boockvar MD </div>")
                .suffix(suffixes)
                .use(HumanName.UseCode.official)
             .build());

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                    .value("00000000-0000-0000-0000-000432198765")
                    .type(CodeableConcept.builder().build())
                    .use(Identifier.UseCode.official)
                .build());

        List<Address> addresses = new ArrayList<>();
        List<String> addressLines = new ArrayList<>();
        addressLines.add("John Boockvar");
        addressLines.add("100 East 77th Street");
        addressLines.add("10022 Manhattan, New York City");
        addressLines.add("United States");
        addresses.add(Address.builder()
                    .use(Address.UseCode.work)
                    .type(Address.TypeCode.postal)
                    .district("Manhattan")
                    .country("United States")
                    .city("New York City")
                    .postalcode("10022")
                    .state("New York")
                    .period(new Period(LocalDateTime.of(2010, 12,12,0,0,0),
                            LocalDateTime.of(2050,06,06,0,0,0)))
                    .line(addressLines)
                    .text("<div> Address...</div>")
                .build());

        List<ContactPoint> telecoms = new ArrayList<>();
        telecoms.add(ContactPoint.builder()
                    .system(ContactPoint.SystemCode.email)
                    .use(ContactPoint.UseCode.work)
                    .value("johnboockvar@gmail.com")
                    .rank(1)
                    .period(new Period(LocalDateTime.of(2010, 12,12,0,0,0),
                            LocalDateTime.of(2050,06,06,0,0,0)))
                .build());

        List<CodeableConcept> communications = new ArrayList<>();
        communications.add(CodeableConcept.builder().text("en")
                .build());

        List<Attachment> photos = new ArrayList<>();
        photos.add(Attachment.builder()
                    .contenttype(Attachment.ContentCode.image)
                    .language(Attachment.LanguageCode.english)
                    .data("https://pbs.twimg.com/profile_images/1300082353172361216/6UaJjLoy_400x400.jpg")
                    .creation(LocalDateTime.of(2020, 12,12,0,0,0))
                    .title("Dr. John Boockvar MD")
                .build());

        // da fehlt noch code und identifier
        List<Qualification> qualifications = new ArrayList<>();
        qualifications.add(Qualification.builder()
                    .period(new Period(LocalDateTime.of(2010, 12,12,0,0,0),
                            LocalDateTime.of(2050,06,06,0,0,0)))
                .build());

        //Instanz erstellt und der bekommt die davor festegelegten Werte zugewiesen
        Practitioner pr = Practitioner.builder()
                    .telecom(telecoms)
                    .identifier(identifiers)
                    .name(names)
                    .active(false)
                    .gender(Patient.GenderCode.male)
                    .address(addresses)
                    .birthDate(LocalDate.of(1975,05,05))
                    .communication(communications)
                    .photo(photos)
                    .qualification(qualifications)
                .build();

        return pr;

    }

}



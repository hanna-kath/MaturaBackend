package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.OrganizationRepository;
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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneOrganization(){
        Organization o = returnOneOrganization();
        Organization savedO = organizationRepository.save(o);
        Optional<Organization> loadedOptional = organizationRepository.findById(UUID.fromString(String.valueOf(savedO.getId())));
        Organization loadedO = loadedOptional.get();

        assertEquals(o.getText(), loadedO.getText());
        assertEquals(o.getActive(),loadedO.getActive());
        assertEquals(o.getName(), loadedO.getName());

        assertTrue(CollectionUtils.isEqualCollection(o.getIdentifier(), loadedO.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(o.getAddress(), loadedO.getAddress()));
        assertTrue(CollectionUtils.isEqualCollection(o.getContact(), loadedO.getContact()));
    }

    @Test
    @Transactional
    public void testDeleteOrganization(){
        Organization o = returnOneOrganization();
        Organization savedO = organizationRepository.save(o);
        organizationRepository.deleteById(UUID.fromString(String.valueOf(savedO.getId())));
        assertTrue(organizationRepository.findById(UUID.fromString(String.valueOf(savedO.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOrganization(){
        Organization o = returnOneOrganization();
        Organization savedEmptyO = organizationRepository.save(new Organization());
        savedEmptyO.setText(o.getText());
        savedEmptyO.setActive(o.getActive());
        savedEmptyO.setAddress(o.getAddress());
        savedEmptyO.setName(o.getName());
        savedEmptyO.setContact(o.getContact());
        //Asserts?? --> Die von oben
    }

    public static Organization returnOneOrganization() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("O_1")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        List<Address> addresses = new ArrayList<>();
        List<String> addressLines = new ArrayList<>();
        addressLines.add("Organization");
        addressLines.add("Organization Street");
        addressLines.add("Austria");
        addresses.add(Address.builder()
                .use(Address.UseCode.work)
                .type(Address.TypeCode.postal)
                .district("Margareten")
                .country("Austria")
                .city("Vienna")
                .postalcode("1050")
                .state("Vienna")
                .period(new Period(LocalDateTime.of(2022, 12,12,0,0,0),
                        LocalDateTime.of(2050,06,06,0,0,0)))
                .line(addressLines)
                .text("<div> Address...</div>")
                .build());

        HumanName names = HumanName.builder()
                .text("Hanna Walter")
                .family("Walter")
                .build();

        CodeableConcept purposes = CodeableConcept.builder()
                .text("...")
                .build();

        List<Contact> contacts = new ArrayList<>();
        contacts.add(Contact.builder()
                .name(names)
                .purpose(purposes)
                .build());

        Organization o = Organization.builder()
                .identifier(identifiers)
                .active(false)
                .address(addresses)
                .name("Organization 1")
                .contact(contacts)
                .build();

        return o;
    }

}

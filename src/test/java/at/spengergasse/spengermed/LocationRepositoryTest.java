package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.LocationRepository;
import at.spengergasse.spengermed.repository.StructureMapRepository;
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
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneLocation(){
        Location l = returnOneLocation();
        Location savedL = locationRepository.save(l);
        Optional<Location> loadedLOptional = locationRepository.findById(UUID.fromString(String.valueOf(savedL.getId())));
        Location loadedL = loadedLOptional.get();

        assertEquals(l.getText(), loadedL.getText());
        assertEquals(l.getStatus(), loadedL.getStatus());
        assertEquals(l.getOperationalStatus(), loadedL.getOperationalStatus());
        assertEquals(l.getName(), loadedL.getName());
        assertEquals(l.getAlias(), loadedL.getAlias());
        assertEquals(l.getDescription(), loadedL.getDescription());
        assertEquals(l.getMode(), loadedL.getMode());
        assertEquals(l.getAddress(), loadedL.getAddress());
        assertEquals(l.getForm(), loadedL.getForm());
        assertEquals(l.getPosition(), loadedL.getPosition());
        assertEquals(l.getManagingOrganization(), loadedL.getManagingOrganization());
        assertEquals(l.getPartOf(), loadedL.getPartOf());

        assertTrue(CollectionUtils.isEqualCollection(l.getIdentifier(), loadedL.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(l.getType(), loadedL.getType()));
        assertTrue(CollectionUtils.isEqualCollection(l.getContact(), loadedL.getContact()));
        assertTrue(CollectionUtils.isEqualCollection(l.getCharacteristic(), loadedL.getCharacteristic()));
        assertTrue(CollectionUtils.isEqualCollection(l.getHoursOfOperation(), loadedL.getHoursOfOperation()));
        assertTrue(CollectionUtils.isEqualCollection(l.getVirtualService(), loadedL.getVirtualService()));
        assertTrue(CollectionUtils.isEqualCollection(l.getEndpoint(), loadedL.getEndpoint()));
    }

    @Test
    @Transactional
    public void testDeleteLocation(){
        Location l = returnOneLocation();
        Location savedL = locationRepository.save(l);
        locationRepository.deleteById(UUID.fromString(String.valueOf(savedL.getId())));
        assertTrue(locationRepository.findById(UUID.fromString(String.valueOf(savedL.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateLocation(){
        Location l = returnOneLocation();
        Location savedEmptyL = locationRepository.save(new Location());
        savedEmptyL.setText(l.getText());
        savedEmptyL.setStatus(l.getStatus());
        savedEmptyL.setOperationalStatus(l.getOperationalStatus());
        savedEmptyL.setName(l.getName());
        savedEmptyL.setAlias(l.getAlias());
        savedEmptyL.setDescription(l.getDescription());
        savedEmptyL.setMode(l.getMode());
        savedEmptyL.setType(l.getType());
        savedEmptyL.setContact(l.getContact());
        savedEmptyL.setAddress(l.getAddress());
        savedEmptyL.setForm(l.getForm());
        savedEmptyL.setPosition(l.getPosition());
        savedEmptyL.setManagingOrganization(l.getManagingOrganization());
        savedEmptyL.setPartOf(l.getPartOf());
        savedEmptyL.setCharacteristic(l.getCharacteristic());
        savedEmptyL.setHoursOfOperation(l.getHoursOfOperation());
        savedEmptyL.setVirtualService(l.getVirtualService());
        savedEmptyL.setEndpoint(l.getEndpoint());
    }

    public static Location returnOneLocation() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("4ea88404-8762-1245-b5ea-0242ac120002")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        Coding ops = Coding.builder()
                .code("operationalstatus")
                .display("Status: active")
                .system("OP")
                .build();

        List<String> aliass = new ArrayList<>();
        aliass.add("alias 1");
        aliass.add("alias 2");
        aliass.add("alias 3");

        List<CodeableConcept> types = new ArrayList<>();
        types.add(CodeableConcept.builder()
                .text("de")
                .build());


        Address addresses = Address.builder()
                .use(Address.UseCode.work)
                .type(Address.TypeCode.postal)
                .district("Margareten")
                .country("Austria")
                .city("Vienna")
                .postalcode("1050")
                .state("Vienna")
                .period(
                        Period.builder()
                                .start(LocalDateTime.of(2010, 1, 1, 0, 0, 0))
                                .end(LocalDateTime.of(2050, 1, 1, 0, 0, 0))
                                .build())
                .text("<div>Address...</div>")
                .build();

        List<HumanName> names = new ArrayList<>();
        names.add(
                HumanName.builder()
                        .family("Pirker")
                        .given(List.of("Simon"))
                        .prefix(List.of("Mag.", "DI"))
                        .suffix(List.of("Bakk.techn."))
                        .period(
                                Period.builder()
                                        .start(LocalDateTime.of(2010, 1, 1, 0, 0, 0))
                                        .end(LocalDateTime.of(2050, 1, 1, 0, 0, 0))
                                        .build())
                        .text("<div> Mag. DI Simon Pirker, Bakk.techn.</div>")
                        .use(HumanName.UseCode.official)
                        .build());

        List<ExtendedContactDetail> contacts = new ArrayList<>();
        contacts.add(ExtendedContactDetail.builder()
                .address((Address) addresses)
                .name(names)
                .build());

        CodeableConcept forms = CodeableConcept.builder()
                .text("en")
                .build();

        Position positions = Position.builder()
                .altitude(234.0)
                .latitude(31.1)
                .build();

        Reference mans = Reference.builder()
                .reference("Location/1")
                .build();

        Reference partOfs = Reference.builder()
                .reference("PartOf/1")
                .build();

        List<CodeableConcept> characteristics = new ArrayList<>();
        characteristics.add(CodeableConcept.builder()
                .text("en")
                .build());

        List<AvailableTime> times = new ArrayList<>();
        times.add(AvailableTime.builder()
                .allDay(true)
                .build());

        List<Availability> hours = new ArrayList<>();
        hours.add(Availability.builder()
                .availableTime(times)
                .build());

        List<VirtualServiceDetail> virtualservices = new ArrayList<>();
        virtualservices.add(VirtualServiceDetail.builder()
                .addressString("String 1")
                .addressUrl("http://fhir.at")
                .sessionKey("Key 1")
                .build());

        List<Reference> endpoints = new ArrayList<>();
        endpoints.add(Reference.builder()
                .reference("Referene/Location/1")
                .type("Location")
                .build());

        Location l = Location.builder()
                .identifier(identifiers)
                .status(Location.StatusCode.active)
                .operationalStatus(ops)
                .name("Name x")
                .alias(aliass)
                .description("Description 1")
                .mode(Location.ModeCode.instance)
                .type(types)
                .contact(contacts)
                .address(addresses)
                .form(forms)
                .position(positions)
                .managingOrganization(mans)
                .partOf(partOfs)
                .characteristic(characteristics)
                .hoursOfOperation(hours)
                .virtualService(virtualservices)
                .endpoint(endpoints)
                .build();

        return l;
    }
}

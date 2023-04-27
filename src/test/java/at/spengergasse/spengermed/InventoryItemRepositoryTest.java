package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImagingStudyRepository;
import at.spengergasse.spengermed.repository.InventoryItemRepository;
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
public class InventoryItemRepositoryTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Test
    @Transactional
    public void testSaveLoadOneInventoryItem() {
        InventoryItem ii = returnOneInventoryItem();
        InventoryItem savedIi = inventoryItemRepository.save(ii);
        Optional<InventoryItem> loadedIiOptional = inventoryItemRepository.findById(UUID.fromString(String.valueOf(savedIi.getId())));
        InventoryItem loadedIi = loadedIiOptional.get();

        assertEquals(ii.getStatus(), loadedIi.getStatus());
        assertEquals(ii.getDescription(), loadedIi.getDescription());
        assertEquals(ii.getBaseUnit(), loadedIi.getBaseUnit());
        assertEquals(ii.getNetContent(), loadedIi.getNetContent());
        assertEquals(ii.getInstance(), loadedIi.getInstance());
        assertEquals(ii.getProductReference(), loadedIi.getProductReference());

        assertTrue(CollectionUtils.isEqualCollection(ii.getIdentifier(), loadedIi.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getCategory(), loadedIi.getCategory()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getCode(), loadedIi.getCode()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getName(), loadedIi.getName()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getResponsibleOrganization(), loadedIi.getResponsibleOrganization()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getInventoryStatus(), loadedIi.getInventoryStatus()));
//        assertTrue(CollectionUtils.isEqualCollection(ii.getAssociation(), loadedIi.getAssociation()));
        assertTrue(CollectionUtils.isEqualCollection(ii.getCharacteristic(), loadedIi.getCharacteristic()));
    }

    @Test
    @Transactional
    public void testDeleteOneInventoryItem() {
        InventoryItem ii = returnOneInventoryItem();
        InventoryItem savedIi = inventoryItemRepository.save(ii);
        inventoryItemRepository.deleteById(UUID.fromString(String.valueOf(savedIi.getId())));
        assertTrue(inventoryItemRepository.findById(UUID.fromString(String.valueOf(savedIi.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneInventoryItem() {
        InventoryItem ii = returnOneInventoryItem();
        InventoryItem savedIi = inventoryItemRepository.save(new InventoryItem());

        savedIi.setIdentifier(ii.getIdentifier());
        savedIi.setStatus(ii.getStatus());
        savedIi.setCategory(ii.getCategory());
        savedIi.setCode(ii.getCode());
        savedIi.setName(ii.getName());
        savedIi.setResponsibleOrganization(ii.getResponsibleOrganization());
        savedIi.setDescription(ii.getDescription());
        savedIi.setInventoryStatus(ii.getInventoryStatus());
        savedIi.setBaseUnit(ii.getBaseUnit());
        savedIi.setNetContent(ii.getNetContent());
        savedIi.setAssociation(ii.getAssociation());
        savedIi.setCharacteristic(ii.getCharacteristic());
        savedIi.setInstance(ii.getInstance());
        savedIi.setProductReference(ii.getProductReference());
    }

    public static InventoryItem returnOneInventoryItem() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-1234-6248-abcd-0000000ano12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<CodeableConcept> categories = new ArrayList<>();
        categories.add(CodeableConcept.builder()
                .text("en")
                .build());

        List<CodeableConcept> codes = new ArrayList<>();
        codes.add(CodeableConcept.builder()
                .text("en")
                .build());

        List<Name> names = new ArrayList<>();
        names.add(Name.builder()
                .name("Name 1")
                .build());

        CodeableConcept rs = CodeableConcept.builder()
                .text("de")
                .build();

        Reference os = Reference.builder()
                .type("Organization")
                .build();

        List<ResponsibleOrganization> ros = new ArrayList<>();
        ros.add(ResponsibleOrganization.builder()
                .role(rs)
                .organization(os)
                .build());

        Description descriptions = Description.builder()
                .description("Description")
                .language("German")
                .build();

        List<CodeableConcept> ins = new ArrayList<>();
        ins.add(CodeableConcept.builder()
                .text("en")
                .build());

        CodeableConcept bu = CodeableConcept.builder()
                .text("en")
                .build();

        Money ncs = Money.builder()
                .currency(Money.CurrencyCode.USD)
                .value(200)
                .build();

        Quantity nums = Quantity.builder()
                .comparator(ComparatorEnum.GREATER)
                .decimal(11)
                .build();

        Ratio ratios = Ratio.builder()
                .denominator(ncs)
                .numerator(nums)
                .build();

        List<Association> associations = new ArrayList<>();
        associations.add(Association.builder()
                .assosciationType(bu)
                .relatedItem(os)
                .quantity(ratios)
                .build());

        List<Characteristic> chars = new ArrayList<>();
        chars.add(Characteristic.builder()
                .code(bu)
                .valueBoolean(true)
                .build());

        Instance instances = Instance.builder()
                .uid("234567")
                .number(2)
                .title("Titellll")
                .build();

        Reference prrefs = Reference.builder()
                .type("location")
                .display("Medication")
                .build();

        InventoryItem ii = InventoryItem.builder()
                .identifier(identifiers)
                .status(InventoryItem.StatusCode.unknown)
                .category(categories)
                .code(codes)
                .name(names)
                .responsibleOrganization(ros)
                .description(descriptions)
                .inventoryStatus(ins)
                .baseUnit(bu)
                .netContent(ncs)
                //.association(associations)
                .characteristic(chars)
                .instance(instances)
                .productReference(prrefs)
                .build();

        return ii;
    }


}

package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.InventoryReportRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InventoryReportRepositoryTest {
    @Autowired
    private InventoryReportRepository inventoryReportRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneInventoryReport(){
        InventoryReport ir = returnOneInventoryReport();
        InventoryReport savedIr = inventoryReportRepository.save(ir);
        Optional<InventoryReport> loadedIrOptional = inventoryReportRepository.findById(savedIr.getId());
        InventoryReport loadedIr = loadedIrOptional.get();

        assertEquals(ir.getText(), loadedIr.getText());
        assertEquals(ir.getStatus(), loadedIr.getStatus());
        assertEquals(ir.getCountType(), loadedIr.getCountType());
        assertEquals(ir.getOperationType(), loadedIr.getOperationType());
        assertEquals(ir.getOperationTypeReason(), loadedIr.getOperationTypeReason());
        assertEquals(ir.getReportedDateTime(), loadedIr.getReportedDateTime());
        assertEquals(ir.getReporter(), loadedIr.getReporter());
        assertEquals(ir.getReportingPeriod(), loadedIr.getReportingPeriod());

        assertTrue(CollectionUtils.isEqualCollection(ir.getIdentifier(), loadedIr.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(ir.getInventoryListing(), loadedIr.getInventoryListing()));
        assertTrue(CollectionUtils.isEqualCollection(ir.getNote(), loadedIr.getNote()));
    }

    @Test
    @Transactional
    public void testDeleteInventoryReport(){
        InventoryReport ir = returnOneInventoryReport();
        InventoryReport savedIr = inventoryReportRepository.save(ir);
        inventoryReportRepository.deleteById(savedIr.getId());
        assertTrue(inventoryReportRepository.findById(savedIr.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateInventoryReport(){
        InventoryReport ir = returnOneInventoryReport();
        InventoryReport savedEmptyIr = inventoryReportRepository.save(new InventoryReport());
        savedEmptyIr.setText(ir.getText());
        savedEmptyIr.setStatus(ir.getStatus());
        savedEmptyIr.setCountType(ir.getCountType());
        savedEmptyIr.setOperationType(ir.getOperationType());
        savedEmptyIr.setOperationTypeReason(ir.getOperationTypeReason());
        savedEmptyIr.setReportedDateTime(ir.getReportedDateTime());
        savedEmptyIr.setReporter(ir.getReporter());
        savedEmptyIr.setReportingPeriod(ir.getReportingPeriod());
        savedEmptyIr.setInventoryListing(ir.getInventoryListing());
        savedEmptyIr.setNote(ir.getNote());;
    }

    public static InventoryReport returnOneInventoryReport() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("20000000-cabd-1654-1679-37000000ac12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        CodeableConcept operationtypes = CodeableConcept.builder()
                .text("addition")
                .build();

        CodeableConcept operationtypereasons = CodeableConcept.builder()
                .text("regularcount")
                .build();

        Reference reporters = Reference.builder()
                .reference("Practitioner/1")
                .display("Practitioners")
                .build();

        Period reportingperiods = Period.builder()
                .start(LocalDateTime.of(2021, 06, 23, 3, 45, 0))
                .end(LocalDateTime.of(2022, 01, 29, 4, 12, 0))
                .build();

        List<InventoryListing> listings = new ArrayList<>();
        listings.add(InventoryListing.builder()
                .countingDateTime(LocalDateTime.of(2020,10,10,12,12,12,00))
                .build());

        List<Annotation> notes = new ArrayList<>();
        notes.add(Annotation.builder()
                .text("Note 1")
                .authorString("By Dr. X")
                .build());


        InventoryReport ir = InventoryReport.builder()
                .identifier(identifiers)
                .status(InventoryReport.StatusCode.draft)
                .countType(InventoryReport.CountCode.snapshot)
                .operationType(operationtypes)
                .operationTypeReason(operationtypereasons)
                .reportedDateTime(LocalDateTime.of(2023,10,10,10,10,10,10))
                .reporter(reporters)
                .reportingPeriod(reportingperiods)
                .inventoryListing(listings)
                .note(notes)
                .build();

        return ir;
    }




}

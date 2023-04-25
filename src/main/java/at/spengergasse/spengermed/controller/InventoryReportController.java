package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.model.InventoryReport;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.InventoryReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/inventoryreport")
public class InventoryReportController {

    @Autowired
    private InventoryReportRepository inventoryReportRepository;

    @GetMapping
    public @ResponseBody
    Iterable<InventoryReport> getAllInventoryReports(){
        return inventoryReportRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryReport> getInventoryReport(@PathVariable UUID id) {
        return inventoryReportRepository
                .findById(id)
                .map(inventoryReport -> ResponseEntity.ok().body(inventoryReport))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<InventoryReport> createInventoryReport(@Valid @RequestBody InventoryReport inventoryReport) {
        inventoryReport.setId(null);
        var saved = inventoryReportRepository.save(inventoryReport);
        return ResponseEntity
                .created(URI.create("/api/inventoryreport/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<InventoryReport> updateInventoryReport(@PathVariable(value = "id") UUID inventoryReportId, @Valid @RequestBody InventoryReport inventoryReportDetails) {
        return inventoryReportRepository.findById(inventoryReportId)
                .map(inventoryReport -> {
                    inventoryReport.setIdentifier(inventoryReportDetails.getIdentifier());
                    inventoryReport.setStatus(inventoryReportDetails.getStatus());
                    inventoryReport.setCountType(inventoryReportDetails.getCountType());
                    inventoryReport.setOperationType(inventoryReportDetails.getOperationType());
                    inventoryReport.setOperationTypeReason(inventoryReportDetails.getOperationTypeReason());
                    inventoryReport.setReportedDateTime(inventoryReportDetails.getReportedDateTime());
                    inventoryReport.setReporter(inventoryReportDetails.getReporter());
                    inventoryReport.setReportingPeriod(inventoryReportDetails.getReportingPeriod());
                    inventoryReport.setInventoryListing(inventoryReportDetails.getInventoryListing());
                    inventoryReport.setNote(inventoryReportDetails.getNote());
                    inventoryReport.setText(inventoryReportDetails.getText());
                    InventoryReport updatedInventoryReport = inventoryReportRepository.save(inventoryReport);
                    return ResponseEntity.ok(updatedInventoryReport);
                }).orElseGet( () -> createInventoryReport(inventoryReportDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InventoryReport> deleteInventoryReport(@PathVariable(value = "id") UUID inventoryReportID) {
        return inventoryReportRepository.findById(inventoryReportID).map(inventoryReport -> {
            inventoryReportRepository.delete(inventoryReport);
            return ResponseEntity.ok().<InventoryReport>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

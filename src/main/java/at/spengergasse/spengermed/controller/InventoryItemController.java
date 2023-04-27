package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.ImagingStudy;
import at.spengergasse.spengermed.model.InventoryItem;
import at.spengergasse.spengermed.repository.ImagingStudyRepository;
import at.spengergasse.spengermed.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/inventoryitem")
@RestController
@CrossOrigin
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @GetMapping
    @ResponseBody
    public Iterable<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItem(@PathVariable UUID id) {
        return inventoryItemRepository
                .findById(id)
                .map(inventoryItem -> ResponseEntity.ok().body(inventoryItem))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<InventoryItem> createInventoryItem(@Valid @RequestBody
                                                                InventoryItem inventoryItem) {
        inventoryItem.setId(null); // ensure to create new names
        var saved = inventoryItemRepository.save(inventoryItem);
        return ResponseEntity
                .created(URI.create("/api/inventoryitem/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(
            @PathVariable("id") UUID inventoryItemId, @Valid @RequestBody InventoryItem inventoryItemDetails) {
        return inventoryItemRepository
                .findById(inventoryItemId)
                .map(inventoryItem -> {
                    inventoryItem.setIdentifier(inventoryItemDetails.getIdentifier());
                    inventoryItem.setStatus(inventoryItemDetails.getStatus());
                    inventoryItem.setCategory(inventoryItemDetails.getCategory());
                    inventoryItem.setCode(inventoryItemDetails.getCode());
                    inventoryItem.setStatus(inventoryItemDetails.getStatus());
                    inventoryItem.setName(inventoryItemDetails.getName());
                    inventoryItem.setResponsibleOrganization(inventoryItemDetails.getResponsibleOrganization());
                    inventoryItem.setDescription(inventoryItemDetails.getDescription());
                    inventoryItem.setInventoryStatus(inventoryItemDetails.getInventoryStatus());
                    inventoryItem.setBaseUnit(inventoryItemDetails.getBaseUnit());
                    inventoryItem.setNetContent(inventoryItemDetails.getNetContent());
                    inventoryItem.setAssociation(inventoryItemDetails.getAssociation());
                    inventoryItem.setCharacteristic(inventoryItemDetails.getCharacteristic());
                    inventoryItem.setInstance(inventoryItemDetails.getInstance());
                    inventoryItem.setProductReference(inventoryItemDetails.getProductReference());

                    InventoryItem updatedInventoryItem = inventoryItemRepository.save(inventoryItem);
                    return ResponseEntity.ok(updatedInventoryItem);
                })
                .orElseGet(() -> createInventoryItem(inventoryItemDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InventoryItem> deleteInventoryItem(@PathVariable("id") UUID inventoryItemId) {
        return inventoryItemRepository
                .findById(inventoryItemId)
                .map(
                        inventoryItem -> {
                            inventoryItemRepository.delete(inventoryItem);
                            return ResponseEntity.ok().<InventoryItem>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }

}

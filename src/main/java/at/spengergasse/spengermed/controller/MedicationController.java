package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Medication;
import at.spengergasse.spengermed.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path="/api/medication")
@RestController
@CrossOrigin
@AllArgsConstructor
public class MedicationController {

    @Autowired
    private MedicationRepository medictionRepository;

    @GetMapping
    @ResponseBody
    public Iterable<Medication> getAllPatients() {
        // This returns a JSON or XML with the users
        return medictionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedication(@PathVariable UUID id) {
        return medictionRepository
                .findById(id)
                .map(medication -> ResponseEntity.ok().body(medication))
                .orElse(ResponseEntity.notFound().build());
    }
    @Transactional
    @PostMapping()
    public ResponseEntity<Medication> createMedication(@Valid @RequestBody Medication medication) {
        medication.setId(null);// ensure to create new names
        var saved = medictionRepository.save(medication);
        return ResponseEntity
                .created(URI.create("/api/medication/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable(value = "id") UUID medicationid, @Valid @RequestBody Medication medicationDetails) {
        return medictionRepository.findById(medicationid)
                .map(medication -> {
                    medication.setId(medicationDetails.getId());
                    medication.setIdentifier(medicationDetails.getIdentifier());
                    medication.setText(medicationDetails.getText());
                    medication.setCode(medicationDetails.getCode());
                    medication.setStatus(medicationDetails.getStatus());
                    medication.setMarketingAuthorizationHolder(medicationDetails.getMarketingAuthorizationHolder());
                    medication.setDoseForm(medicationDetails.getDoseForm());
                    medication.setTotalVolume(medicationDetails.getTotalVolume());
                    medication.setIngredient(medicationDetails.getIngredient());
                    medication.setBatch(medicationDetails.getBatch());
                    medication.setDefinition(medicationDetails.getDefinition());
                    Medication updatedMedication = medictionRepository.save(medication);
                    return ResponseEntity.ok(updatedMedication);
        }).orElse(createMedication(medicationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medication> deleteMedication(@PathVariable(value = "id") UUID medicationid) {
        return medictionRepository.findById(medicationid).map(medication -> {
            medictionRepository.delete(medication);
            return ResponseEntity.ok().<Medication>build();
        }).orElse(ResponseEntity.notFound().build());
    }


}

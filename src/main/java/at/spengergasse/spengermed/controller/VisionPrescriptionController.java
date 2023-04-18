package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.model.VisionPrescription;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.IVisionPrescriptionRespository;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/visionprescription")
public class VisionPrescriptionController {
    @Autowired
    private IVisionPrescriptionRespository visionPrescriptionRespository;

    @GetMapping
    public @ResponseBody
    Iterable<VisionPrescription> getAllVisionPrescriptions(){
        return visionPrescriptionRespository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisionPrescription> getVisionPrescription(@PathVariable UUID id) {
        return visionPrescriptionRespository
                .findById(id)
                .map(visionPrescription -> ResponseEntity.ok().body(visionPrescription))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional  //Datenbankzugriffe als Transaktion
    @PostMapping()
    public ResponseEntity<VisionPrescription> createVisionPrescription(@Valid @RequestBody
                                                     VisionPrescription vp) {
        vp.setId(null); // ensure to create new names
        var saved = visionPrescriptionRespository.save(vp);
        return ResponseEntity
                .created(URI.create("/api/visionprescription/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<VisionPrescription> updateVisionPrescription(@PathVariable(value = "id") UUID vpId, @Valid @RequestBody VisionPrescription vpDetails) {
        return visionPrescriptionRespository.findById(vpId)
                .map(visionPrescription -> {
                    visionPrescription.setIdentifier(vpDetails.getIdentifier());
                    visionPrescription.setText(vpDetails.getText());
                    visionPrescription.setStatus(vpDetails.getStatus());
                    visionPrescription.setPatient(vpDetails.getPatient());
                    visionPrescription.setDateWritten(vpDetails.getDateWritten());
                    visionPrescription.setLenseSpecification(vpDetails.getLenseSpecification());
                    VisionPrescription updatedVp = visionPrescriptionRespository.save(visionPrescription);
                    return ResponseEntity.ok(updatedVp);
                }).orElseGet( () -> createVisionPrescription(vpDetails));
    }

    @DeleteMapping("/{id}")     //Braucht man auch, um darauf zugreifen zu können bspw. beim testen
    public ResponseEntity<VisionPrescription> deleteVisionPrescription(@PathVariable(value = "id") UUID vpId) {
        return visionPrescriptionRespository.findById(vpId).map(vp -> {
            visionPrescriptionRespository.delete(vp);
            return ResponseEntity.ok().<VisionPrescription>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Encounter;
import at.spengergasse.spengermed.repository.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

//REST-Schnittstelle, Web-Schnittstelle
@RestController
@CrossOrigin
@RequestMapping(path = "/api/encounter")
public class EncounterController {

    @Autowired
    private EncounterRepository encounterRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Encounter> getAllEncounters(){
        return encounterRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounter(@PathVariable String id) {
        return encounterRepository
                .findById(UUID.fromString(id))
                .map(encounter -> ResponseEntity.ok().body(encounter))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Encounter> createEncounter(@Valid @RequestBody Encounter encounter) {
        encounter.setId(null);
        var saved = encounterRepository.save(encounter);
        return ResponseEntity
                .created(URI.create("/api/encounter/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Encounter> updateEncounter(@PathVariable(value = "id") String encounterId, @Valid @RequestBody Encounter encounterDetails) {
        return encounterRepository.findById(UUID.fromString(encounterId))
                .map(encounter -> {
                    encounter.setIdentifier(encounterDetails.getIdentifier());
                    encounter.setStatus(encounterDetails.getStatus());
                    encounter.setStatusHistory(encounterDetails.getStatusHistory());
                    encounter.setType(encounterDetails.getType());
                    encounter.setSubject(encounterDetails.getSubject());
                    encounter.setEpisodeOfCare(encounterDetails.getEpisodeOfCare());
                    encounter.setParticipant(encounterDetails.getParticipant());
                    encounter.setAppointment(encounterDetails.getAppointment());
                    encounter.setPeriod(encounterDetails.getPeriod());
                    encounter.setReasonReference(encounterDetails.getReasonReference());
                    encounter.setDiagnosis(encounterDetails.getDiagnosis());
                    encounter.setPartOf(encounterDetails.getPartOf());
                    encounter.setText(encounterDetails.getText());
                    Encounter updatedEncounter = encounterRepository.save(encounter);
                    return ResponseEntity.ok(updatedEncounter);
                }).orElseGet( () -> createEncounter(encounterDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Encounter> deleteEncounter(@PathVariable(value = "id") String encounterId) {
             return encounterRepository.findById(UUID.fromString(encounterId)).map(encounter -> {
            encounterRepository.delete(encounter);
            return ResponseEntity.ok().<Encounter>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

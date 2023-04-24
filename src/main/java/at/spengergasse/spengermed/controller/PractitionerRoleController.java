package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.PractitionerRole;
import at.spengergasse.spengermed.repository.PractitionerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/practitionerrole")
public class PractitionerRoleController {
    @Autowired
    private PractitionerRoleRepository practitionerRoleRepository;

    @GetMapping
    public @ResponseBody
    Iterable<PractitionerRole> getAllPractitionerRoles(){
        return practitionerRoleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PractitionerRole> getPractitionerRole(@PathVariable UUID id) {
        return practitionerRoleRepository
                .findById(id)
                .map(practitionerRole -> ResponseEntity.ok().body(practitionerRole))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<PractitionerRole> createPractitionerRole(@Valid @RequestBody PractitionerRole practitionerRole) {
        practitionerRole.setId(null);
        var saved = practitionerRoleRepository.save(practitionerRole);
        return ResponseEntity
                .created(URI.create("/api/practitionerrole/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<PractitionerRole> updatePractitionerRole(@PathVariable(value = "id") UUID practitionerRoleId, @Valid @RequestBody PractitionerRole practitionerRoleDetails) {
        return practitionerRoleRepository.findById(practitionerRoleId)
                .map(practitionerRole -> {
                    practitionerRole.setIdentifier(practitionerRoleDetails.getIdentifier());
                    practitionerRole.setActive(practitionerRoleDetails.getActive());
                    practitionerRole.setText(practitionerRoleDetails.getText());
                    practitionerRole.setPeriod(practitionerRoleDetails.getPeriod());
                    practitionerRole.setPractitioner(practitionerRoleDetails.getPractitioner());
                    practitionerRole.setSpecialty(practitionerRoleDetails.getSpecialty());
                    practitionerRole.setAvailableTime(practitionerRoleDetails.getAvailableTime());

                    PractitionerRole updatedPractitionerRole = practitionerRoleRepository.save(practitionerRole);
                    return ResponseEntity.ok(updatedPractitionerRole);
                }).orElseGet( () -> createPractitionerRole(practitionerRoleDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PractitionerRole> deletePractitionerRole(@PathVariable(value = "id") UUID practitionerRoleID) {
        return practitionerRoleRepository.findById(practitionerRoleID).map(practitionerRole -> {
            practitionerRoleRepository.delete(practitionerRole);
            return ResponseEntity.ok().<PractitionerRole>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

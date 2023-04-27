package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Immunization;
import at.spengergasse.spengermed.repository.ImmunizationRepository;
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
@RequestMapping(path = "/api/immunization")
public class ImmunizationController {

    @Autowired
    private ImmunizationRepository immunizationRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Immunization> getAllImmunizations(){
        return immunizationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Immunization> getImmunization(@PathVariable UUID id) {
        return immunizationRepository
                .findById(id)
                .map(immunization -> ResponseEntity.ok().body(immunization))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Immunization> createImmunization(@Valid @RequestBody Immunization immunization) {
        immunization.setId(null);
        var saved = immunizationRepository.save(immunization);
        return ResponseEntity
                .created(URI.create("/api/immunization/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Immunization> updateImmunization(@PathVariable(value = "id") UUID immunizationId, @Valid @RequestBody Immunization immunizationDetails) {
        return immunizationRepository.findById(immunizationId)
                .map(immunization -> {
                    immunization.setText(immunizationDetails.getText());
                    immunization.setIdentifier(immunizationDetails.getIdentifier());
                    immunization.setBasedOn(immunizationDetails.getBasedOn());
                    immunization.setStatus(immunizationDetails.getStatus());
                    immunization.setStatusReason(immunizationDetails.getStatusReason());
                    immunization.setVaccineCode(immunizationDetails.getVaccineCode());
                    immunization.setAdministeredProduct(immunizationDetails.getAdministeredProduct());
                    immunization.setManufacturer(immunizationDetails.getManufacturer());
                    immunization.setLotNumber(immunizationDetails.getLotNumber());
                    immunization.setExpirationDate(immunizationDetails.getExpirationDate());
                    immunization.setPatient(immunizationDetails.getPatient());
                    immunization.setEncounter(immunizationDetails.getEncounter());
                    immunization.setSupportinginformation(immunizationDetails.getSupportinginformation());
                    immunization.setOccurrenceDateTime(immunizationDetails.getOccurrenceDateTime());
                    immunization.setOccurrenceString(immunizationDetails.getOccurrenceString());
                    immunization.setPrimarySource(immunizationDetails.getPrimarySource());
                    immunization.setInformationSource(immunizationDetails.getInformationSource());
                    immunization.setLocation(immunizationDetails.getLocation());
                    immunization.setSite(immunizationDetails.getSite());
                    immunization.setRoute(immunizationDetails.getRoute());
                    immunization.setDoseQuantity(immunizationDetails.getDoseQuantity());
                    immunization.setPerformer(immunizationDetails.getPerformer());
                    immunization.setNote(immunizationDetails.getNote());
                    immunization.setReason(immunizationDetails.getReason());
                    immunization.setIsSubpotent(immunizationDetails.getIsSubpotent());
                    immunization.setSubpotentReason(immunizationDetails.getSubpotentReason());
                    immunization.setProgramEligibility(immunizationDetails.getProgramEligibility());
                    immunization.setFundingSource(immunizationDetails.getFundingSource());
                    immunization.setReaction(immunizationDetails.getReaction());
                    immunization.setProtocolApplied(immunizationDetails.getProtocolApplied());
                    Immunization updatedImmunization = immunizationRepository.save(immunization);
                    return ResponseEntity.ok(updatedImmunization);
                }).orElseGet( () -> createImmunization(immunizationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Immunization> deleteImmunization(@PathVariable(value = "id") UUID immunizationId) {
        return immunizationRepository.findById(immunizationId).map(immunization -> {
            immunizationRepository.delete(immunization);
            return ResponseEntity.ok().<Immunization>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}


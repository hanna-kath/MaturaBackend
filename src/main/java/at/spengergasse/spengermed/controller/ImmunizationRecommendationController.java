package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.ImmunizationRecommendation;
import at.spengergasse.spengermed.repository.ImmunizationRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/immunizationrecommendation")
public class ImmunizationRecommendationController {

    @Autowired
    private ImmunizationRecommendationRepository immunizationRecommendationRepository;

    @GetMapping
    public @ResponseBody
    Iterable<ImmunizationRecommendation> getAllImmunizationRecommendations(){
        return immunizationRecommendationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmunizationRecommendation> getImmunizationRecommendation(@PathVariable UUID id) {
        return immunizationRecommendationRepository
                .findById(id)
                .map(immunizationRecommendation -> ResponseEntity.ok().body(immunizationRecommendation))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ImmunizationRecommendation> createImmunizationRecommendation(@Valid @RequestBody ImmunizationRecommendation immunizationRecommendation) {
        immunizationRecommendation.setId(null);
        var saved = immunizationRecommendationRepository.save(immunizationRecommendation);
        return ResponseEntity
                .created(URI.create("/api/immunizationrecommendation/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ImmunizationRecommendation> updateImmunizationRecommendation(@PathVariable(value = "id") UUID immunizationRecommendationId, @Valid @RequestBody ImmunizationRecommendation immunizationRecommendationDetails) {
        return immunizationRecommendationRepository.findById(immunizationRecommendationId)
                .map(immunizationRecommendation -> {
                    immunizationRecommendation.setIdentifier(immunizationRecommendationDetails.getIdentifier());
                    immunizationRecommendation.setPatient(immunizationRecommendationDetails.getPatient());
                    immunizationRecommendation.setDate(immunizationRecommendationDetails.getDate());
                    immunizationRecommendation.setAuthority(immunizationRecommendationDetails.getAuthority());
                    immunizationRecommendation.setRecommendation(immunizationRecommendationDetails.getRecommendation());
                    immunizationRecommendation.setText(immunizationRecommendationDetails.getText());
                    ImmunizationRecommendation updatedImmunizationRecommendation = immunizationRecommendationRepository.save(immunizationRecommendation);
                    return ResponseEntity.ok(updatedImmunizationRecommendation);
                }).orElseGet( () -> createImmunizationRecommendation(immunizationRecommendationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImmunizationRecommendation> deleteImmunizationEvaluation(@PathVariable(value = "id") UUID immunizationRecommendationID) {
        return immunizationRecommendationRepository.findById(immunizationRecommendationID).map(immunizationRecommendation -> {
            immunizationRecommendationRepository.delete(immunizationRecommendation);
            return ResponseEntity.ok().<ImmunizationRecommendation>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Consent;
import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.repository.ConsentRepository;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/immunizationevaluation")
public class ImmunizationEvaluationController {
    @Autowired
    private ImmunizationEvaluationRepository immunizationEvaluationRepository;

    @GetMapping
    public @ResponseBody
    Iterable<ImmunizationEvaluation> getAllImmunizationEvaluations(){
        return immunizationEvaluationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmunizationEvaluation> getImmunizationEvaluation(@PathVariable UUID id) {
        return immunizationEvaluationRepository
                .findById(id)
                .map(immunizationEvaluation -> ResponseEntity.ok().body(immunizationEvaluation))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ImmunizationEvaluation> createImmunizationEvaluation(@Valid @RequestBody ImmunizationEvaluation immunizationEvaluation) {
        immunizationEvaluation.setId(null);
        var saved = immunizationEvaluationRepository.save(immunizationEvaluation);
        return ResponseEntity
                .created(URI.create("/api/immunizationevaluation/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ImmunizationEvaluation> updateImmunizationEvaluation(@PathVariable(value = "id") UUID immunizationEvaluationId, @Valid @RequestBody ImmunizationEvaluation immunizationEvaluationDetails) {
        return immunizationEvaluationRepository.findById(immunizationEvaluationId)
                .map(immunizationEvaluation -> {
                    immunizationEvaluation.setIdentifier(immunizationEvaluationDetails.getIdentifier());
                    immunizationEvaluation.setStatus(immunizationEvaluationDetails.getStatus());
                    immunizationEvaluation.setPatient(immunizationEvaluationDetails.getPatient());
                    immunizationEvaluation.setDate(immunizationEvaluationDetails.getDate());
                    immunizationEvaluation.setAuthority(immunizationEvaluationDetails.getAuthority());
                    immunizationEvaluation.setTargetDisease(immunizationEvaluationDetails.getTargetDisease());
                    immunizationEvaluation.setImmunizationEvent(immunizationEvaluationDetails.getImmunizationEvent());
                    immunizationEvaluation.setDoseStatus(immunizationEvaluationDetails.getDoseStatus());
                    immunizationEvaluation.setDoseStatusReason(immunizationEvaluationDetails.getDoseStatusReason());
                    immunizationEvaluation.setDescription(immunizationEvaluationDetails.getDescription());
                    immunizationEvaluation.setSeries(immunizationEvaluationDetails.getSeries());
                    immunizationEvaluation.setDoseNumber(immunizationEvaluationDetails.getDoseNumber());
                    immunizationEvaluation.setSeriesDoses(immunizationEvaluationDetails.getSeriesDoses());
                    immunizationEvaluation.setText(immunizationEvaluationDetails.getText());
                    ImmunizationEvaluation updatedImmunizationEvaluation = immunizationEvaluationRepository.save(immunizationEvaluation);
                    return ResponseEntity.ok(updatedImmunizationEvaluation);
                }).orElseGet( () -> createImmunizationEvaluation(immunizationEvaluationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImmunizationEvaluation> deleteImmunizationEvaluation(@PathVariable(value = "id") UUID immunizationEvaluationID) {
        return immunizationEvaluationRepository.findById(immunizationEvaluationID).map(immunizationEvaluation -> {
            immunizationEvaluationRepository.delete(immunizationEvaluation);
            return ResponseEntity.ok().<ImmunizationEvaluation>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

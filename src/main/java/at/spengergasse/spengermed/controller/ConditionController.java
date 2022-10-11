package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.repository.ConditionRepository;
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
@RequestMapping(path = "/api/condition")
public class ConditionController {

    @Autowired
    private ConditionRepository conditionRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Condition> getAllConditions(){
        return conditionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Condition> getCondition(@PathVariable UUID id) {
        return conditionRepository
                .findById(id)
                .map(practitioner -> ResponseEntity.ok().body(practitioner))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional  //Datenbankzugriffe als Transaktion
    @PostMapping()
    public ResponseEntity<Condition> createCondition(@Valid @RequestBody
                                                           Condition condition) {
        condition.setId(null); // ensure to create new names
        var saved = conditionRepository.save(condition);
        return ResponseEntity
                .created(URI.create("/api/condition/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Condition> updateCondition(@PathVariable(value = "id") UUID conditionId, @Valid @RequestBody Condition conditionDetails) {
        return conditionRepository.findById(conditionId)
                .map(condition -> {
                    condition.setIdentifier(conditionDetails.getIdentifier());
                    condition.setText(conditionDetails.getText());
                    condition.setClinicalStatus(conditionDetails.getClinicalStatus());
                    condition.setVerificationStatus(conditionDetails.getVerificationStatus());
                    condition.setCategory(conditionDetails.getCategory());
                    condition.setSeverity(conditionDetails.getSeverity());
                    condition.setCode(conditionDetails.getCode());
                    condition.setBodySite(conditionDetails.getBodySite());
                    condition.setSubject(conditionDetails.getSubject());
                    condition.setEncounter(conditionDetails.getEncounter());
                    Condition updatedCondition = conditionRepository.save(condition);
                    return ResponseEntity.ok(updatedCondition);
                }).orElseGet( () -> createCondition(conditionDetails));
    }

    @DeleteMapping("/{id}")     //Braucht man auch, um darauf zugreifen zu können bspw. beim testen
    public ResponseEntity<Condition> deleteCondition(@PathVariable(value = "id") UUID conditionId) {
        return conditionRepository.findById(conditionId).map(condition -> {
            conditionRepository.delete(condition);
            return ResponseEntity.ok().<Condition>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

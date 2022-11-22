package at.spengergasse.spengermed.controller;


import at.spengergasse.spengermed.model.RiskAssessment;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/riskassessment")
@RestController
@CrossOrigin
public class RiskAssessmentController {

    @Autowired
    private RiskAssessmentRepository riskAssessmentRepository;

    @GetMapping
    @ResponseBody
    public Iterable<RiskAssessment> getAllRiskAssessments() {
        // This returns a JSON or XML with the users
        return riskAssessmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskAssessment> getRiskAssessment(@PathVariable UUID id) {
        return riskAssessmentRepository
                .findById(id)
                .map(riskAssessment -> ResponseEntity.ok().body(riskAssessment))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional  //Datenbankzugriffe als Transaktion
    @PostMapping()
    public ResponseEntity<RiskAssessment> createRiskAssessment(@Valid @RequestBody
                                                           RiskAssessment riskAssessment) {
        riskAssessment.setId(null); // ensure to create new names
        var saved = riskAssessmentRepository.save(riskAssessment);
        return ResponseEntity
                .created(URI.create("/api/riskassessment/" + saved.getId()))
                .body(saved);
    }

    // Update a Patient
    @PutMapping("/{id}")
    public ResponseEntity<RiskAssessment> updateRiskAssessment(
            @PathVariable("id") UUID riskAssessmentId, @Valid @RequestBody RiskAssessment riskAssessmentDetails) {
        return riskAssessmentRepository
                .findById(riskAssessmentId)
                .map(
                        riskAssessment -> {
                            riskAssessment.setParent(riskAssessmentDetails.getParent());
                            riskAssessment.setStatus(riskAssessmentDetails.getStatus());
                            riskAssessment.setIdentifier(riskAssessmentDetails.getIdentifier());
                            riskAssessment.setPrediction(riskAssessmentDetails.getPrediction());
                            riskAssessment.setText(riskAssessmentDetails.getText());

                            RiskAssessment updatedRiskAssessment = riskAssessmentRepository.save(riskAssessment);
                            return ResponseEntity.ok(updatedRiskAssessment);
                        })
                .orElseGet(() -> createRiskAssessment(riskAssessmentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RiskAssessment> deleteRiskAssessment(@PathVariable("id") UUID riskAssessmentId) {
        return riskAssessmentRepository
                .findById(riskAssessmentId)
                .map(
                        riskAssessment -> {
                            riskAssessmentRepository.delete(riskAssessment);
                            return ResponseEntity.ok().<RiskAssessment>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }

}

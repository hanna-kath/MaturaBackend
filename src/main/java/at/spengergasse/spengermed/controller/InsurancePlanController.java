package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.InsurancePlan;
import at.spengergasse.spengermed.repository.InsurancePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/insurancePlan")
public class InsurancePlanController {

    @Autowired
    private InsurancePlanRepository insurancePlanRepository;

    @GetMapping
    public @ResponseBody
    Iterable<InsurancePlan> getAllInsurancePlans(){
        return insurancePlanRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsurancePlan> getInsurancePlan(@PathVariable UUID id) {
        return insurancePlanRepository
                .findById(id)
                .map(insurancePlan -> ResponseEntity.ok().body(insurancePlan))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<InsurancePlan> createInsurancePlan(@Valid @RequestBody
                                                     InsurancePlan insurancePlan) {
        insurancePlan.setId(null);
        var saved = insurancePlanRepository.save(insurancePlan);
        return ResponseEntity
                .created(URI.create("/api/insurancePlan/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<InsurancePlan> updateInsurancePlan(@PathVariable(value = "id") UUID insurancePlanId, @Valid @RequestBody InsurancePlan insurancePlanDetails) {
        return insurancePlanRepository.findById(insurancePlanId)
                .map(insurancePlan -> {
                    insurancePlan.setIdentifier(insurancePlanDetails.getIdentifier());
                    insurancePlan.setStatus(insurancePlanDetails.getStatus());
                    insurancePlan.setType(insurancePlanDetails.getType());
                    insurancePlan.setName(insurancePlanDetails.getName());
                    insurancePlan.setAlias(insurancePlanDetails.getAlias());
                    insurancePlan.setPeriod(insurancePlanDetails.getPeriod());
                    insurancePlan.setOwnedBy(insurancePlanDetails.getOwnedBy());
                    insurancePlan.setAdministeredBy(insurancePlanDetails.getAdministeredBy());
                    insurancePlan.setCoverageArea(insurancePlanDetails.getCoverageArea());
                    insurancePlan.setContact(insurancePlanDetails.getContact());
                    insurancePlan.setEndpoint(insurancePlanDetails.getEndpoint());
                    insurancePlan.setNetwork(insurancePlanDetails.getNetwork());
                    insurancePlan.setCoverage(insurancePlanDetails.getCoverage());
                    insurancePlan.setPlan(insurancePlanDetails.getPlan());

                    InsurancePlan updatedInsurancePlan = insurancePlanRepository.save(insurancePlan);
                    return ResponseEntity.ok(updatedInsurancePlan);
                }).orElseGet( () -> createInsurancePlan(insurancePlanDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InsurancePlan> deleteInsurancePlan(@PathVariable(value = "id") UUID insurancePlanId) {
        return insurancePlanRepository.findById(insurancePlanId).map(insurancePlan -> {
            insurancePlanRepository.delete(insurancePlan);
            return ResponseEntity.ok().<InsurancePlan>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

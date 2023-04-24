package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Consent;
import at.spengergasse.spengermed.repository.ConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/consent")
public class ConsentController {

    @Autowired
    private ConsentRepository consentRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Consent> getAllConsents(){
        return consentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consent> getConsent(@PathVariable UUID id) {
        return consentRepository
                .findById(id)
                .map(consent -> ResponseEntity.ok().body(consent))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Consent> createConsent(@Valid @RequestBody Consent consent) {
        consent.setId(null);
        var saved = consentRepository.save(consent);
        return ResponseEntity
                .created(URI.create("/api/consent/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Consent> updateConsent(@PathVariable(value = "id") UUID consentId, @Valid @RequestBody Consent consentDetails) {
        return consentRepository.findById(consentId)
                .map(consent -> {
                    consent.setIdentifier(consentDetails.getIdentifier());
                    consent.setStatus(consentDetails.getStatus());
                    consent.setSourceAttachment(consentDetails.getSourceAttachment());
                    consent.setSourceReference(consentDetails.getSourceReference());
                    consent.setPolicy(consentDetails.getPolicy());
                    consent.setText(consentDetails.getText());
                    Consent updatedConsent = consentRepository.save(consent);
                    return ResponseEntity.ok(updatedConsent);
                }).orElseGet( () -> createConsent(consentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consent> deleteConsent(@PathVariable(value = "id") UUID consentID) {
        return consentRepository.findById(consentID).map(consent -> {
            consentRepository.delete(consent);
            return ResponseEntity.ok().<Consent>build();
        }).orElse(ResponseEntity.notFound().build());
    }





}

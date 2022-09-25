package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Organization;
import at.spengergasse.spengermed.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Organization> getAllOrganizations(){
        return organizationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable String id) {
        return organizationRepository
                .findById(UUID.fromString(id))
                .map(organization -> ResponseEntity.ok().body(organization))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization) {
        organization.setId(null);
        var saved = organizationRepository.save(organization);
        return ResponseEntity
                .created(URI.create("/api/organization/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable(value = "id") String organizationId, @Valid @RequestBody Organization organizationDetails) {
        return organizationRepository.findById(UUID.fromString(organizationId))
                .map(organization -> {
                    organization.setIdentifier(organizationDetails.getIdentifier());
                    organization.setActive(organizationDetails.getActive());
                    organization.setAddress(organizationDetails.getAddress());
                    organization.setName(organizationDetails.getName());
                    organization.setContact(organizationDetails.getContact());
                    organization.setText(organizationDetails.getText());

                    Organization updatedOrganization = organizationRepository.save(organization);
                    return ResponseEntity.ok(updatedOrganization);
                }).orElseGet( () -> createOrganization(organizationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Organization> deleteOrganization(@PathVariable(value = "id") String organizationId) {
        return organizationRepository.findById(UUID.fromString(organizationId)).map(organization -> {
            organizationRepository.delete(organization);
            return ResponseEntity.ok().<Organization>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.ImplementationGuide;
import at.spengergasse.spengermed.repository.ImplementationGuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/implementationguide")
@RestController
@CrossOrigin
public class ImplementationGuideController {

    @Autowired
    private ImplementationGuideRepository igRepository;

    @GetMapping
    @ResponseBody
    public Iterable<ImplementationGuide> getAllImplementationGuides() {
        return igRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImplementationGuide> getImplementationGuide(@PathVariable UUID id) {
        return igRepository
                .findById(id)
                .map(ig -> ResponseEntity.ok().body(ig))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ImplementationGuide> createImplementationGuide(@Valid @RequestBody
                                                               ImplementationGuide ig) {
        ig.setId(null);
        var saved = igRepository.save(ig);
        return ResponseEntity
                .created(URI.create("/api/implementationguide/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImplementationGuide> updateImplementationGuide(
            @PathVariable("id") UUID igId, @Valid @RequestBody ImplementationGuide igDetails) {
        return igRepository
                .findById(igId)
                .map(
                        ig -> {
                            ig.setText(igDetails.getText());
                            ig.setUrl(igDetails.getUrl());
                            ig.setIdentifier(igDetails.getIdentifier());
                            ig.setVersion(igDetails.getVersion());
                            ig.setVersionAlgorithmString(igDetails.getVersionAlgorithmString());
                            ig.setVersionAlgorithmCoding(igDetails.getVersionAlgorithmCoding());
                            ig.setName(igDetails.getName());
                            ig.setTitle(igDetails.getTitle());
                            ig.setStatus(igDetails.getStatus());
                            ig.setExperimental(igDetails.getExperimental());
                            ig.setDate(igDetails.getDate());
                            ig.setPublisher(igDetails.getPublisher());
                            ig.setContact(igDetails.getContact());
                            ig.setDescription(igDetails.getDescription());
                            ig.setUseContext(igDetails.getUseContext());
                            ig.setJurisdication(igDetails.getJurisdication());
                            ig.setPurpose(igDetails.getPurpose());
                            ig.setCopyright(igDetails.getCopyright());
                            ig.setCopyrightLabel(igDetails.getCopyrightLabel());
                            ig.setPackageId(igDetails.getPackageId());
                            ig.setLicense(igDetails.getLicense());
                            ig.setFhirVersion(igDetails.getFhirVersion());
                            ig.setDependsOn(igDetails.getDependsOn());
                            ig.setGlobal(igDetails.getGlobal());
                            ig.setDefinition(igDetails.getDefinition());
                            ig.setManifest(igDetails.getManifest());

                            ImplementationGuide updatedIg = igRepository.save(ig);
                            return ResponseEntity.ok(updatedIg);
                        })
                .orElseGet(() -> createImplementationGuide(igDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImplementationGuide> deleteImplementationGuide(@PathVariable("id") UUID igId) {
        return igRepository
                .findById(igId)
                .map(
                        group -> {
                            igRepository.delete(group);
                            return ResponseEntity.ok().<ImplementationGuide>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }
}

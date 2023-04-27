package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.ImagingStudy;
import at.spengergasse.spengermed.repository.ImagingStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/imagingstudy")
@RestController
@CrossOrigin
public class ImagingStudyController {
    @Autowired
    private ImagingStudyRepository imagingStudyRepository;

    @GetMapping
    @ResponseBody
    public Iterable<ImagingStudy> getAllImagingStudies() {
        return imagingStudyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagingStudy> getImagingStudy(@PathVariable UUID id) {
        return imagingStudyRepository
                .findById(id)
                .map(imagingStudy -> ResponseEntity.ok().body(imagingStudy))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ImagingStudy> createImagingStudy(@Valid @RequestBody
                                             ImagingStudy imagingStudy) {
        imagingStudy.setId(null); // ensure to create new names
        var saved = imagingStudyRepository.save(imagingStudy);
        return ResponseEntity
                .created(URI.create("/api/imaginstudy/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagingStudy> updateImagingStudy(
            @PathVariable("id") UUID imagingStudyId, @Valid @RequestBody ImagingStudy imagingStudyDetails) {
        return imagingStudyRepository
                .findById(imagingStudyId)
                .map(imagingStudy -> {
                        imagingStudy.setIdentifier(imagingStudyDetails.getIdentifier());
                        imagingStudy.setEncounter(imagingStudyDetails.getEncounter());
                        imagingStudy.setEndpoint(imagingStudyDetails.getEndpoint());
                        imagingStudy.setSubject(imagingStudyDetails.getSubject());
                        imagingStudy.setStatus(imagingStudyDetails.getStatus());
                        imagingStudy.setModality(imagingStudyDetails.getModality());
                        imagingStudy.setStarted(imagingStudyDetails.getStarted());
                        imagingStudy.setBasedOn(imagingStudyDetails.getBasedOn());
                        imagingStudy.setPartOf(imagingStudyDetails.getPartOf());
                        imagingStudy.setReferrer(imagingStudyDetails.getReferrer());
                        imagingStudy.setNumberofSeries(imagingStudyDetails.getNumberofSeries());
                        imagingStudy.setNumberofinstances(imagingStudyDetails.getNumberofinstances());
                        imagingStudy.setProcedure(imagingStudyDetails.getProcedure());
                        imagingStudy.setLocation(imagingStudyDetails.getLocation());
                        imagingStudy.setReason(imagingStudyDetails.getReason());
                        imagingStudy.setNote(imagingStudyDetails.getNote());
                        imagingStudy.setDescription(imagingStudyDetails.getDescription());
                        imagingStudy.setSeries(imagingStudyDetails.getSeries());

                            ImagingStudy updatedimagingStudy = imagingStudyRepository.save(imagingStudy);
                            return ResponseEntity.ok(updatedimagingStudy);
                        })
                .orElseGet(() -> createImagingStudy(imagingStudyDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImagingStudy> deleteImagingStudy(@PathVariable("id") UUID imagingStudyId) {
        return imagingStudyRepository
                .findById(imagingStudyId)
                .map(
                        imagingStudy -> {
                            imagingStudyRepository.delete(imagingStudy);
                            return ResponseEntity.ok().<ImagingStudy>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }
}

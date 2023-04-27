package at.spengergasse.spengermed.controller;
import at.spengergasse.spengermed.model.ImagingSelection;
import at.spengergasse.spengermed.repository.ImagingSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/imagingselection")
public class ImagingSelectionController {

    @Autowired
    private ImagingSelectionRepository imagingselectionRepository;

    @GetMapping
    public @ResponseBody
    Iterable<ImagingSelection> getAllImagingselections(){
        return imagingselectionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagingSelection> getImagingselection(@PathVariable UUID id) {
        return imagingselectionRepository
                .findById(id)
                .map(imagingselection -> ResponseEntity.ok().body(imagingselection))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<ImagingSelection> createImagingselection(@Valid @RequestBody ImagingSelection imagingselection) {
        imagingselection.setId(null);
        var saved = imagingselectionRepository.save(imagingselection);
        return ResponseEntity
                .created(URI.create("/api/imagingselection/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ImagingSelection> updateImagingselection(@PathVariable(value = "id") UUID imagingselectionId, @Valid @RequestBody ImagingSelection imagingselectionDetails) {
        return imagingselectionRepository.findById(imagingselectionId)
                .map(imagingselection -> {
                    imagingselection.setIdentifier(imagingselectionDetails.getIdentifier());
                    imagingselection.setStatus(imagingselectionDetails.getStatus());
                    imagingselection.setSubject(imagingselectionDetails.getSubject());
                    imagingselection.setIssued(imagingselectionDetails.getIssued());
                    imagingselection.setPerformer(imagingselectionDetails.getPerformer());
                    imagingselection.setBasedon(imagingselectionDetails.getBasedon());
                    imagingselection.setCategory(imagingselectionDetails.getCategory());
                    imagingselection.setCode(imagingselectionDetails.getCode());
                    imagingselection.setStudyuid(imagingselectionDetails.getStudyuid());
                    imagingselection.setDerivedfrom(imagingselectionDetails.getDerivedfrom());
                    imagingselection.setEndpoint(imagingselectionDetails.getEndpoint());
                    imagingselection.setSeriesuid(imagingselectionDetails.getSeriesuid());
                    imagingselection.setSeriesnumber(imagingselectionDetails.getSeriesnumber());
                    imagingselection.setFrameofreferenceuid(imagingselectionDetails.getFrameofreferenceuid());
                    imagingselection.setBodysite(imagingselectionDetails.getBodysite());
                    imagingselection.setFocus(imagingselectionDetails.getFocus());
                    imagingselection.setInstance(imagingselectionDetails.getInstance());

                    ImagingSelection updatedImagingselection = imagingselectionRepository.save(imagingselection);
                    return ResponseEntity.ok(updatedImagingselection);
                }).orElseGet( () -> createImagingselection(imagingselectionDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImagingSelection> deleteImagingselection(@PathVariable(value = "id") UUID imagingselectionId) {
        return imagingselectionRepository.findById(imagingselectionId).map(imagingselection -> {
            imagingselectionRepository.delete(imagingselection);
            return ResponseEntity.ok().<ImagingSelection>build();
        }).orElse(ResponseEntity.notFound().build());
    }


}

package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;


//Pro Resosurce eine Datei, ein Controller, mit Adresse und GET; POST, PUT und DELETE; in der Methode steht, was damit passieren
//soll (aktualisieren, sehen, löschen, erstellen)
//Um von "außen" über http darauf zugreifen zu können
//REST-Schnittstelle, Web-Schnittstelle
@RestController
@CrossOrigin                                    //aktiviert die ursprungsübergreifende Ressourcenfreigabe ("UrsprungsURL" localhost: 8080)
@RequestMapping(path = "/api/practitioner")     //Webanforderungen Spring Controller-Methoden zuzuordnen
public class PractitionerController {

    @Autowired  //teilt Spring mit, wo es mittels Injection Objekte in andere Klassen einfügen soll
    private PractitionerRepository practitionerRepository;

    @GetMapping     //über http
    public @ResponseBody    //Annotation, die einen Methodenrückgabewert an den Text der Webantwort bindet.
    Iterable<Practitioner> getAllPractitioners(){
        // This returns a JSON or XML with the users
        return practitionerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Practitioner> getPractitioner(@PathVariable UUID id) {
        return practitionerRepository
                .findById(id)
                .map(practitioner -> ResponseEntity.ok().body(practitioner))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional  //Datenbankzugriffe als Transaktion
    @PostMapping()
    public ResponseEntity<Practitioner> createPractitioner(@Valid @RequestBody
                                                         Practitioner practitioner) {
        practitioner.setId(null); // ensure to create new names
        var saved = practitionerRepository.save(practitioner);
        return ResponseEntity
                .created(URI.create("/api/practitioner/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Practitioner> updatePractitioner(@PathVariable(value = "id") UUID practitionerId, @Valid @RequestBody Practitioner practitionerDetails) {
        return practitionerRepository.findById(practitionerId)
                .map(practitioner -> {
                    practitioner.setActive(practitionerDetails.isActive());
                    practitioner.setGender(practitionerDetails.getGender());
                    practitioner.setIdentifier(practitionerDetails.getIdentifier());
                    practitioner.setName(practitionerDetails.getName());
                    practitioner.setAddress(practitionerDetails.getAddress());
                    practitioner.setBirthDate(practitionerDetails.getBirthDate());
                    practitioner.setText(practitionerDetails.getText());
                    practitioner.setTelecom(practitionerDetails.getTelecom());
                    practitioner.setCommunication(practitionerDetails.getCommunication());
                    practitioner.setPhoto(practitionerDetails.getPhoto());
                    practitioner.setQualification(practitionerDetails.getQualification());
                    Practitioner updatedPractitioner = practitionerRepository.save(practitioner);
                    return ResponseEntity.ok(updatedPractitioner);
                }).orElseGet( () -> createPractitioner(practitionerDetails));
    }

    @DeleteMapping("/{id}")     //Braucht man auch, um darauf zugreifen zu können bspw. beim testen
    public ResponseEntity<Practitioner> deletePractitioner(@PathVariable(value = "id") UUID practitionerId) {
        return practitionerRepository.findById(practitionerId).map(practitioner -> {
            practitionerRepository.delete(practitioner);
            return ResponseEntity.ok().<Practitioner>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

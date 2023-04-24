package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Location;
import at.spengergasse.spengermed.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    @ResponseBody
    public Iterable<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable UUID id) {
        return locationRepository
                .findById(id)
                .map(location -> ResponseEntity.ok().body(location))
                .orElse(ResponseEntity.notFound().build());
    }
    @Transactional
    @PostMapping()
    public ResponseEntity<Location> createLocation(@Valid @RequestBody
                                                               Location location) {
        location.setId(null);
        var saved = locationRepository.save(location);
        return ResponseEntity
                .created(URI.create("/api/location/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable(value = "id") UUID locationId, @Valid @RequestBody Location locationDetails) {
        return locationRepository.findById(locationId)
                .map(location -> {

                    location.setIdentifier(locationDetails.getIdentifier());
                    location.setStatus(locationDetails.getStatus());
                    location.setOperationalStatus(locationDetails.getOperationalStatus());
                    location.setName(locationDetails.getName());
                    location.setAlias(locationDetails.getAlias());
                    location.setDescription(locationDetails.getDescription());
                    location.setMode(locationDetails.getMode());
                    location.setType(locationDetails.getType());
                    location.setContact(locationDetails.getContact());
                    location.setAddress(locationDetails.getAddress());
                    location.setForm(locationDetails.getForm());
                    location.setPosition(locationDetails.getPosition());
                    location.setManagingOrganization(locationDetails.getManagingOrganization());
                    location.setPartOf(locationDetails.getPartOf());
                    location.setCharacteristic(locationDetails.getCharacteristic());
                    location.setHoursOfOperation(locationDetails.getHoursOfOperation());
                    location.setVirtualService(locationDetails.getVirtualService());
                    location.setEndpoint(locationDetails.getEndpoint());

                    Location updatedLocation = locationRepository.save(location);
                    return ResponseEntity.ok(updatedLocation);
                }).orElseGet( () -> createLocation(locationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable(value = "id") UUID locationId) {
        return locationRepository.findById(locationId).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().<Location>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.repository.StructureMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/structuremap")
public class StructureMapController {

    @Autowired
    private StructureMapRepository structureMapRepository;

    @GetMapping
    public @ResponseBody
    Iterable<StructureMap> getAllStructureMaps(){
        return structureMapRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StructureMap> getStructureMap(@PathVariable UUID id) {
        return structureMapRepository
                .findById(id)
                .map(structureMap -> ResponseEntity.ok().body(structureMap))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<StructureMap> createStrucutreMap(@Valid @RequestBody StructureMap structureMap) {
        structureMap.setId(null);
        var saved = structureMapRepository.save(structureMap);
        return ResponseEntity
                .created(URI.create("/api/structuremap/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<StructureMap> updateStructureMap(@PathVariable(value = "id") UUID structureMapId, @Valid @RequestBody StructureMap structureMapDetails) {
        return structureMapRepository.findById(structureMapId)
                .map(structureMap -> {

                    structureMap.setStat(structureMapDetails.getStat());
                    structureMap.setDatetime(structureMapDetails.getDatetime());
                    structureMap.setDetails(structureMapDetails.getDetails());
                    structureMap.setJunction(structureMapDetails.getJunction());
                    structureMap.setStructure(structureMapDetails.getStructure());

                    StructureMap updatedStructureMap = structureMapRepository.save(structureMap);
                    return ResponseEntity.ok(updatedStructureMap);
                }).orElseGet( () -> createStrucutreMap(structureMapDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StructureMap> deleteStructureMap(@PathVariable(value = "id") UUID structureMapID) {
        return structureMapRepository.findById(structureMapID).map(structureMap -> {
            structureMapRepository.delete(structureMap);
            return ResponseEntity.ok().<StructureMap>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}

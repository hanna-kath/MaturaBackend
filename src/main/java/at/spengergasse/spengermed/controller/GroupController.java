package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Group;
import at.spengergasse.spengermed.model.RiskAssessment;
import at.spengergasse.spengermed.repository.GroupRepository;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/group")
@RestController
@CrossOrigin
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping
    @ResponseBody
    public Iterable<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable UUID id) {
        return groupRepository
                .findById(id)
                .map(group -> ResponseEntity.ok().body(group))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Group> createGroup(@Valid @RequestBody
                                                               Group group) {
        group.setId(null); // ensure to create new names
        var saved = groupRepository.save(group);
        return ResponseEntity
                .created(URI.create("/api/group/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(
            @PathVariable("id") UUID groupId, @Valid @RequestBody Group groupDetails) {
        return groupRepository
                .findById(groupId)
                .map(
                        group -> {
                            group.setIdentifier(groupDetails.getIdentifier());
                            group.setCharacteristic(groupDetails.getCharacteristic());
                            group.setText(groupDetails.getText());

                            Group updatedGroup = groupRepository.save(group);
                            return ResponseEntity.ok(updatedGroup);
                        })
                .orElseGet(() -> createGroup(groupDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable("id") UUID groupId) {
        return groupRepository
                .findById(groupId)
                .map(
                        group -> {
                            groupRepository.delete(group);
                            return ResponseEntity.ok().<Group>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }
}

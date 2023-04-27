package at.spengergasse.spengermed.controller;
import at.spengergasse.spengermed.model.Ingredient;
import at.spengergasse.spengermed.model.RiskAssessment;
import at.spengergasse.spengermed.repository.IngredientRepository;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping(path = "/api/ingredient")
@RestController
@CrossOrigin
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    @ResponseBody
    public Iterable<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable UUID id) {
        return ingredientRepository
                .findById(id)
                .map(ingredient -> ResponseEntity.ok().body(ingredient))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody
                                                       Ingredient ingredient) {
        ingredient.setId(null); // ensure to create new names
        var saved = ingredientRepository.save(ingredient);
        return ResponseEntity
                .created(URI.create("/api/ingredient/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable("id") UUID ingredientId, @Valid @RequestBody Ingredient ingredientDetails) {
        return ingredientRepository
                .findById(ingredientId)
                .map(
                        ingredient -> {
                            ingredient.setIdentifier(ingredientDetails.getIdentifier());
                            ingredient.setStatus(ingredientDetails.getStatus());
                            ingredient.setForr(ingredientDetails.getForr());
                            ingredient.setRole(ingredientDetails.getRole());
                            ingredient.setFunction(ingredientDetails.getFunction());
                            ingredient.setGroup(ingredientDetails.getGroup());
                            ingredient.setAllergenicIndicator(ingredientDetails.getAllergenicIndicator());
                            ingredient.setComment(ingredientDetails.getComment());
                            ingredient.setManufacturer(ingredientDetails.getManufacturer());
                            ingredient.setSubstance(ingredientDetails.getSubstance());

                            Ingredient updatedIngredient = ingredientRepository.save(ingredient);
                            return ResponseEntity.ok(updatedIngredient);
                        })
                .orElseGet(() -> createIngredient(ingredientDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") UUID ingredientId) {
        return ingredientRepository
                .findById(ingredientId)
                .map(
                        ingredient -> {
                            ingredientRepository.delete(ingredient);
                            return ResponseEntity.ok().<Ingredient>build();
                        })
                .orElse(ResponseEntity.notFound().build());
    }

}

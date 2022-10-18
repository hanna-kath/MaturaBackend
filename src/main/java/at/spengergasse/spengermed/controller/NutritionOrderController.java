package at.spengergasse.spengermed.controller;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.NutritionOrder;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.NutritionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/nutritionorder")
public class NutritionOrderController {

    @Autowired
    private NutritionOrderRepository nutritionOrderRepository;

    @GetMapping
    public @ResponseBody
    Iterable<NutritionOrder> getAllNutritionOrders(){
        return nutritionOrderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionOrder> getNutritionOrder(@PathVariable UUID id) {
        return nutritionOrderRepository
                .findById(id)
                .map(nutritionOrder -> ResponseEntity.ok().body(nutritionOrder))
                .orElse(ResponseEntity.notFound().build());
    }
    @Transactional  //Datenbankzugriffe als Transaktion
    @PostMapping()
    public ResponseEntity<NutritionOrder> createNutritionOrder(@Valid @RequestBody
                                                     NutritionOrder nutritionOrder) {
        nutritionOrder.setId(null); // ensure to create new names
        var saved = nutritionOrderRepository.save(nutritionOrder);
        return ResponseEntity
                .created(URI.create("/api/nutritionorder/" + saved.getId()))
                .body(saved);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<NutritionOrder> updateNutritionOrder(@PathVariable(value = "id") UUID nutritionorderId, @Valid @RequestBody NutritionOrder nutritionorderDetails) {
        return nutritionOrderRepository.findById(nutritionorderId)
                .map(nutritionOrder -> {

                    nutritionOrder.setIdentifier(nutritionorderDetails.getIdentifier());
                    nutritionOrder.setText(nutritionorderDetails.getText());
                    nutritionOrder.setInstantiates(nutritionorderDetails.getInstantiates());
                    nutritionOrder.setIntent(nutritionorderDetails.getIntent());
                    nutritionOrder.setPatient(nutritionorderDetails.getPatient());
                    nutritionOrder.setOralDiet(nutritionorderDetails.getOralDiet());
                    NutritionOrder updatedNutritionorder = nutritionOrderRepository.save(nutritionOrder);
                    return ResponseEntity.ok(updatedNutritionorder);
                }).orElseGet( () -> createNutritionOrder(nutritionorderDetails));
    }

    @DeleteMapping("/{id}")     //Braucht man auch, um darauf zugreifen zu können bspw. beim testen
    public ResponseEntity<NutritionOrder> deleteCondition(@PathVariable(value = "id") UUID nutritionorderId) {
        return nutritionOrderRepository.findById(nutritionorderId).map(nutritionOrder -> {
            nutritionOrderRepository.delete(nutritionOrder);
            return ResponseEntity.ok().<NutritionOrder>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

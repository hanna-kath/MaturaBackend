package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.IngredientRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test, ob man das in die Datenbank speichern, abrufen, etc. kann
@SpringBootTest
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneIngredient(){
        Ingredient i = returnOneIngredient();
        Ingredient savedI = ingredientRepository.save(i);
        Optional<Ingredient> loadedIOptional = ingredientRepository.findById(savedI.getId());
        Ingredient loadedI = loadedIOptional.get();

        assertEquals(i.getText(), loadedI.getText());
        assertEquals(i.getIdentifier(), loadedI.getIdentifier());
        assertEquals(i.getStatus(), loadedI.getStatus());
        assertEquals(i.getRole(), loadedI.getRole());
        assertEquals(i.getGroup(), loadedI.getGroup());
        assertEquals(i.getAllergenicIndicator(), loadedI.getAllergenicIndicator());
        assertEquals(i.getComment(), loadedI.getComment());
        assertEquals(i.getSubstance(), loadedI.getSubstance());

        assertTrue(CollectionUtils.isEqualCollection(i.getFunction(), loadedI.getFunction()));
        assertTrue(CollectionUtils.isEqualCollection(i.getForr(), loadedI.getForr()));
        assertTrue(CollectionUtils.isEqualCollection(i.getManufacturer(), loadedI.getManufacturer()));
    }

    @Test
    @Transactional
    public void testDeleteIngredient(){
        Ingredient i = returnOneIngredient();
        Ingredient savedI = ingredientRepository.save(i);
        ingredientRepository.deleteById(savedI.getId());
        assertTrue(ingredientRepository.findById(savedI.getId()).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateIngredient(){
        Ingredient i = returnOneIngredient();
        Ingredient savedEmptyI = ingredientRepository.save(new Ingredient());
        savedEmptyI.setText(i.getText());
        savedEmptyI.setStatus(i.getStatus());
        savedEmptyI.setIdentifier(i.getIdentifier());
        savedEmptyI.setForr(i.getForr());
        savedEmptyI.setFunction(i.getFunction());
        savedEmptyI.setRole(i.getRole());
        savedEmptyI.setGroup(i.getGroup());
        savedEmptyI.setAllergenicIndicator(i.getAllergenicIndicator());
        savedEmptyI.setComment(i.getComment());
        savedEmptyI.setManufacturer(i.getManufacturer());
        savedEmptyI.setSubstance(i.getSubstance());
    }

    public static Ingredient returnOneIngredient() {

        Identifier identifiers = Identifier.builder()
                .value("20000000-1234-1654-2621-00000000ac12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build();

        List<Reference> forrs = new ArrayList<>();
        forrs.add(Reference.builder()
                .type("ManufacturedItemDefinition")
                .display("Ingredients")
                .build());

        CodeableConcept roles = CodeableConcept.builder()
                .text("active")
                .build();

        List<CodeableConcept> functions = new ArrayList<>();
        functions.add(CodeableConcept.builder()
                .text("Ingredient Function")
                .build());

        CodeableConcept groups = CodeableConcept.builder()
                .text("inner body")
                .build();

        List<Manufacturer> ma = new ArrayList<>();
        ma.add(Manufacturer.builder()
                .role(Manufacturer.code.actual)
                .build());

        List<Strength> strengths = new ArrayList<>();
        strengths.add(Strength.builder()
                .measurementPoint("Start at X")
                .textconcentration("Concentration")
                .textpresentation("Presentations")
                .build());

        Substance substances = Substance.builder()
                .strenght(strengths)
                .build();

        Ingredient i = Ingredient.builder()
                .identifier(identifiers)
                .status(Ingredient.code.draft)
                .forr(forrs)
                .role(roles)
                .function(functions)
                .group(groups)
                .allergenicIndicator(true)
                .comment("Comment 1")
                .manufacturer(ma)
                .substance(substances)
                .build();

        return i;
    }

}

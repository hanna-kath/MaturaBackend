package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.NutritionOrderRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NutritionOrderRepositoryTest {

    @Autowired
    private NutritionOrderRepository nutritionOrderRepository;

    @Test
    @Transactional
    public void testSaveLoadOneNutritionOrder() {
        NutritionOrder no = returnOneNutritionOrder();
        NutritionOrder savedNo = nutritionOrderRepository.save(no);
        Optional<NutritionOrder> loadedNoOptional = nutritionOrderRepository.findById(UUID.fromString(String.valueOf(savedNo.getId())));
        NutritionOrder loadedNo = loadedNoOptional.get();

        assertEquals(no.getText(), loadedNo.getText());
        assertEquals(no.getIntent(), loadedNo.getIntent());
        assertEquals(no.getPatient(), loadedNo.getPatient());
        assertEquals(no.getOralDiet(), loadedNo.getOralDiet());

        assertTrue(CollectionUtils.isEqualCollection(no.getIdentifier(), loadedNo.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(no.getInstantiates(), loadedNo.getInstantiates()));
    }

    @Test
    @Transactional
    public void testDeleteOneNutritionOrder() {
        NutritionOrder no = returnOneNutritionOrder();
        NutritionOrder savedNo = nutritionOrderRepository.save(no);
        nutritionOrderRepository.deleteById(UUID.fromString(String.valueOf(savedNo.getId())));
        assertTrue(nutritionOrderRepository.findById(UUID.fromString(String.valueOf(savedNo.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneNutritionOrder() {
        NutritionOrder no = returnOneNutritionOrder();
        NutritionOrder savedEmptyNo = nutritionOrderRepository.save(new NutritionOrder());
        savedEmptyNo.setInstantiates(no.getInstantiates());
        savedEmptyNo.setIntent(no.getIntent());
        savedEmptyNo.setPatient(no.getPatient());
        savedEmptyNo.setOralDiet(no.getOralDiet());
    }

    public static NutritionOrder returnOneNutritionOrder() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-0000-00000000no12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        List<URIString> instantiates = new ArrayList<>();
        instantiates.add(URIString.builder().uriString("protocol")
                .build());

        Reference patients = Reference.builder()
                .reference("Patient/1")
                .build();

        CodeableConcept modifiers = CodeableConcept.builder()
                .text("...")
                .build();

        List<Nutrient> nutrients = new ArrayList<>();
        nutrients.add(Nutrient.builder()
                .modifier(modifiers)
                .build());

        List<CodeableConcept> types = new ArrayList<>();
        types.add(CodeableConcept.builder().text("type1")
                .build());

        OralDiet oralDiets = OralDiet.builder()
                .nutrient(nutrients)
                .type(types)
                .build();

        NutritionOrder no = NutritionOrder.builder()
                .identifier(identifiers)
                .instantiates(instantiates)
                .intent(NutritionOrder.IntentCode.instanceorder)
                .patient(patients)
                .oralDiet(oralDiets)
                .build();

        return no;

    }

}

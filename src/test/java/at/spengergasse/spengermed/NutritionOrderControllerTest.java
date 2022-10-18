package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.NutritionOrder;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.NutritionOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class NutritionOrderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    NutritionOrderRepository nutritionOrderRepository;

    @Test
    public void getAllNutritionOrders() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/nutritionorder"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getANutritionOrder() throws Exception {
        NutritionOrder nutritionOrder = NutritionOrderRepositoryTest.returnOneNutritionOrder();
        val id = nutritionOrderRepository.save(nutritionOrder).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/nutritionorder/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postANutritionOrder(){
        NutritionOrder nutritionOrder = NutritionOrderRepositoryTest.returnOneNutritionOrder();
        String json= null;
        try {
            json = om.writeValueAsString(nutritionOrder);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/nutritionorder")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void putANutritionOrder() throws Exception {
        NutritionOrder nutritionOrder = nutritionOrderRepository.save(NutritionOrderRepositoryTest.returnOneNutritionOrder());
        val id = nutritionOrder.getId();
        Entities.unsetAllIds(nutritionOrder);

        nutritionOrder.setId(UUID.fromString("f5cb4c98-4ee4-11ed-bdc3-0242ac120002"));
        nutritionOrder.setIntent(NutritionOrder.IntentCode.option);

        String json = om.writeValueAsString(nutritionOrder);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/nutritionorder/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteANutritionOrder() throws Exception {
        NutritionOrder no = NutritionOrderRepositoryTest.returnOneNutritionOrder();
        NutritionOrder noWithId = nutritionOrderRepository.save(no);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/nutritionorder/" + noWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}

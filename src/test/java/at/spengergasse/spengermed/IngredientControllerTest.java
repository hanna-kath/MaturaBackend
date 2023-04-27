package at.spengergasse.spengermed;
import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.IngredientRepository;
import at.spengergasse.spengermed.repository.PatientRepository;
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


// Test, ob die Webschnittstelle so reagiert, wie sie sollte
@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerTest {

    @Autowired
    MockMvc mockMvc;    //spezielle Art von Integrationstest - zwischen Unit und Integrationstest
    // für einen Aufruf bestimmte externe URL definierte Werte zurückgeliefert, anstatt einen
    // http-Aufruf des externen Dienstes auszuführen

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    IngredientRepository ingredientRepository;


    @Test
    public void getAllIngredients() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/ingredient"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void getAnIngredient(){
        try {
            Ingredient ingredient = IngredientRepositoryTest.returnOneIngredient();
            val id = ingredientRepository.save(ingredient).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/ingredient/" + id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAnIngredient(){
        Ingredient ingredient = IngredientRepositoryTest.returnOneIngredient();
        String json= null;
        try {
            json = om.writeValueAsString(ingredient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredient/")
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
    public void putAnIngredient() throws Exception {
        Ingredient ingredient = ingredientRepository.save(IngredientRepositoryTest.returnOneIngredient());
        val id = ingredient.getId();
        Entities.unsetAllIds(ingredient);
        ingredient.setId(UUID.fromString("00000000-0000-2345-0000-000000000123"));

        String json = om.writeValueAsString(ingredient);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/ingredient/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAnIngredient() throws Exception {
        Ingredient e = IngredientRepositoryTest.returnOneIngredient();
        Ingredient eWithId = ingredientRepository.save(e);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/ingredient/" + eWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

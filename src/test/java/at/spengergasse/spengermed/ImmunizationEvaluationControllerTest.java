package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Consent;
import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.repository.ConsentRepository;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
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
public class ImmunizationEvaluationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    ImmunizationEvaluationRepository immunizationEvaluationRepository;

    @Test
    public void getAllImmunizationEvaluations() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/immunizationevaluation"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAImmunizationEvaluation() throws Exception {
        ImmunizationEvaluation immunizationEvaluation = ImmunizationEvaluationRepositoryTest.returnOneImmunizationEvaluation();
        val id = immunizationEvaluationRepository.save(immunizationEvaluation).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/immunizationevaluation/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAImmunizationEvaluation(){
        ImmunizationEvaluation immunizationEvaluation = ImmunizationEvaluationRepositoryTest.returnOneImmunizationEvaluation();
        String json= null;
        try {
            json = om.writeValueAsString(immunizationEvaluation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/immunizationevaluation/")
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
    public void putAImmunizationEvaluation() throws Exception {
        ImmunizationEvaluation immunizationEvaluation = immunizationEvaluationRepository.save(ImmunizationEvaluationRepositoryTest.returnOneImmunizationEvaluation());
        val id = immunizationEvaluation.getId();
        Entities.unsetAllIds(immunizationEvaluation);

        immunizationEvaluation.setId(UUID.fromString("625e51ba-e37e-11ed-b5ea-0242ac120002"));
        immunizationEvaluation.setStatus(ImmunizationEvaluation.StatusCode.completed);

        String json = om.writeValueAsString(immunizationEvaluation);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/immunizationevaluation/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAImmunizationEvaluation() throws Exception {
        ImmunizationEvaluation immunizationEvaluation = ImmunizationEvaluationRepositoryTest.returnOneImmunizationEvaluation();
        ImmunizationEvaluation immunizationEvaluationWithId = immunizationEvaluationRepository.save(immunizationEvaluation);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/immunizationevaluation/" + immunizationEvaluationWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

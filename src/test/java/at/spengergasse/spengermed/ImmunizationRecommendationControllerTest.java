package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.model.ImmunizationRecommendation;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.ImmunizationRecommendationRepository;
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

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ImmunizationRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    ImmunizationRecommendationRepository immunizationRecommendationRepository;

    @Test
    public void getAllImmunizationRecommendations() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/immunizationrecommendation"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAImmunizationRecommendation(){
        try {
            ImmunizationRecommendation immunizationRecommendation = ImmunizationRecommendationRepositoryTest.returnOneImmunizationRecommendation();
            val id = immunizationRecommendationRepository.save(immunizationRecommendation).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/immunizationrecommendation/"+id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAImmunizationRecommendation(){
        ImmunizationRecommendation immunizationRecommendation = ImmunizationRecommendationRepositoryTest.returnOneImmunizationRecommendation();
        String json= null;
        try {
            json = om.writeValueAsString(immunizationRecommendation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/immunizationrecommendation/")
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
    public void putAImmunizationRecommendation() throws Exception {
        ImmunizationRecommendation immunizationRecommendation = immunizationRecommendationRepository.save(ImmunizationRecommendationRepositoryTest.returnOneImmunizationRecommendation());
        val id = immunizationRecommendation.getId();
        Entities.unsetAllIds(immunizationRecommendation);

        immunizationRecommendation.setId(UUID.fromString("625e6cb8-e37e-11ed-b5ea-0242ac120002"));
        immunizationRecommendation.setDate(LocalDateTime.of(2023,04,25,12,30,00,00));

        String json = om.writeValueAsString(immunizationRecommendation);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/immunizationrecommendation/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAImmunizationRecommendation() throws Exception {
        ImmunizationRecommendation immunizationRecommendation = ImmunizationRecommendationRepositoryTest.returnOneImmunizationRecommendation();
        ImmunizationRecommendation immunizationRecommendationWithId = immunizationRecommendationRepository.save(immunizationRecommendation);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/immunizationrecommendation/" + immunizationRecommendationWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

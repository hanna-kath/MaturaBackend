package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.TODO;
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
public class ConditionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    ConditionRepository conditionRepository;

    @Test
    public void getAllConditions() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/condition"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getACondition() throws Exception {
        Condition condition = ConditionRepositoryTest.returnOneCondition();
        val id = conditionRepository.save(condition).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/condition/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postACondition(){
        Condition condition = ConditionRepositoryTest.returnOneCondition();
        String json= null;
        try {
            json = om.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/condition")
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
    public void putACondition() throws Exception {
        Condition condition = conditionRepository.save(ConditionRepositoryTest.returnOneCondition());
        val id = condition.getId();
        Entities.unsetAllIds(condition);

        condition.setId(UUID.fromString("00000000-0000-0000-0000-000000000123"));

        String json = om.writeValueAsString(condition);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/condition/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteACondition() throws Exception {
        Condition c = ConditionRepositoryTest.returnOneCondition();
        Condition cWithId = conditionRepository.save(c);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/condition/" + cWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

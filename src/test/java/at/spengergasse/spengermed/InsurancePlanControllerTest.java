package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.InsurancePlan;
import at.spengergasse.spengermed.repository.InsurancePlanRepository;
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
public class InsurancePlanControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    InsurancePlanRepository insurancePlanRepository;


    @Test
    public void getAllInsurancePlans() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/insurancePlan"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAInsurancePlan() throws Exception {
        InsurancePlan ip = InsurancePlanRepositoryTest.returnOneInsurancePlan();
        val id = insurancePlanRepository.save(ip).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/insurancePlan/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAInsurancePlan(){
        InsurancePlan ip = InsurancePlanRepositoryTest.returnOneInsurancePlan();
        String json= null;
        try {
            json = om.writeValueAsString(ip);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/insurancePlan")
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
    public void putAInsurancePlan() throws Exception {
        InsurancePlan ip = insurancePlanRepository.save(InsurancePlanRepositoryTest.returnOneInsurancePlan());
        val id = ip.getId();
        Entities.unsetAllIds(ip);

        ip.setId(UUID.fromString("abcb4c98-1234-11ed-abcd-0242ac120abb"));

        String json = om.writeValueAsString(ip);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/insurancePlan/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAInsurancePlan() throws Exception {
        InsurancePlan ip = InsurancePlanRepositoryTest.returnOneInsurancePlan();
        InsurancePlan ipWithId = insurancePlanRepository.save(ip);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/insurancePlan/" + ipWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

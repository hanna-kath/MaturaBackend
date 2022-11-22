package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.model.RiskAssessment;
import at.spengergasse.spengermed.repository.PractitionerRepository;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootTest         //Macht aus der Klasse eine Testklasse
@AutoConfigureMockMvc
public class RiskAssessmentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    RiskAssessmentRepository riskAssessmentRepository;


    @Test
    public void getAllRiskAssessments() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/riskassessment"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getARiskAssessment() throws Exception {
        RiskAssessment ra = RiskAssessmentRepositoryTest.returnOneRiskAssessment();
        val id = riskAssessmentRepository.save(ra).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/riskassessment/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postARiskAssessment(){
        RiskAssessment ra = RiskAssessmentRepositoryTest.returnOneRiskAssessment();
        String json= null;
        try {
            json = om.writeValueAsString(ra);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/riskassessment")
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
    public void putARiskAssessment() throws Exception {
        RiskAssessment ra = riskAssessmentRepository.save(RiskAssessmentRepositoryTest.returnOneRiskAssessment());
        val id = ra.getId();
        Entities.unsetAllIds(ra);

        ra.setId(UUID.fromString("f5cb4c98-1234-11ed-bdc3-0242ac120abc"));
        ra.setStatus(RiskAssessment.StatusCode.registered);

        String json = om.writeValueAsString(ra);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/riskassessment/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteARiskAssessment() throws Exception {
        RiskAssessment r = RiskAssessmentRepositoryTest.returnOneRiskAssessment();
        RiskAssessment rWithId = riskAssessmentRepository.save(r);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/riskassessment/" + rWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

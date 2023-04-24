package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Consent;
import at.spengergasse.spengermed.model.NutritionOrder;
import at.spengergasse.spengermed.repository.ConsentRepository;
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
public class ConsentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    ConsentRepository consentRepository;

    @Test
    public void getAllConsents() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/consent"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAConsent() throws Exception {
        Consent consent = ConsentRepositoryTest.returnOneConsent();
        val id = consentRepository.save(consent).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/consent/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAConsent(){
        Consent consent = ConsentRepositoryTest.returnOneConsent();
        String json= null;
        try {
            json = om.writeValueAsString(consent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/consent/")
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
    public void putAConsent() throws Exception {
        Consent consent = consentRepository.save(ConsentRepositoryTest.returnOneConsent());
        val id = consent.getId();
        Entities.unsetAllIds(consent);

        consent.setId(UUID.fromString("910583c0-e1e0-11ed-b5ea-0242ac120002"));
        consent.setStatus(Consent.StatusCode.active);

        String json = om.writeValueAsString(consent);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/consent/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAConsent() throws Exception {
        Consent consent = ConsentRepositoryTest.returnOneConsent();
        Consent consentWithId = consentRepository.save(consent);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/consent/" + consentWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Medication;
import at.spengergasse.spengermed.model.NutritionOrder;
import at.spengergasse.spengermed.repository.MedicationRepository;
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
public class MedicationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    MedicationRepository medicationRepository;

    @Test
    public void getAllMedications() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/medication"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAMedication() throws Exception {
        Medication medication = MedicationRepositoryTest.returnOneMedication();
        val id = medicationRepository.save(medication).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/medication/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAMedication(){
        Medication medication = MedicationRepositoryTest.returnOneMedication();
        String json= null;
        try {
            json = om.writeValueAsString(medication);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/medication")
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
    public void putAMedication() throws Exception {
        Medication medication = medicationRepository.save(MedicationRepositoryTest.returnOneMedication());
        val id = medication.getId();
        Entities.unsetAllIds(medication);

        medication.setId(UUID.fromString("f5cb4c98-4131-11ed-1234-0242ac120002"));
        medication.setStatus(Medication.StatusCode.inactive);

        String json = om.writeValueAsString(medication);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/medication/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAMedication() throws Exception {
        Medication medication = MedicationRepositoryTest.returnOneMedication();
        Medication medicationWithId = medicationRepository.save(medication);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/medication/" + medicationWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

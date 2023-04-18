package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.model.VisionPrescription;
import at.spengergasse.spengermed.repository.ConditionRepository;
import at.spengergasse.spengermed.repository.IVisionPrescriptionRespository;
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
public class VisionPrescriptionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    IVisionPrescriptionRespository vpRepository;

    @Test
    public void getAllVisionPrescriptions() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/visionprescription"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAVisionPrescription() throws Exception {
        VisionPrescription vp = IVisionPrescriptionRepositoryTest.returnOneVisionPrescription();
        val id = vpRepository.save(vp).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/visionprescription/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAVisionPrescription(){
        VisionPrescription vp = IVisionPrescriptionRepositoryTest.returnOneVisionPrescription();
        String json= null;
        try {
            json = om.writeValueAsString(vp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/visionprescription")
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
    public void putAVisionPrescription() throws Exception {
        VisionPrescription visionPrescription = vpRepository.save(IVisionPrescriptionRepositoryTest.returnOneVisionPrescription());
        val id = visionPrescription.getId();
        Entities.unsetAllIds(visionPrescription);

        visionPrescription.setId(UUID.fromString("00a00000-abc9-0000-2471-000000000123"));

        String json = om.writeValueAsString(visionPrescription);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/visionprescription/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAVisionPrescription() throws Exception {
        VisionPrescription vp = IVisionPrescriptionRepositoryTest.returnOneVisionPrescription();
        VisionPrescription vpWithId = vpRepository.save(vp);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/visionprescription/" + vpWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

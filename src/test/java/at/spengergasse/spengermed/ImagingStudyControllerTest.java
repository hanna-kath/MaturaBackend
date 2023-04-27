package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.ImagingStudy;
import at.spengergasse.spengermed.repository.ImagingStudyRepository;
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
public class ImagingStudyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    ImagingStudyRepository imagingStudyRepository;

    @Test
    public void getAllImagingStudy() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/imagingstudy"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAImagingStudy(){
        try {
            ImagingStudy imagingStudy = ImagingStudyRepositoryTest.returnOneImagingStudy();
            val id = imagingStudyRepository.save(imagingStudy).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/imagingstudy/"+id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAImagingStudy(){
        ImagingStudy is = ImagingStudyRepositoryTest.returnOneImagingStudy();
        String json= null;
        try {
            json = om.writeValueAsString(is);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/imagingstudy")
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
    public void putAImagingStudy() throws Exception {
        ImagingStudy is = imagingStudyRepository.save(ImagingStudyRepositoryTest.returnOneImagingStudy());
        val id = is.getId();
        Entities.unsetAllIds(is);

        is.setId(UUID.fromString("abcb4c98-1234-11ed-abcd-0242ac120abc"));
        //g.setCharacteristic(GroupComponent.Code.grouped)

        String json = om.writeValueAsString(is);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/imagingstudy/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAImagingStudy() throws Exception {
        ImagingStudy is = ImagingStudyRepositoryTest.returnOneImagingStudy();
        ImagingStudy gWithId = imagingStudyRepository.save(is);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/imagingstudy/" + gWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

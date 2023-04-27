package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.ImplementationGuide;
import at.spengergasse.spengermed.repository.ImplementationGuideRepository;
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
public class ImplementationGuideControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    ImplementationGuideRepository igRepository;


    @Test
    public void getAllImplementationGuides() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/implementationguide"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAImplementationGuide(){
        try {
            ImplementationGuide implementationGuide = ImplementationGuideRepositoryTest.returnOneImplementationGuide();
            val id = igRepository.save(implementationGuide).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/implementationguide/"+id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAImplementationGuide(){
        ImplementationGuide ig = ImplementationGuideRepositoryTest.returnOneImplementationGuide();
        String json= null;
        try {
            json = om.writeValueAsString(ig);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/implementationguide/")
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
    public void putAImplementationGuide() throws Exception {
        ImplementationGuide ig = igRepository.save(ImplementationGuideRepositoryTest.returnOneImplementationGuide());
        val id = ig.getId();
        Entities.unsetAllIds(ig);
        ig.setId(UUID.fromString("00000000-0000-0000-0000-000000000123"));
        String json = om.writeValueAsString(ig);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/implementationguide/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAImplementationGuide() throws Exception {
        ImplementationGuide ig = ImplementationGuideRepositoryTest.returnOneImplementationGuide();;
        ImplementationGuide igWithId = igRepository.save(ig);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/implementationguide/" + igWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

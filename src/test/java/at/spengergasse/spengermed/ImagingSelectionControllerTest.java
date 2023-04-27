package at.spengergasse.spengermed;
import at.spengergasse.spengermed.model.Condition;
import at.spengergasse.spengermed.model.ImagingSelection;
import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.repository.ImagingSelectionRepository;
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
public class ImagingSelectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    ImagingSelectionRepository imagingSelectionRepository;

    @Test
    public void getAllImagingSelections() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/imagingselection"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAImagingSelection(){
        try {
            ImagingSelection imagingSelection = ImagingSelectionRepositoryTest.returnOneImagingselection();
            val id = imagingSelectionRepository.save(imagingSelection).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/imagingselection/" + id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAImagingSelection(){
        ImagingSelection imagingSelection = ImagingSelectionRepositoryTest.returnOneImagingselection();
        String json= null;
        try {
            json = om.writeValueAsString(imagingSelection);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/imagingselection/")
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
    public void putAImagingSelection() throws Exception {
        ImagingSelection imagingSelection = imagingSelectionRepository.save(ImagingSelectionRepositoryTest.returnOneImagingselection());
        val id = imagingSelection.getId();
        Entities.unsetAllIds(imagingSelection);

        imagingSelection.setId(UUID.fromString("625e51ba-2372-11ed-b5ea-0242ac120002"));
        imagingSelection.setStatus(ImagingSelection.code.available);

        String json = om.writeValueAsString(imagingSelection);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/imagingselection/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAImagingSelection() throws Exception {
        ImagingSelection imagingSelection = ImagingSelectionRepositoryTest.returnOneImagingselection();
        ImagingSelection imagingSelectionWithId = imagingSelectionRepository.save(imagingSelection);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/imagingselection/" + imagingSelectionWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

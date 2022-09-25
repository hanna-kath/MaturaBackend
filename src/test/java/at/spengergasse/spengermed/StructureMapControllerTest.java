package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.repository.PatientRepository;
import at.spengergasse.spengermed.repository.StructureMapRepository;
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
public class StructureMapControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    StructureMapRepository structureMapRepository;

    @Test
    public void getAllStructureMaps() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/structuremap"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAStructureMap() throws Exception {
        StructureMap structureMap = StructureMapRepositoryTest.returnOneStructureMap();
        val id = structureMapRepository.save(structureMap).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/structuremap/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAStructureMap(){
        StructureMap structureMap = StructureMapRepositoryTest.returnOneStructureMap();
        String json= null;
        try {
            json = om.writeValueAsString(structureMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/structuremap/")
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
    public void putAStructureMap() throws Exception {
        StructureMap structureMap = structureMapRepository.save(StructureMapRepositoryTest.returnOneStructureMap());
        val id = structureMap.getId();
        Entities.unsetAllIds(structureMap);
        structureMap.setId(UUID.fromString("00000000-0000-0000-0000-000000000123"));

        String json = om.writeValueAsString(structureMap);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/structuremap/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteAStructureMap(){
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/structuremap/00000000-0000-0000-0000-000000000sm1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

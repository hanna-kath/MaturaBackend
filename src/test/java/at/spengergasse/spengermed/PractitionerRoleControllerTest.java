package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.PractitionerRole;
import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.repository.PractitionerRoleRepository;
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
public class PractitionerRoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    PractitionerRoleRepository practitionerRoleRepository;

    @Test
    public void getAllPractitionerRole() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/practitionerrole"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAPractitionerRole() throws Exception {
        PractitionerRole practitionerRole = PractitionerRoleRepositoryTest.returnOnePractitionerRole();
        val id = practitionerRoleRepository.save(practitionerRole).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/practitionerrole/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAPractitionerRole(){
        PractitionerRole practitionerRole = PractitionerRoleRepositoryTest.returnOnePractitionerRole();
        String json= null;
        try {
            json = om.writeValueAsString(practitionerRole);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/practitionerrole/")
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
    public void putAPractitionerRole() throws Exception {
        PractitionerRole practitionerRole = practitionerRoleRepository.save(PractitionerRoleRepositoryTest.returnOnePractitionerRole());
        val id = practitionerRole.getId();
        Entities.unsetAllIds(practitionerRole);
        practitionerRole.setId(UUID.fromString("00000000-0000-0000-0000-000000045123"));

        String json = om.writeValueAsString(practitionerRole);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/practitionerrole/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteAPractitionerRole() throws Exception{
        PractitionerRole pr = PractitionerRoleRepositoryTest.returnOnePractitionerRole();
        PractitionerRole prWithId = practitionerRoleRepository.save(pr);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/practitionerrole/" + prWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

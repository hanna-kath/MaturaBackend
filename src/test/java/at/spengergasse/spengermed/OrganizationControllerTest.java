package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Organization;
import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.repository.OrganizationRepository;
import at.spengergasse.spengermed.repository.PatientRepository;
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
public class OrganizationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    OrganizationRepository organizationRepository;


    @Test
    public void getAllOrganizations() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/organization"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAnOrganization() throws Exception {
        Organization organization = OrganizationRepositoryTest.returnOneOrganization();
        val id = organizationRepository.save(organization).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/organization/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAnOrganization(){
        Organization organization = OrganizationRepositoryTest.returnOneOrganization();
        String json= null;
        try {
            json = om.writeValueAsString(organization);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/organization/")
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
    public void putAnOrganization() throws Exception {
        Organization organization = organizationRepository.save(OrganizationRepositoryTest.returnOneOrganization());
        val id = organization.getId();
        Entities.unsetAllIds(organization);
        // Ein paar Attribute werden geändert
        organization.setId(UUID.fromString("00000000-0000-0000-0000-000000001234"));

        String json = om.writeValueAsString(organization);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/organization/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteAnOrganization(){
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/organization/00000000-0000-0000-0000-0000000000o1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

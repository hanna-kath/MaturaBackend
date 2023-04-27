package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.ImagingSelection;
import at.spengergasse.spengermed.model.Immunization;
import at.spengergasse.spengermed.repository.ImmunizationRepository;
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


// Test, ob die Webschnittstelle so reagiert, wie sie sollte
@SpringBootTest
@AutoConfigureMockMvc
public class ImmunizationControllerTest {

    @Autowired
    MockMvc mockMvc;    //spezielle Art von Integrationstest - zwischen Unit und Integrationstest
    // für einen Aufruf bestimmte externe URL definierte Werte zurückgeliefert, anstatt einen
    // http-Aufruf des externen Dienstes auszuführen

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    ImmunizationRepository immunizationRepository;


    @Test
    public void getAllImmunizations() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/immunization"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAImmunization(){
        try {
            Immunization immunization = ImmunizationRepositoryTest.returnOneImmunization();
            val id = immunizationRepository.save(immunization).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/immunization/" + id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAnImmunization(){
        Immunization immunization = ImmunizationRepositoryTest.returnOneImmunization();
        String json= null;
        try {
            json = om.writeValueAsString(immunization);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/immunization/")
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
    public void putAnImmunization() throws Exception {
        Immunization immunization = immunizationRepository.save(ImmunizationRepositoryTest.returnOneImmunization());
        val id = immunization.getId();
        Entities.unsetAllIds(immunization);
        immunization.setId(UUID.fromString("00000000-ab15-13ff-1345-000000000123"));
        immunization.setStatus(Immunization.code.completed);

        String json = om.writeValueAsString(immunization);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/immunization/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAnImmunization() throws Exception {
        Immunization e = ImmunizationRepositoryTest.returnOneImmunization();
        Immunization eWithId = immunizationRepository.save(e);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/immunization/" + eWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}

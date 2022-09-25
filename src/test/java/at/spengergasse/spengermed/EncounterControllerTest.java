package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.EncounterRepository;
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


// Test, ob die Webschnittstelle so reagiert, wie sie sollte
@SpringBootTest
@AutoConfigureMockMvc
public class EncounterControllerTest {

    @Autowired
    MockMvc mockMvc;    //spezielle Art von Integrationstest - zwischen Unit und Integrationstest
    // für einen Aufruf bestimmte externe URL definierte Werte zurückgeliefert, anstatt einen
    // http-Aufruf des externen Dienstes auszuführen

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    EncounterRepository encounterRepository;


    @Test
    public void getAllEncounters() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/encounter"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAnEncounter() throws Exception {
        Encounter encounter = EncounterRepositoryTest.returnOneEncounter();
        val id = encounterRepository.save(encounter).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/encounter/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAnEncounter(){
        Encounter encounter = EncounterRepositoryTest.returnOneEncounter();
        String json= null;
        try {
            json = om.writeValueAsString(encounter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/encounter/")
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
    public void putAnEncounter() throws Exception {
        Encounter encounter = encounterRepository.save(EncounterRepositoryTest.returnOneEncounter());
        val id = encounter.getId();
        Entities.unsetAllIds(encounter);
        encounter.setId(UUID.fromString("00000000-0000-0000-0000-000000000123"));

        String json = om.writeValueAsString(encounter);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/encounter/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAnEncounter() throws Exception {
        Encounter e = EncounterRepositoryTest.returnOneEncounter();
        Encounter eWithId = encounterRepository.save(e);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/encounter/" + eWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}

package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Location;
import at.spengergasse.spengermed.model.StructureMap;
import at.spengergasse.spengermed.repository.LocationRepository;
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
public class LocationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    LocationRepository locationRepository;

    @Test
    public void getAllLocations() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/location"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getALocation() throws Exception {
        Location location = LocationRepositoryTest.returnOneLocation();
        val id = locationRepository.save(location).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/location/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postALocation(){
        Location location = LocationRepositoryTest.returnOneLocation();
        String json= null;
        try {
            json = om.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/location/")
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
    public void putALocation() throws Exception {
        Location location = locationRepository.save(LocationRepositoryTest.returnOneLocation());
        val id = location.getId();
        Entities.unsetAllIds(location);
        location.setId(UUID.fromString("00000000-1848-0000-ac49-000000000123"));

        String json = om.writeValueAsString(location);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/location/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteALocation() throws Exception{
        Location location = LocationRepositoryTest.returnOneLocation();
        Location lWithId = locationRepository.save(location);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/location/" + lWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

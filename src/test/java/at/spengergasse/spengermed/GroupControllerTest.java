package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Group;
import at.spengergasse.spengermed.model.GroupComponent;
import at.spengergasse.spengermed.model.RiskAssessment;
import at.spengergasse.spengermed.repository.GroupRepository;
import at.spengergasse.spengermed.repository.RiskAssessmentRepository;
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
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper om;
    @Autowired
    GroupRepository groupRepository;


    @Test
    public void getAllGroups() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/group"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAGroup() throws Exception {
        Group g = GroupRepositoryTest.returnOneGroup();
        val id = groupRepository.save(g).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/group/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAGroup(){
        Group g = GroupRepositoryTest.returnOneGroup();
        String json= null;
        try {
            json = om.writeValueAsString(g);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/group")
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
    public void putAGroup() throws Exception {
        Group g = groupRepository.save(GroupRepositoryTest.returnOneGroup());
        val id = g.getId();
        Entities.unsetAllIds(g);

        g.setId(UUID.fromString("abcb4c98-1234-11ed-abcd-0242ac120abc"));
        //g.setCharacteristic(GroupComponent.Code.grouped)

        String json = om.writeValueAsString(g);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/group/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAGroup() throws Exception {
        Group g = GroupRepositoryTest.returnOneGroup();
        Group gWithId = groupRepository.save(g);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/group/" + gWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Immunization;
import at.spengergasse.spengermed.model.InventoryItem;
import at.spengergasse.spengermed.repository.ImmunizationRepository;
import at.spengergasse.spengermed.repository.InventoryItemRepository;
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
public class InventoryItemControllerTest {

    @Autowired
    MockMvc mockMvc;    //spezielle Art von Integrationstest - zwischen Unit und Integrationstest
    // für einen Aufruf bestimmte externe URL definierte Werte zurückgeliefert, anstatt einen
    // http-Aufruf des externen Dienstes auszuführen

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    InventoryItemRepository inventoryItemRepository;


    @Test
    public void getAllInventoryItems() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/inventoryitem"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAInventoryItem(){
        try {
            InventoryItem inventoryItem = InventoryItemRepositoryTest.returnOneInventoryItem();
            val id = inventoryItemRepository.save(inventoryItem).getId();
            mockMvc

                    .perform(MockMvcRequestBuilders.get("/api/inventoryitem/" + id))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postAnInventoryItem(){
        InventoryItem inventoryItem = InventoryItemRepositoryTest.returnOneInventoryItem();
        String json= null;
        try {
            json = om.writeValueAsString(inventoryItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/inventoryitem/")
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
    public void putAnInventoryItem() throws Exception {
        InventoryItem inventoryItem = inventoryItemRepository.save(InventoryItemRepositoryTest.returnOneInventoryItem());
        val id = inventoryItem.getId();
        Entities.unsetAllIds(inventoryItem);
        inventoryItem.setId(UUID.fromString("00000000-ab15-13ff-1345-000000000123"));
        inventoryItem.setStatus(InventoryItem.StatusCode.active);

        String json = om.writeValueAsString(inventoryItem);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/inventoryitem/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAnInventoryItem() throws Exception {
        InventoryItem inventoryItem = InventoryItemRepositoryTest.returnOneInventoryItem();
        InventoryItem iiWithId = inventoryItemRepository.save(inventoryItem);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/inventoryitem/" + iiWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

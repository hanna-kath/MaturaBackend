package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.ImmunizationEvaluation;
import at.spengergasse.spengermed.model.InventoryReport;
import at.spengergasse.spengermed.repository.ImmunizationEvaluationRepository;
import at.spengergasse.spengermed.repository.InventoryReportRepository;
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
public class InventoryReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;    //provides functionality for reading and writing JSON

    @Autowired
    InventoryReportRepository inventoryReportRepository;

    @Test
    public void getAllInventoryReports() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/inventoryreport"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAInventoryReport() throws Exception {
        InventoryReport inventoryReport = InventoryReportRepositoryTest.returnOneInventoryReport();
        val id = inventoryReportRepository.save(inventoryReport).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/inventoryreport/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAInventoryReport(){
        InventoryReport inventoryReport = InventoryReportRepositoryTest.returnOneInventoryReport();
        String json= null;
        try {
            json = om.writeValueAsString(inventoryReport);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/inventoryreport/")
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
    public void putAInventoryReport() throws Exception {
        InventoryReport inventoryReport = inventoryReportRepository.save(InventoryReportRepositoryTest.returnOneInventoryReport());
        val id = inventoryReport.getId();
        Entities.unsetAllIds(inventoryReport);

        inventoryReport.setId(UUID.fromString("625e7866-e37e-11ed-b5ea-0242ac120002"));
        inventoryReport.setStatus(InventoryReport.StatusCode.requested);

        String json = om.writeValueAsString(inventoryReport);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/inventoryreport/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAInventoryReport() throws Exception {
        InventoryReport inventoryReport = InventoryReportRepositoryTest.returnOneInventoryReport();
        InventoryReport inventoryReportWithId = inventoryReportRepository.save(inventoryReport);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/inventoryreport/" + inventoryReportWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

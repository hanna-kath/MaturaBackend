package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.ImmunizationRecommendation;
import at.spengergasse.spengermed.model.Invoice;
import at.spengergasse.spengermed.repository.InvoiceRepository;
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

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    public void getAllInvoices() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/invoice"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAInvoice() throws Exception {
        Invoice invoice = InvoiceRepositoryTest.returnOneInvoice();
        val id = invoiceRepository.save(invoice).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/invoice/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void postAInvoice(){
        Invoice invoice = InvoiceRepositoryTest.returnOneInvoice();
        String json= null;
        try {
            json = om.writeValueAsString(invoice);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/invoice")
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
    public void putAInvoice() throws Exception {
        Invoice invoice = invoiceRepository.save(InvoiceRepositoryTest.returnOneInvoice());
        val id = invoice.getId();
        Entities.unsetAllIds(invoice);

        invoice.setId(UUID.fromString("625e6cb8-1234-6513-9545-0242ac120002"));
        invoice.setStatus(Invoice.code.cancelled);

        String json = om.writeValueAsString(invoice);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/invoice/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void deleteAInvoice() throws Exception {
        Invoice i = InvoiceRepositoryTest.returnOneInvoice();
        Invoice iWithId = invoiceRepository.save(i);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/invoice/" + iWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

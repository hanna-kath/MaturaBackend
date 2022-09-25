package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.Patient;
import at.spengergasse.spengermed.model.Practitioner;
import at.spengergasse.spengermed.repository.PatientRepository;
import at.spengergasse.spengermed.repository.PractitionerRepository;
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

//Dabei wird die http Schnittstelle überprüft. Man sendet also http Nachrichten an den Controller und prüft,
//ob das Ergebnis geliefert wird, das man erwartet
@SpringBootTest         //Macht aus der Klasse eine Testklasse
@AutoConfigureMockMvc   //Darüber werden Anfragen an den Controller geschickt und die Antworten ausgewertet.
public class PractitionerControllerTest {

    @Autowired              //teilt Spring mit, wo es mittels Injection Objekte in andere Klassen einfügen soll
    MockMvc mockMvc;        //spezielle Art von Integrationstest - zwischen Unit und Integrationstest
    // für einen Aufruf bestimmte externe URL definierte Werte zurückgeliefert, anstatt einen
    // http-Aufruf des externen Dienstes auszuführen

    @Autowired
    ObjectMapper om;        //provides functionality for reading and writing JSON

    @Autowired
    PractitionerRepository practitionerRepository;


    //Erster Test, um alle Ärzte unter der URL  mit GET abzufragen.
    // andExpect überprüft, ob der zurückgegebene Status 200 (OK) ist.
    @Test
    public void getAllPractitioners() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/practitioner"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Ein einzelner Arzt wird mit der id mit GET abgefragt. Dabei muss der Arzt mit der id in der DB existieren.
    //im import.sql muss dieser Arzt somit eingefügt werden.
    //andExpect überprüft, ob der zurückgegebene Status 200 (OK) ist.

    @Test
    public void getAPatient() throws Exception {
        Practitioner practitioner = PractitionerRepositoryTest.returnOnePractitioner();
        val id = practitionerRepository.save(practitioner).getId();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/practitioner/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Es wird ein neuer Arzt mit POST an den Controller geschickt und somit in der DB gespeichert.
    //Wir können die Methode aus PractitionerRepositoryTest, die uns eine Ärzte Instanz erzeugt auch hier verwenden.
    //Der erwartete Rückgabecode ist "CREATED" Also 201.
    @Test
    public void postAPractitioner(){
        Practitioner practitioner = PractitionerRepositoryTest.returnOnePractitioner();
        String json= null;
        try {
            json = om.writeValueAsString(practitioner);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/practitioner")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PUT aktualisiert einen Arzt. Dieser muss somit bereits in der DB existieren (über import.sql)
    //Die id im Practitioner und die id in der URL sollten gesetzt sein und müssen in der DB existieren.
    //Wir erwarten ein 200- OK für einen aktualisierten Practitioner.
    //Kein 201 CREATED, sonst wäre der Practitioner neu angelegt worden.
    @Test
    @Transactional
    public void putAPractitioner() throws Exception {
        Practitioner practitioner = practitionerRepository.save(PractitionerRepositoryTest.returnOnePractitioner());
        val id = practitioner.getId();
        Entities.unsetAllIds(practitioner);

        practitioner.setGender(Patient.GenderCode.unknown);

        String json = om.writeValueAsString(practitioner);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/api/practitioner/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    //Der Practitioner wird gelöscht.
    //Die id muss es bereits geben.
    //Erwartete Antwort ist 200 (OK)s
    @Test
    @Transactional
    public void deleteAPractitioner() throws Exception {
        Practitioner p = PractitionerRepositoryTest.returnOnePractitioner();
        Practitioner pWithId = practitionerRepository.save(p);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/practitioner/" + pWithId.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

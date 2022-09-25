package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.StructureMapRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StructureMapRepositoryTest {

    @Autowired
    private StructureMapRepository structureMapRepository;

    @Test
    @Transactional
    public void testSaveAndLoadOneStructureMap(){
        StructureMap sm = returnOneStructureMap();
        StructureMap savedSm = structureMapRepository.save(sm);
        Optional<StructureMap> loadedSmOptional = structureMapRepository.findById(UUID.fromString(String.valueOf(savedSm.getId())));
        StructureMap loadedSm = loadedSmOptional.get();

        assertEquals(sm.getText(), loadedSm.getText());
        assertEquals(sm.getStat(), loadedSm.getStat());
        assertEquals(sm.getDatetime(), loadedSm.getDatetime());

        assertTrue(CollectionUtils.isEqualCollection(sm.getDetails(), loadedSm.getDetails()));
        assertTrue(CollectionUtils.isEqualCollection(sm.getJunction(), loadedSm.getJunction()));
        assertTrue(CollectionUtils.isEqualCollection(sm.getStructure(), loadedSm.getStructure()));

    }

    @Test
    @Transactional
    public void testDeleteStructureMap(){
        StructureMap sm = returnOneStructureMap();
        StructureMap savedSm = structureMapRepository.save(sm);
        structureMapRepository.deleteById(UUID.fromString(String.valueOf(savedSm.getId())));
        assertTrue(structureMapRepository.findById(UUID.fromString(String.valueOf(savedSm.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateStructureMap(){
        StructureMap sm = returnOneStructureMap();
        StructureMap savedEmptySm = structureMapRepository.save(new StructureMap());
        savedEmptySm.setText(sm.getText());
        savedEmptySm.setStat(sm.getStat());
        savedEmptySm.setDatetime(sm.getDatetime());
        savedEmptySm.setDetails(sm.getDetails());
        savedEmptySm.setJunction(sm.getJunction());
        savedEmptySm.setStructure(sm.getStructure());
    }

    public static StructureMap returnOneStructureMap() {


        ContactPoint teles = ContactPoint.builder()
                .system(ContactPoint.SystemCode.email)
                .use(ContactPoint.UseCode.work)
                .build();

        List<ContactDetail> detailss = new ArrayList<>();
        detailss.add(ContactDetail.builder()
                .name("StructureMapDetail1")
                .tele(teles)
                .build());


        List<CodeableConcept> junctions = new ArrayList<>();
        junctions.add(CodeableConcept.builder().text("en")
                .build());


        List<Structure> structures = new ArrayList<>();
        structures.add(Structure.builder()
                .modus(Structure.ModusCode.source)
                .aliases("A1")
                .build());

        StructureMap sm = StructureMap.builder()
                .stat(StructureMap.StatCode.draft)
                .datetime(LocalDateTime.of(1990,01,01,15,0,0))
                .details(detailss)
                .junction(junctions)
                .structure(structures)
                .build();

        return sm;
    }
}

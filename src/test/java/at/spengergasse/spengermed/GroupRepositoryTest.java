package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.GroupRepository;
import at.spengergasse.spengermed.repository.NutritionOrderRepository;
import com.sun.xml.bind.v2.TODO;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @Transactional
    public void testSaveLoadOneGroup() {
        Group g = returnOneGroup();
        Group savedG = groupRepository.save(g);
        Optional<Group> loadedGOptional = groupRepository.findById(UUID.fromString(String.valueOf(savedG.getId())));
        Group loadedG = loadedGOptional.get();

//        assertEquals(g.getText(), loadedG.getText());

        assertTrue(CollectionUtils.isEqualCollection(g.getIdentifier(), loadedG.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(g.getCharacteristic(), loadedG.getCharacteristic()));
    }

    @Test
    @Transactional
    public void testDeleteOneGroup() {
        Group g = returnOneGroup();
        Group savedG = groupRepository.save(g);
        groupRepository.deleteById(UUID.fromString(String.valueOf(savedG.getId())));
        assertTrue(groupRepository.findById(UUID.fromString(String.valueOf(savedG.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOneGroup() {
        Group g = returnOneGroup();
        Group savedEmptyG = groupRepository.save(new Group());
//        savedEmptyG.setText(g.getText());
        savedEmptyG.setCharacteristic(g.getCharacteristic());
    }

    public static Group returnOneGroup() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-abcd-00000000no12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.official)
                .build());

        CodeableConcept codes = CodeableConcept.builder()
                .text("en")
                .build();

        List<GroupComponent> groupComponents = new ArrayList<>();
        groupComponents.add(GroupComponent.builder()
                .type(GroupComponent.Code.valueOf("grouped"))
                .build());

        List<Characteristic> characteristics = new ArrayList<>();
        characteristics.add(Characteristic.builder()
                .code(codes)
                .valueBoolean(false)
                .groupComponent(groupComponents)
                .build());


        Group g = Group.builder()
                .identifier(identifiers)
                .characteristic(characteristics)
                .build();

        return g;
    }
}

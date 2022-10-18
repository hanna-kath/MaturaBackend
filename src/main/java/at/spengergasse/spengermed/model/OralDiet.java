package at.spengergasse.spengermed.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "od_oraldiet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OralDiet extends BackboneElement{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_od_fk", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> type = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "nu_od_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Nutrient> nutrient = new ArrayList<Nutrient>();
}

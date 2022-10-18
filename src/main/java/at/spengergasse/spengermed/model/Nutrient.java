package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "nu_nutrient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Nutrient extends  BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nu_cc_id")
    private CodeableConcept modifier;

}

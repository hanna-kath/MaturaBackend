package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pre_prediction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Prediction extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pre_outcome", referencedColumnName = "id")
    private CodeableConcept outcome;

    @Column(name = "pre_probabilitydecimal")
    private Float probabilityDecimal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pre_probabilityrange", referencedColumnName = "id")
    private Range probabilityRange;

}

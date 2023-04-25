package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "lim_limit")
public class Limit extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lim_qu_id", referencedColumnName = "id")
    private Quantity value;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lim_cc_id", referencedColumnName = "id")
    private CodeableConcept code;


}

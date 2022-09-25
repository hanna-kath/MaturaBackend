package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "c_contact")
public class Contact extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_cc_id")
    private CodeableConcept purpose;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_hn_id")
    private HumanName name;

}

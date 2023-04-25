package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "cos_cost")
public class Cost extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cos_cc_id", referencedColumnName = "id")
    @NotNull
    private CodeableConcept type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cos_cc_id2", referencedColumnName = "id")
    private CodeableConcept applicability;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cos_cc_id3", referencedColumnName = "id")
    @NotNull
    private CodeableConcept qualifiers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cos_qu_id", referencedColumnName = "id")
    private Quantity value;




}

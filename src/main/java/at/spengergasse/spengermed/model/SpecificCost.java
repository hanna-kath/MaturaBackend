package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "sc_specificCost")
public class SpecificCost extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sc_cc_id", referencedColumnName = "id")
    @NotNull
    private CodeableConcept category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "be_sc_id", referencedColumnName = "id")
    @Builder.Default
    private List<Benefit> benefit = new ArrayList<>();
}

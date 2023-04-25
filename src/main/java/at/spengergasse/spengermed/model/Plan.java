package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "pl_plan")
public class Plan extends BackboneElement {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_pl_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pl_cc_id", referencedColumnName = "id")
    private CodeableConcept type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_pl_id2", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> coverageArea = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_pl_id3", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> network = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gc_pl_id", referencedColumnName = "id")
    @Builder.Default
    private List<GeneralCost> generalCost = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sc_pl_id", referencedColumnName = "id")
    @Builder.Default
    private List<SpecificCost> specificCost = new ArrayList<>();


}

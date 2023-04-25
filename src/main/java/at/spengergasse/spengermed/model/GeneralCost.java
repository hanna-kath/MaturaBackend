package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "gc_generalCost")
public class GeneralCost extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gc_cc_id", referencedColumnName = "id")
    private CodeableConcept type;

    @Column(name = "gc_groupSize")
    @Positive
    private Integer groupSize;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gc_mon_id", referencedColumnName = "id")
    private Money cost;

    @Column(name = "gc_comment")
    private String comment;


}

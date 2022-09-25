package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "d_diagnosis")
public class Diagnosis extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "d_condition_fk")
    private Reference condition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "d_use") //_fk
    private CodeableConcept use;

    @Min(0)
    @Column(name = "d_rank")
    private Integer rank;
}

package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pel_programeligibility")
@SuperBuilder
public class ProgramEligibility extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pel_pr_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept program;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pel_ps_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept programStatus;
}

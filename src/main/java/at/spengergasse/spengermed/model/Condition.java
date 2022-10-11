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
@Table(name = "con_condition")
@SuperBuilder
@AllArgsConstructor
public class Condition extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_con_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_cc_clinicalstatus")
    private CodeableConcept clinicalStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_cc_verificationsstatus")
    private CodeableConcept verificationStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_con_fk", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> category = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_cc_severity")
    private CodeableConcept severity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_cc_id")
    private CodeableConcept code;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_con_fk", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> bodySite = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_re_subject", nullable = false)
    @NotNull
    private Reference subject;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "con_re_encounter")
    private Reference encounter;
}

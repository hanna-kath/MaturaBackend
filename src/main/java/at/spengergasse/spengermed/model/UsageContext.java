package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "uc_usagecontext")
public class UsageContext extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uc_co_id", nullable = false)
    @NotNull
    private Coding code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uc_cc_id", nullable = false)
    @NotNull
    private CodeableConcept valueCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uc_qu_id", nullable = false)
    @NotNull
    private Quantity valueQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uc_ra_id", nullable = false)
    @NotNull
    private Range valueRange;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uc_re_id", nullable = false)
    @NotNull
    private Reference valueReference;

}

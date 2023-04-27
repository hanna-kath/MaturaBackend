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
@Table(name = "mc_monetaryComponent")
public class MonetaryComponent extends Element{

    public enum code {
        base , surcharge , deduction , discount , tax , informational
    }

    @Column(name = "mc_type", nullable = false)
    @NonNull
    private MonetaryComponent.code type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="mc_cc_code", referencedColumnName = "id")
    private CodeableConcept code;

    @Column(name = "mc_factor", nullable = true)
    private double factor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="mc_cc_amount", referencedColumnName = "id")
    private Money amount;

}

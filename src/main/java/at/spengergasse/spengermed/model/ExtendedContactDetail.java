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
@AllArgsConstructor
@Table(name = "ex_extendedContactDetail")
@SuperBuilder
public class ExtendedContactDetail extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_cc_id", referencedColumnName = "id")
    private CodeableConcept purpose;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hn_ex_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<HumanName> name = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_ex_id", referencedColumnName = "id")
    @Builder.Default
    private List<ContactPoint> telecom = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_a_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_re_id", referencedColumnName = "id")
    private Reference organization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_pe_id", referencedColumnName = "id")
    private Period period;
}

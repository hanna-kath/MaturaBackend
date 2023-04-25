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
@Table(name = "cov_coverage")
public class Coverage extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cov_cc_id", referencedColumnName = "id")
    @NotNull
    private CodeableConcept type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_cov_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> network = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "be_cov_id", referencedColumnName = "id")
    @NotNull
    @Builder.Default
    private List<Benefit> benefit = new ArrayList<>();

}

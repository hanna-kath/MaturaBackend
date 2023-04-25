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
@Table(name = "be_benefit")
public class Benefit extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "be_cc_id", referencedColumnName = "id")
    @NotNull
    private CodeableConcept type;

    @Column(name = "be_requirement")
    private String requirement;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lim_be_id", referencedColumnName = "id")
    @Builder.Default
    private List<Limit> limit = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cos_be_id", referencedColumnName = "id")
    @Builder.Default
    private List<Cost> cost = new ArrayList<>();

}

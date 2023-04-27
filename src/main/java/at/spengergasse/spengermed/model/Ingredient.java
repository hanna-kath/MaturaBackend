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
@AllArgsConstructor
@Table(name = "in_ingredient")
@SuperBuilder
public class Ingredient extends DomainResource {

    public enum code {
        draft, active, retired, unknown;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_I_id", referencedColumnName = "id")
    private Identifier identifier;

    @Column(name="in_status")
    private code status;

    //Man kann das nicht for nennen, er checkt das nd dass es ein name ist und nicht die Schleife
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_in_for", referencedColumnName = "id")
    private List<Reference> forr = new ArrayList<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_cc_id", referencedColumnName = "id", nullable = false)
    private CodeableConcept role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_in_id", referencedColumnName = "id")
    private List<CodeableConcept> function = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_cc_idcc", referencedColumnName = "id")
    private CodeableConcept group;

    @Column(name="in_allergenicIndicator")
    private Boolean allergenicIndicator;

    @Column(name="in_comment")
    private String comment;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_in_id", referencedColumnName = "id")
    private List<Manufacturer> manufacturer = new ArrayList<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_sub_id", referencedColumnName = "id", nullable = false)
    private Substance substance;

}

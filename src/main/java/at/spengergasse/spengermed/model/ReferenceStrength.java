package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.map.AbstractReferenceMap;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "res_referencestrenght")
public class ReferenceStrength extends BackboneElement {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "res_code", referencedColumnName = "id", nullable = false)
    private CodeableReference substance;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "res_r_id", referencedColumnName = "id")
    private Ratio strenghtRatio;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "res_rra_id", referencedColumnName = "id")
    private RatioRange strenghtRatioRatio;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "res_qu_id", referencedColumnName = "id")
    private Quantity strenghtQuantity;

    @Column(name="res_measurementPoint")
    private String measurementPoint;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_res_id", referencedColumnName = "id")
    private List<CodeableConcept> country = new ArrayList<>();

}

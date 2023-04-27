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
@Table(name = "str_strenght")
public class Strength extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_r_id", referencedColumnName = "id")
    private Ratio presentationRatio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_rra_id", referencedColumnName = "id")
    private RatioRange presentationRatioRatio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_cc_id", referencedColumnName = "id")
    private CodeableConcept presentationCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_qu_id", referencedColumnName = "id")
    private Quantity presentationQuantity;

    @Column(name="str_textpresentation")
    private String textpresentation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_r_ra", referencedColumnName = "id")
    private Ratio concentrationRatio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_rra_rr", referencedColumnName = "id")
    private RatioRange concentrationRatioRatio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_cc_cc", referencedColumnName = "id")
    private CodeableConcept concentrationCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_qu_qu", referencedColumnName = "id")
    private Quantity concentrationQuantity;

    @Column(name="str_textconcentration")
    private String textconcentration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_cc_cct", referencedColumnName = "id")
    private CodeableConcept basis;

    @Column(name="str_measurementPoint")
    private String measurementPoint;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_str_id", referencedColumnName = "id")
    private List<CodeableConcept> country = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "res_str_id", referencedColumnName = "id")
    private List<ReferenceStrength> referenceStrength = new ArrayList<>();

}

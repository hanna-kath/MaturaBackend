package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "lit_lineItem")
public class LineItem extends BackboneElement{

    @Column(name = "lit_sequence", nullable = true)
    @Min(1)
    //@Positive
    private int sequence;

    @Column(name = "lit_serviceddate", nullable = true)
    private Date serviceddate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lit_pe_servicedPeriod", referencedColumnName = "id", nullable = false)
    @NotNull
    private Period servicedPeriod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lit_re_chargeItemReference", referencedColumnName = "id", nullable = false)
    @NotNull
    private Reference chargeItemReference;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lit_cc_chargeItemCodeableConcept", referencedColumnName = "id")
    private CodeableConcept chargeItemCodeableConcept;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="lit_mc_id", referencedColumnName = "id")
    private List<MonetaryComponent> priceComponent = new ArrayList<>();



}

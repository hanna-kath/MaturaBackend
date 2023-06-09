package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inb_ingredientbb")
@SuperBuilder
public class IngredientBB extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inb_itemcodeableconcept", referencedColumnName = "id")
    private CodeableReference itemCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inb_itemreference", referencedColumnName = "id")
    private Reference itemReference;

    @Column(name="inb_isactive")
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inb_r_id", referencedColumnName = "id")
    private Ratio strengthRatio;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "in_cc_id", referencedColumnName = "id")
//    private CodeableConcept strengthCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inb_qu_id", referencedColumnName = "id")
    private Quantity strengthQuantity;
}

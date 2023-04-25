package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "it_item")
@SuperBuilder
public class Item extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "it_cc_id", referencedColumnName = "id")
    private CodeableConcept category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "it_qu_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Quantity itemStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "it_cf_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableReference item;

}

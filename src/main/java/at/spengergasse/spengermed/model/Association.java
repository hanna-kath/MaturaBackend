package at.spengergasse.spengermed.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "as_assosciation")
public class Association extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "cc_as_id", referencedColumnName = "id", nullable = false)
    private CodeableConcept assosciationType;

    @OneToOne
    @NotNull
    @JoinColumn(name = "re_as_id", referencedColumnName = "id")
    private Reference relatedItem;

    @OneToOne
    @NotNull
    @JoinColumn(name = "ra_as_id", referencedColumnName = "id")
    private Ratio quantity;

}

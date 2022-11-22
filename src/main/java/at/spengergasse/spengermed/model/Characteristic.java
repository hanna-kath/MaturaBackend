package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ch_characteristic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Characteristic extends BackboneElement{

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ch_cc_id", nullable = false)
    private CodeableConcept code;

    @Column(name = "ch_valueboolean")
    private Boolean valueBoolean;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ch_qu_id")
    private Quantity valueQuantity;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gc_ch_id", referencedColumnName = "id")
    @Builder.Default
    private List<GroupComponent> groupComponent = new ArrayList<>();
}

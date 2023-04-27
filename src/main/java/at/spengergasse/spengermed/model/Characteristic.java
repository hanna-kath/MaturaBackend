package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    
    
    //InventoryItem
    @Column(name = "ch_valueString")
    @NotNull
    private String valueString;

    @Column(name = "ch_valueInteger")
    @NotNull
    private Integer valueInteger;

    @Column(name = "ch_valueDecimal")
    @NotNull
    private Float valueDecimal;

    @Column(name = "ch_valueUrl")
    @NotNull
    private String valueUrl;

    @Column(name = "ch_valueDateTime")
    @NotNull
    private LocalDateTime valueDateTime;

    @JoinColumn(name = "ra_ch_id", referencedColumnName = "id")
    @NotNull
    @OneToOne
    private Range valueRange;

    @JoinColumn(name = "an_ch_id", referencedColumnName = "id")
    @NotNull
    @OneToOne
    private Annotation valueAnnotation;

    @JoinColumn(name = "ad_ch_id", referencedColumnName = "id")
    @NotNull
    @OneToOne
    private Address valueAddress;

    @JoinColumn(name = "qu_ch_vDuration_id", referencedColumnName = "id")
    @NotNull
    @OneToOne
    private Quantity valueDuration;

    @JoinColumn(name = "qu_ch_vcc_id", referencedColumnName = "id")
    @NotNull
    @OneToOne
    private CodeableConcept valueCodeableConcept;
    
}

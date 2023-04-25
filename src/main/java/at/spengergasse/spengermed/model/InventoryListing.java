package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "il_inventorylisting")
@SuperBuilder
public class InventoryListing extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "il_re_reporter", referencedColumnName = "id")
    private Reference location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "il_cc_operationtype", referencedColumnName = "id")
    private CodeableConcept itemStatus;

    @Column(name = "il_countingdatetime", nullable = false)
    @NotNull
    private LocalDateTime countingDateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "it_il_id", referencedColumnName = "id")
    @Builder.Default
    private List<Item> item = new ArrayList<>();

}

package at.spengergasse.spengermed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "inr_inventoryreport")
@SuperBuilder
public class InventoryReport extends DomainResource {

    public enum StatusCode {
        active,
        draft,
        requested,
        @JsonProperty("entered-in-error") enteredinerror
    }

    public enum CountCode {
        snapshot,
        difference
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_inr_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "inr_status", nullable = false)
    @NotNull
    private InventoryReport.StatusCode status;

    @Column(name = "inr_counttype", nullable = false)
    @NotNull
    private InventoryReport.CountCode countType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inr_cc_operationtype", referencedColumnName = "id")
    private CodeableConcept operationType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inr_cc_operationtypereason", referencedColumnName = "id")
    private CodeableConcept operationTypeReason;

    @Column(name = "inr_reporteddatetime", nullable = false)
    @NotNull
    private LocalDateTime reportedDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inr_re_reporter", referencedColumnName = "id")
    private Reference reporter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inr_pe_id")
    private Period reportingPeriod;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "il_inr_id", referencedColumnName = "id")
    @Builder.Default
    private List<InventoryListing> inventoryListing = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "an_inr_id", referencedColumnName = "id")
    @Builder.Default
    private List<Annotation> note = new ArrayList<>();

}

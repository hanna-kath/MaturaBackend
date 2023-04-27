package at.spengergasse.spengermed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.aspectj.apache.bcel.classfile.Code;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imm_immunization")
@SuperBuilder
public class Immunization extends DomainResource{

    public enum code {
        completed, entered_in_error, not_done
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> basedOn = new ArrayList<>();

    @Column(name = "imm_status", nullable = false)
    @NotNull
    private Immunization.code status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_sr_id", referencedColumnName = "id")
    private CodeableConcept statusReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_vc_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept vaccineCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_adm_id", referencedColumnName = "id")
    private CodeableReference administeredProduct;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mnf_imm_id", referencedColumnName = "id")
    private CodeableReference manufacturer;

    @Column(name = "imm_lotnumber")
    private String lotNumber;

    @Column(name = "imm_expirationdate")
    private LocalDate expirationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_p_id", referencedColumnName = "id",nullable = false)
    @NotNull
    private Reference patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_e_id", referencedColumnName = "id")
    private Reference encounter;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_imm_isi", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> supportinginformation = new ArrayList<>();

    @Column(name = "imm_occurrencedatetime", nullable = false)
    @NotNull
    private LocalDateTime occurrenceDateTime;

    @Column(name = "imm_occurrencestring", nullable = false)
    @NotNull
    private String occurrenceString;

    @Column(name = "imm_primarysource")
    private Boolean primarySource;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_is_id", referencedColumnName = "id")
    private CodeableReference informationSource;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_lo_id", referencedColumnName = "id")
    private Reference location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_cc_site", referencedColumnName = "id")
    private CodeableConcept site;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_cc_route", referencedColumnName = "id")
    private CodeableConcept route;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_dq_id", referencedColumnName = "id")
    private Money doseQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<Performer> performer = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "an_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<Annotation> note = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cr_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableReference> reason = new ArrayList<>();

    @Column(name = "imm_issubpotent")
    private Boolean isSubpotent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> subpotentReason = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pel_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<ProgramEligibility> programEligibility = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imm_fs_id", referencedColumnName = "id")
    private CodeableConcept fundingSource;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rea_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reaction> reaction = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pra_imm_id", referencedColumnName = "id")
    @Builder.Default
    private List<ProtocolApplied> protocolApplied = new ArrayList<>();



}

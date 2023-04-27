package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "iv_invoice")
public class Invoice extends DomainResource{

    public enum code {
        draft , issued , balanced , cancelled , entered_in_error
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_i_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "iv_status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private code status;

    @Column(name = "iv_cancelledReason", nullable = true)
    private String cancelledReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_cc_type", referencedColumnName = "id")
    private CodeableConcept type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_re_subject", referencedColumnName = "id")
    private Reference subject;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_re_recipient", referencedColumnName = "id")
    private Reference recipient;

    @Column(name = "iv_date", nullable = true)
    private LocalDateTime date;

    @Column(name = "iv_creation", nullable = true)
    private LocalDateTime creation;

    @Column(name = "iv_periodDate", nullable = true)
    private LocalDate periodDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_pe_period", referencedColumnName = "id")
    private Period periodPeriod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_re_issuer", referencedColumnName = "id")
    private Reference issuer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_re_account", referencedColumnName = "id")
    private Reference account;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_mc_id", referencedColumnName = "id")
    private List<MonetaryComponent> totalPriceComponent = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_my_totalNet", referencedColumnName = "id")
    private Money totalNet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_my_totalGross", referencedColumnName = "id")
    private Money totalGross;

    @Column(name = "iv_paymentTerms", nullable = true)
    private String paymentTerms;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_an_id", referencedColumnName = "id")
    private List<Annotation> note = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_lit_id", referencedColumnName = "id")
    private List<LineItem> lineItem = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="iv_pa_id", referencedColumnName = "id")
    private List<Participant> participant = new ArrayList<>();
}

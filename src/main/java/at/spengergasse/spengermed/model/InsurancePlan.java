package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Table(name = "ip_insurancePlan")
public class InsurancePlan extends DomainResource {

    public enum StatusCode {
        draft, active, retired, unknown
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ip_status", nullable = false)
    private StatusCode status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> type = new ArrayList<>();

    @Column(name = "ip_name")
    private String name;

    @Column(name = "ip_alias")
    @ElementCollection
    private List<String> alias;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ip_pe_id", referencedColumnName = "id")
    private Period period;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ip_re_id", referencedColumnName = "id")
    private Reference ownedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ip_re_id2", referencedColumnName = "id")
    private Reference administeredBy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> coverageArea = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<ExtendedContactDetail> contact = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_ip_id3", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> endpoint = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_ip_id4", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> network = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cov_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<Coverage> coverage = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pl_ip_id", referencedColumnName = "id")
    @Builder.Default
    private List<Plan> plan = new ArrayList<>();

}

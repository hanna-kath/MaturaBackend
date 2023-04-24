package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "l_location")
@SuperBuilder
public class Location extends DomainResource{

    public enum StatusCode{
        active, suspended, inactive
    }
    public enum ModeCode{
        instance, kind
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "l_status")
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_c_id", referencedColumnName = "id")
    private Coding operationalStatus;

    @Column(name = "l_name")
    private String name;

    @Column(name = "l_alias")
    @ElementCollection
    private List<String> alias;

    @Column(name = "l_description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "l_mode")
    private ModeCode mode;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> type = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<ExtendedContactDetail> contact = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_a_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_cc_id", referencedColumnName = "id")
    private CodeableConcept form;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_po_id", referencedColumnName = "id")
    private Position position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_re_id", referencedColumnName = "id")
    private Reference managingOrganization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "l_re_id2", referencedColumnName = "id")
    private Reference partOf;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> characteristic = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "av_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<Availability> hoursOfOperation = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vsd_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<VirtualServiceDetail> virtualService = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_l_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> endpoint = new ArrayList<>();

}

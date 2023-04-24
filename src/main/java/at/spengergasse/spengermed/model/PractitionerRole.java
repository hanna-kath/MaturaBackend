package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pr_practitionerrole")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PractitionerRole extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_pr_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "pr_active")
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pr_pe_id")
    private Period period;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pr_re_id", referencedColumnName = "id")
    private Reference practitioner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_pr_id")
    @Builder.Default
    private List<CodeableConcept> specialty = new ArrayList<CodeableConcept>();

    //AvailableTime
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "at_pr_id")
    @Builder.Default
    private List<AvailableTime> availableTime = new ArrayList<AvailableTime>();
}

package at.spengergasse.spengermed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//Entity bewirkt, dass die Klasse Patient von JPA beachtet wird
//und dafür eine Tabelle in der DB erzeugt wird
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "en_encounter")
public class Encounter extends DomainResource {

    public enum StatusCode{
        planned, arrived, triaged, @JsonProperty("in-progress") inprogress, onleave, finished, cancelled
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_en_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @Enumerated(EnumType.STRING)
    @NotNull        //Attribute darf nicht Null sein
    @Column(name = "en_status", nullable = false)   //nullable=false in der DB, null-Constraint in der DB
    private StatusCode status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "st_en_fk", referencedColumnName = "id")
    @Builder.Default
    private List<StatusHistory> statusHistory = new ArrayList<StatusHistory>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_en_fk", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> type = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "en_re_id")
    private Reference subject;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_en_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> episodeOfCare = new ArrayList<Reference>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_en_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Participant> participant = new ArrayList<Participant>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_en_appointment", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> appointment = new ArrayList<Reference>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "en_pe_id")
    private Period period;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_en_reason", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> reasonReference = new ArrayList<Reference>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "d_en_id", referencedColumnName = "id")
    @Builder.Default
    private List<Diagnosis> diagnosis = new ArrayList<Diagnosis>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "en_re_partof")
    private Reference partOf;
}


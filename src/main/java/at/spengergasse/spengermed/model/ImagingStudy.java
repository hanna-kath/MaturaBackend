package at.spengergasse.spengermed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "is_imagingstudy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImagingStudy extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_is_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    public enum code {
        registered,
        available,
        cancelled,
        @JsonProperty("entered-in-error") enteredinerror,
        unknown
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "is_c_id", nullable = false)
    private code status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_is_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> modality = new ArrayList<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "is_re_subject",nullable = false)
    private Reference subject;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "is_re_encounter")
    private Reference encounter;

    @Column(name = "is_started")
    private LocalDateTime started;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_is_basedOn",referencedColumnName = "id")
    private List<Reference> basedOn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_is_partOf",referencedColumnName = "id")
    private List<Reference> partOf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "is_re_referrer")
    private Reference referrer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_is_endpoint",referencedColumnName = "id")
    private List<Reference> endpoint;

    @Column(name = "is_numberofseries")
    private int numberofSeries;

    @Column(name = "is_numberofinstances")
    private int numberofinstances;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "is_cr_procedure",referencedColumnName = "id")
    private List<CodeableReference> procedure = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "is_re_location")
    private Reference location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cr_is_reason",referencedColumnName = "id")
    private List<CodeableReference> reason;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "an_is_id", referencedColumnName = "id")
    @Builder.Default
    private List<Annotation> note = new ArrayList<>();

    @Column(name = "is_description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "se_is_id", referencedColumnName = "id")
    @Builder.Default
    private List<Series> series = new ArrayList<>();


}

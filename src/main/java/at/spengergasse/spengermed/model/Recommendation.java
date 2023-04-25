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
@AllArgsConstructor
@Table(name = "rec_recommendation")
@SuperBuilder
public class Recommendation extends BackboneElement {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_rec_vc", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> vaccineCode = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_rec_td", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> targetDisease = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_rec_cvc", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> contraindicatedVaccineCode = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rec_cc_fs", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept forecastStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_rec_fr", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> forecastReason = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dc_rec_fr", referencedColumnName = "id")
    @Builder.Default
    private List<DateCriterion> dateCriterion = new ArrayList<>();

    @Column(name = "rec_description")
    private String description;

    @Column(name = "rec_series")
    private String series;

    @Column(name = "rec_dosenumber")
    private String doseNumber;

    @Column(name = "rec_seriesdoses")
    private String seriesDoses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_rec_id", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> supportingImmunization = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_rec_id2", referencedColumnName = "id")
    @Builder.Default
    private List<Reference> supportingPatientInformation = new ArrayList<>();

}

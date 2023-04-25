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
@Table(name = "ie_immunizationevaluation")
@SuperBuilder
public class ImmunizationEvaluation extends DomainResource{

    public enum StatusCode {
        completed,
        @JsonProperty("entered-in-error") enteredinerror
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ie_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @Column(name = "ie_status", nullable = false)
    @NotNull
    private ImmunizationEvaluation.StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ie_re_pat", referencedColumnName = "id", nullable = false)
    @NotNull
    private Reference patient;

    @Column(name = "ie_date")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ie_re_aut", referencedColumnName = "id")
    private Reference authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ie_cc_td", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept targetDisease;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ie_re_ime", referencedColumnName = "id", nullable = false)
    @NotNull
    private Reference immunizationEvent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ie_cc_ds", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept doseStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_cc_dsr", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> doseStatusReason = new ArrayList<>();

    @Column(name = "ie_description")
    private String description;

    @Column(name = "ie_series")
    private String series;

    @Column(name = "ie_dosenumber")
    private String doseNumber;

    @Column(name = "ie_seriesdoses")
    private String seriesDoses;

}

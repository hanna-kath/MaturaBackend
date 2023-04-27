package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "se_series")
public class Series extends BackboneElement {

    @NotNull
    @Column(name = "se_uid", nullable = false)
    private String uid;

    @Column(name = "se_number")
    private int number;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "se_modality", nullable = false)
    private CodeableConcept modality;

    @Column(name = "se_description")
    private String description;

    @Column(name = "se_numberofinstances")
    private int numberofinstances;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_se_endpoint",referencedColumnName = "id")
    private List<Reference> endpoint = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "se_re_bodySite")
    private CodeableReference bodySite;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "se_laterality")
    private CodeableConcept laterality;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_se_id",referencedColumnName = "id")
    private List<Reference> specimen = new ArrayList<>();

    @Column(name = "se_started")
    private LocalDateTime started;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_se_id", referencedColumnName = "id")
    @Builder.Default
    private List<Performer> performer = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "inst_is_id", referencedColumnName = "id")
    @Builder.Default
    private List<Instance> instance = new ArrayList<>();
}

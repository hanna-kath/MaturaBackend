package at.spengergasse.spengermed.model;

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
@Table(name = "vp_visionprescription")
@SuperBuilder
@AllArgsConstructor

public class VisionPrescription extends DomainResource{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_vp_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vp_re_patient", nullable = false)
    //@NotNull
    private Reference patient;

    public enum StatusCode {
        acitve, cancelled, draft, enteredinerror
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status")
    //@NotNull
    private VisionPrescription.StatusCode status;

    @Column(name = "vp_datewritten")
    private LocalDateTime dateWritten;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ls_vp_id", referencedColumnName = "id")
    @Builder.Default
    private List<LenseSpecification> lenseSpecification = new ArrayList<>();
}

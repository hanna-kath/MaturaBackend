package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sm_structuremap")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StructureMap extends DomainResource{

    public enum StatCode{
        draft, active, retired, unknown
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "con_status", nullable = false)
    private StatCode stat;

    @Column(name = "sm_datetime")
    private LocalDateTime datetime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_sm_id", referencedColumnName = "id")
    @Builder.Default
    private List<ContactDetail> details = new ArrayList<ContactDetail>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_sm_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> junction = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "s_sm_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Structure> structure = new ArrayList<Structure>();
}

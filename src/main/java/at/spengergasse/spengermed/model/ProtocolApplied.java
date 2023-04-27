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
@Table(name = "pra_protocolapplied")
@SuperBuilder
public class ProtocolApplied extends BackboneElement{

    @Column(name = "pra_series")
    private String series;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pra_re_act", referencedColumnName = "id")
    private Reference actor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_pra_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> targetDisease = new ArrayList<>();

    @Column(name = "pra_dosenumber", nullable = false)
    @NotNull
    private String doseNumber;

    @Column(name = "pra_seriesdoses")
    private String seriesDoses;

}

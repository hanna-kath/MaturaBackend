package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pa_participant")
public class Participant extends BackboneElement{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_pa_id", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> type = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_pe_id")
    private Period period;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_individual")
    private Reference individual;
}

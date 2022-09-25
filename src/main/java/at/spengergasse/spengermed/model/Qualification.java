package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "q_qualification")
public class Qualification extends BackboneElement {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_q_fk", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_q_code", referencedColumnName = "id")
    @Builder.Default
    private List<CodeableConcept> code = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pe_q_id", referencedColumnName = "id")
    private Period period;

}

package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "cr_codeablereference")
public class CodeableReference extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cr_concept", referencedColumnName = "id")
    private CodeableConcept concept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cr_reference", referencedColumnName = "id")
    private Reference reference;

}

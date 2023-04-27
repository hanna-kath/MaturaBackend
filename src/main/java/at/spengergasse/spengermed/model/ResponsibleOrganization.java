package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ro_responsibleorganization")
public class ResponsibleOrganization extends BackboneElement{
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "cc_ro_id", referencedColumnName = "id", nullable = false)
    private CodeableConcept role;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "re_ro_id", referencedColumnName = "id")
    private Reference organization;
}

package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "per_performer")
public class Performer extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_fun_id", referencedColumnName = "id")
    private CodeableConcept function;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_re_id", referencedColumnName = "id")
    private Reference actor;
}

package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

//neu
@Entity
@Table(name = "s_structure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Structure extends BackboneElement {

    //@OneToMany
    @Column(name="s_aliases")
    private String aliases;

    public enum ModusCode{
        source, queried, target, produced
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "s_modus")
    private ModusCode modus;

}

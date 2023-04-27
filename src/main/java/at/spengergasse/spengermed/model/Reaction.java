package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rea_reaction")
@SuperBuilder
public class Reaction extends BackboneElement{

    @Column(name = "rea_datetime")
    private LocalDateTime dateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rea_man_id", referencedColumnName = "id")
    private CodeableReference manifestation;

    @Column(name = "rea_reported")
    private Boolean reported;
}

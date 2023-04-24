package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "an_annotation")
public class Annotation extends Element {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "an_re_id")
    private Reference authorReference;

    @Column(name = "an_authorstring")
    private String authorString;

    @Column(name = "an_time")
    private LocalDateTime time;

    @Column(name = "an_text")
    private String text;
}

package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "par_parameter")
public class Parameter extends BackboneElement{

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="par_co_id", referencedColumnName = "id", nullable = false)
    private Coding code;

    @NotNull
    @Column(name = "par_value")
    private String value;
}

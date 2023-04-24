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
@Table(name = "r_ratio")
public class Ratio extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rat_numerator", referencedColumnName = "id")
    private Quantity numerator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="rat_denominator", referencedColumnName = "id")
    private Money denominator;
}

package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.net.URI;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "qu_quantity")
@SuperBuilder
@AllArgsConstructor
public class Quantity extends Element {

    @Column(name = "qu_decimal")
    private double decimal;

    @Column(name = "qu_comparator")
    @Enumerated(EnumType.STRING)
    private ComparatorEnum comparator;

    @Column(name = "qu_unit")
    private String unit;

    @Column(name = "qu_sytem")
    private URI system;

    @Column(name = "qu_code")
    private String code;
}

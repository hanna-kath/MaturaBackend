package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "na_name")
public class Name extends BackboneElement {

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "co_na_id", referencedColumnName = "id", nullable = false)
    private Coding nameType;

    @NotNull
    @Column(name = "na_language", nullable = false)
    private String language;

    @Column(name = "na_name", nullable = false)
    private String name;

}

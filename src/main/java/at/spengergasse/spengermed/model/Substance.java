package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "sub_substance")
public class Substance extends BackboneElement {
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_code", referencedColumnName = "id", nullable = false)
    private CodeableReference code;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "str_in_id", referencedColumnName = "id")
    private List<Strength> strenght = new ArrayList<>();
}

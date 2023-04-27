package at.spengergasse.spengermed.model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "ma_manufacturer")
public class Manufacturer extends BackboneElement {

    public enum code {
        allowed , possible , actual;
    }

    @Column(name="ma_role")
    private code role;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_manufacturer", referencedColumnName = "id")
    private Reference manufacturer;
}

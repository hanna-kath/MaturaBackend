package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "g_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Group extends DomainResource{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_g_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gc_ch_id", referencedColumnName = "id")
    @Builder.Default
    private List<Characteristic> characteristic = new ArrayList<>();

}

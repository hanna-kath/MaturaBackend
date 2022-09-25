package at.spengergasse.spengermed.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "o_organization")
public class Organization extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="i_o_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @Column(name = "o_active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_o_id", referencedColumnName = "id")
    @Builder.Default
    private List<Address> address = new ArrayList<Address>();

    @Column(name = "o_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_o_id", referencedColumnName = "id")
    @Builder.Default
    private List<Contact> contact = new ArrayList<Contact>();

}

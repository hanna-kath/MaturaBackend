package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

//neu
@Entity
@Table(name = "cd_contactdetail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContactDetail extends Element{

    //@OneToMany
    @Column(name="cd_name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_cd_fk")
    private ContactPoint tele;


}

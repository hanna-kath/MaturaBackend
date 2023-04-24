package at.spengergasse.spengermed.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "po_policy")
public class Policy extends BackboneElement {

    @Column(name = "po_authority")
    //private URI authority;
    private String authority;


}

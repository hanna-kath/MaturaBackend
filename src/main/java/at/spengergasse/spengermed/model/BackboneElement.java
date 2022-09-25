package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
//@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BackboneElement extends Element {   //kann man abstract machen, weil man das nie extra stehen hat
}

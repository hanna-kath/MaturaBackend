package at.spengergasse.spengermed.model;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class DomainResource extends Resource {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "dr_n_id", referencedColumnName = "id")
  private Narrative text;
}

package at.spengergasse.spengermed.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
@Table(name = "n_narrative")
public class Narrative extends Element {

  public enum NarrativeCode {
    generated,
    extensions,
    additional,
    empty
  }

  @Enumerated(EnumType.STRING)
  @NotNull(message = "Der status von Narrative muss einen Wert haben")
  @Column(name = "n_status", nullable = false)
  private NarrativeCode status;

  @NotNull(message = "Das div Attribut von Narrative muss einen Wert haben")
  @Lob
  @Column(name = "n_div", nullable = false)
  private String div;
}

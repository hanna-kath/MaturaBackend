package at.spengergasse.spengermed.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "cc_codeableconcept")
@AllArgsConstructor
public class CodeableConcept extends Element {

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "co_cc_id")
  @Builder.Default
  private List<Coding> coding = new ArrayList<>();

  @Column(name = "cc_text")
  private String text;
}

package at.spengergasse.spengermed.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "hn_humanname")
public class HumanName extends Element {

  public enum UseCode {
    usual,
    official,
    temp,
    nickname,
    anonymous,
    old,
    maiden
  }

  @Column(name = "hn_use")
  @Enumerated(EnumType.STRING)
  private UseCode use;

  @Column(name = "hn_text")
  private String text;

  @Column(name = "hn_family")
  private String family;

  @ElementCollection
  @CollectionTable(name = "given_humanname", joinColumns = @JoinColumn(name = "id"))
  @Builder.Default
  private List<String> given = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "prefix_humanname", joinColumns = @JoinColumn(name = "id"))
  @Builder.Default
  private List<String> prefix = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "suffix_humanname", joinColumns = @JoinColumn(name = "id"))
  @Builder.Default
  private List<String> suffix = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hn_pe_id")
  private Period period;
}

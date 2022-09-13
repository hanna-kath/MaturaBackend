package at.spengergasse.spengermed.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "cp_contactpoint")
public class ContactPoint extends Element {

  public enum SystemCode {
    phone,
    fax,
    email,
    pager,
    url,
    sms,
    other
  }

  public enum UseCode {
    home,
    work,
    temp,
    old,
    mobile
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "cp_system")
  private SystemCode system;

  @Column(name = "cp_value")
  private String value;

  @Enumerated(EnumType.STRING)
  @Column(name = "cp_use")
  private UseCode use;

  @Min(1)
  @Column(name = "cp_rank")
  private Integer rank;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cp_pe_id")
  private Period period;
}

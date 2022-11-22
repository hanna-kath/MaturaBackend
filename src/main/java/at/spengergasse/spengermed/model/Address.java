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
@Table(name = "a_address")
@SuperBuilder
@AllArgsConstructor
public class Address extends Element {
  public enum UseCode {
    home,
    work,
    temp,
    old,
    billing
  }

  public enum TypeCode {
    postal,
    physical,
    both
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "a_use")
  private UseCode use;

  @Enumerated(EnumType.STRING)
  @Column(name = "a_type")
  private TypeCode type;

  @Column(name = "a_text")
  private String text;

  @ElementCollection
  @CollectionTable(name = "a_address_line")
  @Builder.Default
  private List<String> line = new ArrayList<>();

  @Column(name = "a_city")
  private String city;

  @Column(name = "a_district")
  private String district;

  @Column(name = "a_state")
  private String state;

  @Column(name = "a_postalcode")
  private String postalcode;

  @Column(name = "a_country")
  private String country;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "a_pe_id", referencedColumnName = "id")
  private Period period;
}

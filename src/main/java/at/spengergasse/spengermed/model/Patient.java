package at.spengergasse.spengermed.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;

import lombok.*;
import lombok.experimental.SuperBuilder;

// Entity bewirkt, dass die Klasse Patient von JPA beachtet wird
// und dafür eine Tabelle in der DB erzeugt wird
@Entity
@Table(name = "p_patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends DomainResource {

  public enum GenderCode {
    male,
    female,
    other,
    unknown
  }

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "i_p_id", referencedColumnName = "id")
  @Builder.Default
  private List<Identifier> identifier = new ArrayList<>();

  @Column(name = "p_active")
  private Boolean active;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "hn_p_id", nullable = true, referencedColumnName = "id")
  @Builder.Default
  private List<HumanName> name = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "cp_p_id", referencedColumnName = "id")
  @Builder.Default
  private List<ContactPoint> telecom = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(name = "p_gender")
  private GenderCode gender;

  @PastOrPresent(message = "Das Geburtsdatum muss in der Vergangenheit liegen")
  @Column(name = "p_birthdate")
  private LocalDate birthDate;

  @Column(name = "p_deceasedboolean")
  private Boolean deceasedBoolean;

  @Column(name = "p_deceaseddatetime")
  private LocalDateTime deceasedDateTime;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "a_p_id", referencedColumnName = "id")
  @Builder.Default
  private List<Address> address = new ArrayList<>();
}

package at.spengergasse.spengermed.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "pe_period")
public class Period extends Element {
  @Column(name = "pe_start")
  private LocalDateTime start;

  @Column(name = "pe_end")
  private LocalDateTime end;
}

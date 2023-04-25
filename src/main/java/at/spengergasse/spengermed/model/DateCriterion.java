package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dc_datecriterion")
@SuperBuilder
public class DateCriterion extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dc_cc_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private CodeableConcept code;

    @Column(name = "dc_value", nullable = false)
    @NotNull
    private LocalDateTime value;
}

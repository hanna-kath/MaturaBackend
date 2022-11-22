package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ra_riskassessment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RiskAssessment extends DomainResource{

    public enum StatusCode {
        registered  ("registered "),
        preliminary  ("preliminary "),
        amended   ("amended  "),
        f ("final ");
        private String value;
        private StatusCode(String value) {
            this.value = value;
        }
        public String toString() {
            return this.value;
        }
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ra_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ra_re_id")
    private Reference parent;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "ra_status", nullable = false)
    private RiskAssessment.StatusCode status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pre_ra_fk", referencedColumnName = "id")
    private List<Prediction> prediction = new ArrayList<>();

}

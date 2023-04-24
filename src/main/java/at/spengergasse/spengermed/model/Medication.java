package at.spengergasse.spengermed.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "m_medication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Medication extends DomainResource {
    public enum StatusCode {
        active,
        inactive,
        enteredinerror
        //@JsonProperty("entered-in-error") enteredinerror
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_m_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_cc_code", referencedColumnName = "id")
    private CodeableConcept code;

    @Column(name = "m_status")
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_r_id", referencedColumnName = "id")
    private Reference marketingAuthorizationHolder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_cc_doseForm", referencedColumnName = "id")
    private CodeableConcept doseForm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_qu_id", referencedColumnName = "id")
    private Quantity totalVolume;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_in_id", referencedColumnName = "id")
    @Builder.Default
    private List<Ingredient> ingredient = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_b_id", referencedColumnName = "id")
    private Batch batch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_re_id", referencedColumnName = "id")
    private Reference definition;

}

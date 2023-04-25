package at.spengergasse.spengermed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ir_immunizationrecommendation")
@SuperBuilder
public class ImmunizationRecommendation extends DomainResource {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_ir_id", referencedColumnName = "id")
    @Builder.Default
    private List<Identifier> identifier = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ir_re_pat", referencedColumnName = "id", nullable = false)
    @NotNull
    private Reference patient;

    @Column(name = "ir_date", nullable = false)
    @NotNull
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ir_re_aut", referencedColumnName = "id")
    private Reference authority;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rec_ir_id", referencedColumnName = "id", nullable = false)
    @Builder.Default
    @NotNull
    private List<Recommendation> recommendation = new ArrayList<>();

}

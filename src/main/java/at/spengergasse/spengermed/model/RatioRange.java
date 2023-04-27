package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "rra_ratiorange")
public class RatioRange extends Element{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rra_qu_id", referencedColumnName = "id")
    private Quantity lownumerator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rra_qu_qu", referencedColumnName = "id")
    private Quantity highnumerator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rra_qu_qy", referencedColumnName = "id")
    private Quantity denominator;

}

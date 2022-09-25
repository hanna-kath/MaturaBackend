package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "st_statushistory")
public class StatusHistory extends BackboneElement{

    public enum StatusCode{
        planned, arrived, triaged, inprogress, onleave, finished, cancelled
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "st_status")
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "st_pe_id")
    private Period period;

}

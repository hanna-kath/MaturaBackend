package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "navt_NotAvailableTime")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NotAvailableTime extends Element {

    @Column(name = "navt_description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "navt_pe_id", referencedColumnName = "id")
    private Period during;
}

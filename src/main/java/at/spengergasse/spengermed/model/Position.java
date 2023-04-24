package at.spengergasse.spengermed.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Table(name = "po_position")
public class Position extends BackboneElement{

    @Column(name = "po_longitude")
    private Double longitude;

    @Column(name = "po_latitude")
    private Double latitude;

    @Column(name = "po_altitude")
    private Double altitude;
}

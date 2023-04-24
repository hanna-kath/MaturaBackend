package at.spengergasse.spengermed.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "av_availability")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Availability extends Element{

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "avt_av_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<AvailableTime> availableTime = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "navt_av_id", nullable = true, referencedColumnName = "id")
    @Builder.Default
    private List<NotAvailableTime> notAvailableTime = new ArrayList<>();

}
